package searchengine;

import java.util.*;

/**
 * It represents the Vector that is the reference on which every cluster is built. Once values are
 * assigned it accept Vectors of Websites that are assigned to the cluster, which can be deleted and
 * reassigned.
 * 
 * @author André Mortensen Kobæk
 * @author Domenico Villani
 * @author Flemming Westberg
 * @author Mikkel Buch Smedemand
 */
public class CentroidMap {
    private Map<String, Double> values;
    private List<VectorMap> websiteVectors;
    private String name;

    /**
     * Takes a List of Double as values and a name as String for the cluster
     * @param values List of Double
     * @param name String
     */
    public CentroidMap(Map<String, Double> values, String name){
        this.name = name;
        websiteVectors = new ArrayList<>();
        this.values = values;
    }

    /**
     * Takes a centroid and copies its name and its values to a new Centroid instance. This constructor is needed
     * to copy the content of a Centroid instance without pointing at the same reference in the memory.
     * @param c Centroid
     */
    public CentroidMap(CentroidMap c){
        this.name = c.getClusterName();
        this.websiteVectors = new ArrayList<>();
        this.values = c.getCentroidValuesMap();
    }

    /**
     * Return a list of Double that represents the values of the Centroid
     * @return List of Double
     */
    public Map<String, Double> getCentroidValuesMap(){
        return values;
    }

    /**
     * Delete all the values of the Centroid and sets new values for the List
     * @param newValues List of Double
     */
    public void setNewValues(Map<String, Double> newValues){
        values = new HashMap<>(newValues);
        //values.addAll(0,newValues);
    }

    /**
     * Takes a Vector and adds it to the list of vectors assigned to the centroid
     * @param w Vector
     */
    public void assignWebsiteVectorToCentroid(VectorMap w){
        websiteVectors.add(w);
    }

    /**
     * Erase the list of Vector assigned to the centroid
     */
    public void clearListOfWebsites(){
        websiteVectors = new ArrayList<>();
    }

    /**
     * Return the list of Vector assigned to the centroid
     * @return List of Vector
     */
    public List<VectorMap> getWebsiteVectors(){
        return websiteVectors;
    }

    /**
     * Return the name of the cluster
     * @return String
     */
    public String getClusterName(){
        return name;
    }
}
