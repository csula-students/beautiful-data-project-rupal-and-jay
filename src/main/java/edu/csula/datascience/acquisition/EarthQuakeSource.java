package edu.csula.datascience.acquisition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class EarthQuakeSource implements Source
{

	@Override
	public boolean hasNext() 
	{	
		return false;
	}
	
	@Override
	public Object next()
	{
		return null;
	}

	@Override
	public void getEarthQuakeData()
	{
	
		try 
		{
		
			int year=1990;
			
			while(year!=2016)
			{
				
				int month=1;
				int month1=6;
				
				while(month<=12)
				{
				
					//int month1=month+6;
				
					URL url;
					url = new URL(("http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime="+year+"-"+month+"-01&endtime="+year+"-"+month1+"-01&minmagnitude=1&limit=20000"));
					BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
					BufferedWriter out = new BufferedWriter(new FileWriter("C:/Users/Jay Purohit/Desktop/save.txt",true));
					char[] cbuf=new char[255];
					
					while ((in.read(cbuf)) != -1)
					{
						out.write(cbuf);
					}
					
					in.close();
					out.close();

					month=7;
					month1=12;
					
				}
				
				year++;
				
			}
		}
		
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		
		catch (IOException e1)
		{
			e1.printStackTrace();
		}	
	}
}
