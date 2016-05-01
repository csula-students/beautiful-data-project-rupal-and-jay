package edu.csula.datascience.acquisition;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CollectorImplementation implements Collector {
	
	MongoClient mongoClient;
	 MongoDatabase database;
	 MongoCollection<Document> collection;
	 public CollectorImplementation() {
	        // establish database connection to MongoDB
	        mongoClient = new MongoClient();

	        // select `bd-example` as testing database
	        database = mongoClient.getDatabase("bd-example");

	        // select collection by name `tweets`
	        collection = database.getCollection("EarthquakeModel");
	    }
	 


	//  collection =   // select collection by name `tweets`  database = mongoClient.getDatabase("bd-example");

	@Override
	public List<EarthquakeModel> mungee() {
		 JSONParser parser = new JSONParser();
	        try{
	        
	        		List<EarthquakeModel> l1=new ArrayList<>();
	        		
	        		Object obj = parser.parse(new FileReader("/Users/Macbook/Desktop/r.json"));
	        		JSONObject jsonObject = (JSONObject) obj;
	        		JSONArray msg = (JSONArray) jsonObject.get("features");
	        		Iterator<JSONObject> iterator = msg.iterator();
	        		while (iterator.hasNext())
	        		{
	        			EarthquakeModel e1 = new EarthquakeModel(null, null, 0);
	        			JSONObject factObj = (JSONObject) iterator.next();
	        			/*  String type = (String) jsonObject.get("type");
	            		System.out.println(type);*/
	        			String id = (String) factObj.get("id");
	        			e1.setId(id);
	        			JSONObject properties = (JSONObject) factObj.get("properties");
	        			String place = (String) properties.get("place");
	        			e1.setPlace(place);
	        			//System.out.println(place);
	        			Long time = (Long) properties.get("time");
	        			e1.setTime(time);
	        			//JSONObject jsonc = new JSONObject();  
	        			l1.add(e1);

	        		}
	        		return l1;
	        }	
	        	catch (Exception e)
	        	{
	        		e.printStackTrace();
	        	}
			return null;
	        
		//return null;
	}

	@Override
	public void save(List<EarthquakeModel> l1) 
	{
		System.out.println("hii");
			//List l2=new ArrayList();
			for(EarthquakeModel e2:l1)
			{
				//System.out.println("hello");

				Document doc1=new Document();
				doc1.append("id",e2.getId());
				doc1.append("time",e2.getTime());
				doc1.append("place", e2.getPlace());
				 collection.insertOne(doc1);
				//l2.add(doc1);
					//System.out.println("hii");

				
			}
			
	}
	
		
	

}
