package edu.csula.datascience.acquisition;

public class EarthquakeModel
{
	String id;
	String place;
	long time;
	public EarthquakeModel(String id, String place, long time) {
		
		this.id = id;
		this.place = place;
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	
}
