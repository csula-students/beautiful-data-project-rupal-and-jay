package edu.csula.datascience.acquisition;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

/**
 * Interface to define collector behavior
 *
 * It should be able to download data from source and save data.
 */
public interface Collector {
    /**
     * Mungee method is to clean data. e.g. remove data rows with errors
     */
    List<EarthquakeModel> mungee();

    void save(List<EarthquakeModel> l1);
}
