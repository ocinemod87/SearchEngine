package searchengine;

import java.util.Map;
import java.util.SortedSet;


/**
 * CosineSimilarity calculates the distance between two Websites's Vectors, it is also used to
 * measure the distance between a Centroid's Vector and a Website's Vector.
 *
 * @author Domenico Villani
 * @author Mikkel Buch Smedemand
 */
public class CosineSimilarityMap {

    /** Creates an instance of the {@code CosineSimilarity} class */
    public CosineSimilarityMap(){}

    /**
     * Takes two vectors as lists and returns the distance by a number between 0.0 and 1.0
     * 
     * @param vector1    List of Double values
     * @param vector2    List of Double values
     * @param totalWords A SortedSet with all the words in the dataset
     * @return Double value representing the cosine similarity
     */
    public double calculateCS(Map<String, Double> vector1, Map<String, Double> vector2,
            SortedSet<String> totalWords) {


        double magnitudeVect1 = 0;
        double magnitudeVect2 = 0;
        double dotProduct = 0;

        // Take the value of the vectors of the two lists, add the value of each position in the
        // vector in power of 2
        // to a magnitude variable and multiply each value with the value at the same index in the
        // other Vector.
        for (String word : totalWords) {

            // The absolute value is used for normalization purposes
            if (vector1.containsKey(word) && vector2.containsKey(word)) {
                dotProduct += vector1.get(word) * vector2.get(word);
                magnitudeVect1 += Math.pow(Math.abs(vector1.get(word)), 2);
                magnitudeVect2 += Math.pow(Math.abs(vector2.get(word)), 2);
            } else if (vector1.containsKey(word) && !vector2.containsKey(word)) {
                // Add only magnitude 1
                magnitudeVect1 += Math.pow(Math.abs(vector1.get(word)), 2);
            } else if (!vector1.containsKey(word) && vector2.containsKey(word)) {
                magnitudeVect2 += Math.pow(Math.abs(vector2.get(word)), 2);
            }
        }

        // Divide the dotProduct by the square root of each vector and multiply together
        if (Double.isNaN(dotProduct / (Math.sqrt(magnitudeVect1) * Math.sqrt(magnitudeVect2)))) {
            return 0.0;
        }
        return dotProduct / (Math.sqrt(magnitudeVect1) * Math.sqrt(magnitudeVect2));
    }
}
