package edu.csula.datascience.examples;

import com.google.common.collect.Lists;
import io.searchbox.action.BulkableAction;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Collection;

/**
 * A quick example app to send data to elastic search on AWS
 */
public class JestExampleApp {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String indexName = "bd-data";
        String typeName = "EarthQuake";
        String awsAddress = "https://search-bd-data-nvd22odj4ufhebqhbf4eyx3aji.us-west-2.es.amazonaws.com/";
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
            .Builder(awsAddress)
            .multiThreaded(true)
            .build());
        JestClient client = factory.getObject();

        // as usual process to connect to data source, we will need to set up
        // node and client// to read CSV file from the resource folder
        File csv = new File(
            ClassLoader.getSystemResource("all_month.csv")
                .toURI()
        );

        try {
            // after reading the csv file, we will use CSVParser to parse through
            // the csv files
            CSVParser parser = CSVParser.parse(
                csv,
                Charset.defaultCharset(),
                CSVFormat.EXCEL.withHeader()
            );
            Collection<EarthQuake> earthquakes = Lists.newArrayList();

            int count = 0;

            // for each record, we will insert data into Elastic Search
//            parser.forEach(record -> {
            for (CSVRecord record: parser) {
                // cleaning up dirty data which doesn't have time or temperature
                if (
                		!record.get("time").isEmpty() &&
	                    !record.get("mag").isEmpty()&&
	                    !record.get("locationSource").isEmpty()
                ) {
                    EarthQuake eq = new EarthQuake(
                    		record.get("time"),
                    		//Double.valueOf(record.get("AverageTemperature")),
                    		Double.valueOf(record.get("mag")),
                    		record.get("locationSource")
                    );

                    if (count < 500) {
                        earthquakes.add(eq);
                        count ++;
                    } else {
                        try {
                            Collection<BulkableAction> actions = Lists.newArrayList();
                            earthquakes.stream()
                                .forEach(tmp -> {
                                    actions.add(new Index.Builder(tmp).build());
                                });
                            Bulk.Builder bulk = new Bulk.Builder()
                                .defaultIndex(indexName)
                                .defaultType(typeName)
                                .addAction(actions);
                            client.execute(bulk.build());
                            count = 0;
                            earthquakes = Lists.newArrayList();
                            System.out.println("Inserted 500 documents to cloud");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            Collection<BulkableAction> actions = Lists.newArrayList();
            earthquakes.stream()
                .forEach(tmp -> {
                    actions.add(new Index.Builder(tmp).build());
                });
            Bulk.Builder bulk = new Bulk.Builder()
                .defaultIndex(indexName)
                .defaultType(typeName)
                .addAction(actions);
            client.execute(bulk.build());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("We are done! Yay!");
    }

    static class EarthQuake {
        final String time;
        final double magnitude;
        final String location;
       // final String country;
		public EarthQuake(String time, double magnitude, String location) {
			//super();
			this.time = time;
			this.magnitude = magnitude;
			this.location = location;
		}

        
    }
}
