package edu.csula.datascience.acquisition;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.csula.datascience.acquisition.CollectorImplementation;;

public class EarthquakeTest {

	@Test
	public void mungee() throws Exception {

		CollectorImplementation dataset = new CollectorImplementation();

		//Perform mungee on data
		List<EarthquakeModel> EarthquakeDataList = dataset.mungee();
	


		//Result after mungee
		Assert.assertEquals(EarthquakeDataList.size(), 275);

	}
}
