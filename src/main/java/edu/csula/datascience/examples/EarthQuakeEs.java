/*package edu.csula.datascience.examples;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import com.google.gson.Gson;

public class EarthQuakeEs {
	 private final static String indexName = "bd-data";
	    private final static String typeName = "EarthQuake";

	    public static void main(String[] args) throws URISyntaxException, IOException {
	        Node node = nodeBuilder().settings(Settings.builder()
	            .put("cluster.name", "my-example")
	            .put("path.home", "C:/elasticsearch-2.3.2/data1")).node();
	        Client client = node.client();

	        *//**
	         *
	         *
	         * INSERT data to elastic search
	         *//*

	        // as usual process to connect to data source, we will need to set up
	        // node and client// to read CSV file from the resource folder
	        File csv = new File(
	            ClassLoader.getSystemResource("all_month.csv")
	                .toURI()
	        );

	        // create bulk processor
	        BulkProcessor bulkProcessor = BulkProcessor.builder(
	            client,
	            new BulkProcessor.Listener() {
	                @Override
	                public void beforeBulk(long executionId,
	                                       BulkRequest request) {
	                }

	                @Override
	                public void afterBulk(long executionId,
	                                      BulkRequest request,
	                                      BulkResponse response) {
	                }

	                @Override
	                public void afterBulk(long executionId,
	                                      BulkRequest request,
	                                      Throwable failure) {
	                    System.out.println("Facing error while importing data to elastic search");
	                    failure.printStackTrace();
	                }
	            })
	            .setBulkActions(10000)
	            .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
	            .setFlushInterval(TimeValue.timeValueSeconds(5))
	            .setConcurrentRequests(1)
	            .setBackoffPolicy(
	                BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
	            .build();

	        // Gson library for sending json to elastic search
	        Gson gson = new Gson();

	        try {
	            // after reading the csv file, we will use CSVParser to parse through
	            // the csv files
	            CSVParser parser = CSVParser.parse(
	                csv,
	                Charset.defaultCharset(),
	                CSVFormat.EXCEL.withHeader()
	            );

	            // for each record, we will insert data into Elastic Search
	            parser.forEach(record -> {
	                // cleaning up dirty data which doesn't have time or temperature
	                if (
	                    !record.get("time").isEmpty() &&
	                    !record.get("mag").isEmpty()&&
	                    !record.get("locationSource").isEmpty()
	                    
	                ) {
	                	
	                	String t2=record.get("time");
	                    Double m1=Double.valueOf(record.get("mag"));
	                   String loc1= record.get("locationSource");
	                   EarthQuake eq = new EarthQuake();
	                   eq.setTime(t2);
	                   eq.setMagnitude(m1);
	                   eq.setLocation(loc1);

	                    bulkProcessor.add(new IndexRequest(indexName, typeName)
	                        .source(gson.toJson(eq))
	                    );
	                }
	            });
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        *//**
	         * Structured search
	         *//*

	        // simple search by field name "state" and find Washington
//	        SearchResponse response = client.prepareSearch(indexName)
//	            .setTypes(typeName)
//	            .setSearchType(SearchType.DEFAULT)
//	            .setQuery(QueryBuilders.matchQuery("state", "Washington"))   // Query
//	            .setScroll(new TimeValue(60000))
//	            .setSize(60).setExplain(true)
//	            .execute()
//	            .actionGet();
	//
//	        //Scroll until no hits are returned
//	        while (true) {
	//
//	            for (SearchHit hit : response.getHits().getHits()) {
//	                System.out.println(hit.sourceAsString());
//	            }
//	            response = client
//	                .prepareSearchScroll(response.getScrollId())
//	                .setScroll(new TimeValue(60000))
//	                .execute()
//	                .actionGet();
//	            //Break condition: No hits are returned
//	            if (response.getHits().getHits().length == 0) {
//	                break;
//	            }
//	        }
	//
	        *//**
	         * AGGREGATION
	         *//*
	        SearchResponse sr = node.client().prepareSearch(indexName)
	            .setTypes(typeName)
	            .setQuery(QueryBuilders.matchAllQuery())
	            .addAggregation(
	                AggregationBuilders.terms("locationAgg").field("location")
	                    .size(Integer.MAX_VALUE)
	            )
	            .execute().actionGet();

	        // Get your facet results
	        Terms agg1 = sr.getAggregations().get("locationAgg");

	        for (Terms.Bucket bucket: agg1.getBuckets()) {
	            System.out.println(bucket.getKey() + ": " + bucket.getDocCount());
	        }
	    }
	static class EarthQuake{
	String time;
	Double magnitude;
	String location;
	
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getMagnitude() {
		return magnitude;
	}
	
	public void setMagnitude(Double magnitude) {
		this.magnitude = magnitude;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}

}
*/