package searchengine;

import java.util.List;

/**
 * CosineSimilarity calculates the distance between two Websites's Vectors, it is also used to
 * measure the distance between a Centroid's Vector and a Website's Vector
 *
 * @author Domenico Villani
 * @author Mikkel Buch Smedemand
 */
public class CosineSimilarity {

    /** Creates an instance of the {@code CosineSimilarity} class */
    public CosineSimilarity(){}

    /**
     * Takes two vectors as lists and returns the distance by a number between 0.0 and 1.0
     * 
     * @param vector1 List of Double
     * @param vector2 List of Double
     * @return double value representing cosine similarity
     */
    public double calculateCS(List<Double> vector1, List<Double> vector2) {


        double magnitudeVect1 = 0;
        double magnitudeVect2 = 0;
        double dotProduct = 0;

        // Take the value of the vectors of the two lists, add the value of each position in the
        // vector in power of 2
        // to a magnitude variable and multiply each value with the value at the same index in the
        // other Vector.
        // If the value is null or NaN, add 0
        for (int i = 0; i < vector1.size(); i++) {
            if (Double.isNaN(vector1.get(i)) && Double.isNaN(vector2.get(i))) {
                // Do nothing
            } else if (!Double.isNaN(vector1.get(i)) && Double.isNaN(vector2.get(i))) {
                // Add only magnitude 1
                magnitudeVect1 += Math.pow(Math.abs(vector1.get(i)), 2);

            } else if (Double.isNaN(vector1.get(i)) && !Double.isNaN(vector2.get(i))) {
                // Add only magnitude 2
                magnitudeVect2 += Math.pow(Math.abs(vector2.get(i)), 2);
            } else {
                dotProduct += vector1.get(i) * vector2.get(i);
                magnitudeVect1 += Math.pow(Math.abs(vector1.get(i)), 2);
                magnitudeVect2 += Math.pow(Math.abs(vector2.get(i)), 2);
            }
            // System.out.println("Vect1: "+vector1.get(i)+" Vect2: "+vector2.get(i));
            // System.out.println("Magnitude Vect1: "+magnitudeVect1+" Magnitude Vect2:
            // "+magnitudeVect2+ " DotProduct: "+dotProduct);
        }

        // Divide the dotProduct by the square root of each vector and multiplied together
        if (Double.isNaN(dotProduct / (Math.sqrt(magnitudeVect1) * Math.sqrt(magnitudeVect2)))) {
            return 0;
        }
        return dotProduct / (Math.sqrt(magnitudeVect1) * Math.sqrt(magnitudeVect2));
    }
}
