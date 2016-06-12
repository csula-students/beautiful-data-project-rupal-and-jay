package edu.csula.datascience.acquisition;

import java.util.List;

public class EarthQuakeDataCollector 
{
	public static void main(String[] args)
	{
		/*EarthQuakeSource src=new EarthQuakeSource();
		src.getEarthQuakeData();*/
		CollectorImplementation c1=new CollectorImplementation();
		List<EarthquakeModel> l1=c1.mungee();	
		c1.save(l1);
		
	}
	
}
