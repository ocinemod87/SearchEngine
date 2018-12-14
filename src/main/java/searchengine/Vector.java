package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a Vector of a Website. Initialize a Website a vectorValues List of Double and a
 * wordValues Map of String and Double. Takes a String and a Double and assign them to the Map and
 * to the List to represent the Vector's values
 *
 * @author Domenico Villani
 * @author Mikkel Buch Smedemand
 */
public class Vector {
    Website website;
    List<Double> vectorValues;

    public Vector(Website website) {
        this.website = website;
        vectorValues = new ArrayList<>();
    }

    /**
     * Takes a word and a value and assign them to a list and a map
     * 
     * @param word  to assign in the wordWalues Map
     * @param value to assign in the wordValues Map and vectorValues list
     */
    public void addVectorValue(String word, Double value) {
        vectorValues.add(value);
    }

    /**
     * Returns the list of Double containing the value of the Vector
     * 
     * @return List of Double
     */
    public List<Double> getVectorValues() {
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
