package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 * It represents the Vector that is the reference on which every cluster is built. Once values are
 * assigned it accept Vectors of Websites that are assigned to the cluster, which can be deleted and
 * reassigned.
 *
 * @author Domenico Villani
 * @author Mikkel Buch Smedemand
 */
public class Centroid {

    private List<Double> values;
    private List<Vector> websiteVectors;
    private String name;

    /**
     * Takes a List of Double as values and a name as String for the cluster
     * 
     * @param values List of Double
     * @param name   String
     */
    public Centroid(List<Double> values, String name) {
        this.name = name;
        websiteVectors = new ArrayList<>();
        this.values = values;
    }

    /**
     * Takes a centroid and copies its name and its values to a new Centroid instance. This
     * constructor is needed to copy the content of a Centroid instance without pointing at the same
     * reference in the memory.
     * 
     * @param other Centroid
     */
    public Centroid(Centroid other) {
        this.name = other.getClusterName();
        this.websiteVectors = new ArrayList<>();
        this.values = other.getCentroidValues();
    }

    /**
     * Return a list of Double that represents the values of the Centroid
     * 
     * @return List of Double
     */
    public List<Double> getCentroidValues() {
        return values;
    }

    /**
     * Delete all the values of the Centroid and sets new values for the List
     * 
     * @param newValues List of Double
     */
    public void setNewValues(List<Double> newValues) {
        values = new ArrayList<>(newValues);
        // values.addAll(0,newValues);
    }

    /**
     * Takes a Vector and adds it to the list of vectors assigned to the centroid
     * 
     * @param w Vector
     */
    public void assignWebsiteVectorToCentroid(Vector w) {
        websiteVectors.add(w);
    }

    /**
     * Erase the list of Vector assigned to the centroid
     */
    public void clearListOfWebsites() {
        websiteVectors = new ArrayList<>();
    }

    /**
     * Return the list of Vector assigned to the centroid
     * 
     * @return List of Vector
     */
    public List<Vector> getWebsiteVectors() {
        return websiteVectors;
    }

    /**
     * Return the name of the cluster
     * 
     * @return String
     */
    public String getClusterName() {
        return name;
    }
}
