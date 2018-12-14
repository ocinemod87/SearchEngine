package searchengine;

import java.util.Map;
import java.util.HashMap;

/**
 * A {@code VectorMap}
 *
 * @author Domenico Villani
 * @author Mikkel Buch Smedemand
 */
public class VectorMap {
    Website website;
    Map<String, Double> vectorValues;

    public VectorMap(Website website) {
        this.website = website;
        vectorValues = new HashMap<>();
    }

    /**
     * Takes a word and a value and assign them to a list and a map
     * 
     * @param word  to assign in the wordWalues Map
     * @param value to assign in the wordValues Map and vectorValues list
     */
    public void addVectorValue(String word, Double value) {
        vectorValues.put(word, value);
    }

    /**
     * Returns the list of Double containing the value of the Vector
     * 
     * @return List of Double
     */
    public Map<String, Double> getVectorValuesMap() {
        return vectorValues;
    }

    /**
     * Return the website assigned to the Vector
     * 
     * @return Website
     */
    public Website getWebsite() {
        return website;
    }

}
