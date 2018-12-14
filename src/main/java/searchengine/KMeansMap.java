package searchengine;

import java.util.*;

/**
 * The k-means algorithm implements a cluster analysis of the database. It takes an arbitrary number
 * as K, which represents the number of clusters to create. It uses Tf-Idf score to calculate the
 * relation of each website with all the words in dataset. It randomly assign K number of websites
 * as initial centroids and then calculate the distance of each website from the centroids, when it
 * finds the minumum distance (which is the higher value of cosine similarity) of a website from
 * that centroid it assigns the website to the centroid.
 *
 *  @author Domenico Villani
 *  @author Mikkel Buch Smedemand
 */

public class KMeansMap {
    /**The list of website into the dataset*/
    private List<Website> dataset;
    /**The list of total words into the dataset*/
    private SortedSet<String> totalWords;
    /**The list of centroids created*/
    private List<CentroidMap> centroids;
    /**A list of old centroid used to be compared with the new one*/
    private List<CentroidMap> oldCentroids;
    /**The list of all the vectors*/
    private List<VectorMap> vectors;
    /**A cosine similarity object to calculate cosine similarity*/
    private CosineSimilarityMap cosineSimilarity;
    /**The corpus used for the kmeans*/
    private Corpus corpus;
    /**The score used in the kmeans*/
    private Score score;

    /**
     * Takes a list of Website a Corpus instance, a Score instance and it assigns them to the
     * respective objects declared in the class
     * 
     * @param dataset a List of {@code Website}
     * @param corpus  Corpus
     * @param score   Score
     */
    public KMeansMap(List<Website> dataset, Corpus corpus, Score score) {
        this.dataset = dataset;
        totalWords = new TreeSet<>();
        centroids = new ArrayList<>();
        vectors = new ArrayList<>();
        cosineSimilarity = new CosineSimilarityMap();
        oldCentroids = new ArrayList<>();
        this.corpus = corpus;
        this.score = score;
    }

    /**
     * Starts the K-means algorithm performing the initial settings and then getting into the loop
     * which recalculates centroids and every time reassigns the websites to the new calculated
     * centroids until the n and n-1 iterations have the same centroids as result.
     * 
     * @param k the number of clusters to create
     */
    public void startKMeans(int k) {

        calculateTotalWords();
        createVectors();
        // randomize centroids
        centroids = calculateInitialCentroids(k);

        boolean condition = true;
        int iteration = 0;
        while (condition) {

            System.out.println("Into kmeans condition interation number: " + iteration);
            iteration++;
            // This needs to be performed in a bigger loop
            for (int v = 0; v < vectors.size(); v++) {
                ArrayList<Double> distance = new ArrayList<>();
                for (int c = 0; c < centroids.size(); c++) {
                    Double similarity =
                            cosineSimilarity.calculateCS(centroids.get(c).getCentroidValuesMap(),
                                    vectors.get(v).getVectorValuesMap(), totalWords);
                    distance.add(similarity);
                    // System.out.println("Centroid name: "+centroids.get(c).getClusterName()+"
                    // Website: "+vectors.get(v).getWebsite().getTitle()+" Distance: "+similarity);
                }
                int index = calculateClosestCentroid(distance);
                // System.out.println("Centroid: "+centroids.get(index).getClusterName()+ " Vector:
                // "+ vectors.get(v).getWebsite().getTitle());
                centroids.get(index).assignWebsiteVectorToCentroid(vectors.get(v));
            }

            System.out.println("Total word: " + totalWords.size());
            oldCentroids = new ArrayList<>();

            // Copy the centroids into a new List of centroids to be compared later
            for (CentroidMap c : centroids) {
                oldCentroids.add(new CentroidMap(c));
            }

            // Recalculate centroids
            for (CentroidMap c : centroids) {
                Map<String, Double> temparayCentroid = new HashMap<>();

                // For every word in the dataset
                for (String word : totalWords) {
                    double tempVector = 0;
                    double tempCentroidValue = 0;
                    for (VectorMap v : c.getWebsiteVectors()) {
                        // If the word is present in the map
                        if (v.getVectorValuesMap().containsKey(word)) {
                            // If the value of the word is not null or NaN
                            if (!v.getVectorValuesMap().get(word).isNaN()
                                    || !v.getVectorValuesMap().get(word).equals(null)) {
                                tempVector += Math.abs(v.getVectorValuesMap().get(word));
                            }
                        }
                    }
                    // Calculate temporary centroid median and add it to the temporary vector
                    // Add the value of the temporary divided by the number of vectors into the
                    // centroid
                    // to a single temporary var
                    tempCentroidValue = Math.abs(tempVector / c.getWebsiteVectors().size());
                    temparayCentroid.put(word, tempCentroidValue);
                }
            }


            if (compareCentroids(centroids, oldCentroids)) {
                for (CentroidMap c : centroids) {
                    c.clearListOfWebsites();
                }
            } else {
                condition = false;
            }
            iteration++;
        }

        // Debug information on the Terminal
        for (CentroidMap c : centroids) {
            int b = 0;
            System.out.println("---------Centroid: " + c.getClusterName() + "--------------");
            for (VectorMap v : c.getWebsiteVectors()) {
                System.out.println("Website: " + v.getWebsite().getTitle() + " distance: "
                        + cosineSimilarity.calculateCS(c.getCentroidValuesMap(),
                                v.getVectorValuesMap(), totalWords));
                b++;
            }
        }
    }

    /**
     * Compare two Centroid to check that are the same or not, this similarity has a tolerance of
     * 0.001. If the values of the two Centroid are the same it return false that stops the loop
     * into startKmeans method, otherwise it returns true
     * @param centroids1 List
     * @param centroids2 List
     * @return boolean
     */
    public boolean compareCentroids(List<CentroidMap> centroids1, List<CentroidMap> centroids2) {

        int compared = 0;

        for (int i = 0; i < centroids1.size(); i++) {
            Double a = 0.0;
            Double b = 0.0;
            for (String word : centroids1.get(i).getCentroidValuesMap().keySet()) {
                if (centroids1.get(i).getCentroidValuesMap().containsKey(word)) {
                    a = centroids1.get(i).getCentroidValuesMap().get(word);
                }
                if (centroids2.get(i).getCentroidValuesMap().containsKey(word)) {
                    b = centroids2.get(i).getCentroidValuesMap().get(word);
                }
                Double tolerance = 0.001;

                if (Math.abs(a - b) < tolerance) {
                    compared += 0;
                } else {
                    compared += 1;
                }
            }
        }
        System.out.println("Compared = " + compared);

        if (compared > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Takes a list of Double containing the distance from a Centroid to a Vector and returns the
     * index of the closest one
     * 
     * @param distance List of Double
     * @return int
     */
    public int calculateClosestCentroid(List<Double> distance) {
        int index = 0;

        // Takes the list and retrieves the maximum value in the list
        Double maxValue = Collections.max(distance);
        // Retrieves the index position of the maximum value in the list and assign it to an int to
        // be returned
        index = distance.indexOf(maxValue);

        return index;
    }

    /**
     * Calculate the total number of words in the dataset
     */
    public void calculateTotalWords() {
        for (Website site : dataset) {
            for (String word : site.getWords()) {
                totalWords.add(word);
            }
        }
    }

    /**
     * For every Website in the dataset, this creates a Vector containing the Website and
     * calculating the Tf-Idf value of every word contained into the Website. In case the word from
     * the dataset is not contained into the Website its value is not added to the Vector.
     */
    public void createVectors() {
        // for every website calculate its vector-representation according to database/corpus.
        for (Website website : dataset) {
            System.out.println("Building Vector for: " + website.getTitle());
            VectorMap vector = new VectorMap(website);
            for (String word : totalWords) {

                if (website.containsWord(word)) {
                    // calculate a score and put it in the vector.
                    vector.addVectorValue(word, score.rankSingle(website, corpus, word));
                } else {
                    // do nothing
                }
            }
            vectors.add(vector);
        }
    }

    /**
     * Takes a int value called cardinality wich is the k assigned to the constructor and caculates
     * k number of centroids calling the randomPoint method and assigning its result to a new
     * istance of Centroid, then this are added to the initialCentroids list
     * 
     * @param cardinality int
     * @return List of Centroid
     */
    public List<CentroidMap> calculateInitialCentroids(int cardinality) {
        List<CentroidMap> initialCentroids = new ArrayList<>();
        int x = 0;

        while (x < cardinality) {
            String clusterName = "cluster " + x;
            CentroidMap c = randomPoint(clusterName);
            initialCentroids.add(c);
            x++;
        }

        return initialCentroids;
    }

    /**
     * Takes a string as name for the cluster, generates a random integer between zero and the size
     * of the dataset and takes a vector from the vectors list using that randomly generated int.
     * Then takes the value of the randomly chosen vector and assaign them to a new Centroid
     * instance with the String name. The new instance is returned from the method.
     * 
     * @param clusterName the name for the {@code CentroidMap} cluster
     * @return Centroid
     */
    public CentroidMap randomPoint(String clusterName) {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(dataset.size());
        VectorMap v = vectors.get(index);
        CentroidMap centroid = new CentroidMap(v.getVectorValuesMap(), clusterName);
        System.out.println("Initial Cluster random: " + v.getWebsite().getTitle()
                + " Check correctness of centroid distance from website: "
                + cosineSimilarity.calculateCS(centroid.getCentroidValuesMap(),
                        v.getVectorValuesMap(), totalWords));
        return centroid;
    }

    /**
     * Assigns the URLs of similar websites to all the {@code Website} objects contained in all the centroids
     */
    public void assignSimilarWebsites() {
        for (CentroidMap centroid : centroids) {
            List<String> similarWebsites = new ArrayList<>();

            // First, extract websites from all the we
            for (VectorMap vector : centroid.getWebsiteVectors()) {
                similarWebsites.add(vector.getWebsite().getUrl());
                System.out.println("Adding " + vector.getWebsite().getUrl()
                        + " to list of similar websites in " + centroid.getClusterName());
            }
            // Second, assign the list to each website
            for (VectorMap vector : centroid.getWebsiteVectors()) {
                vector.getWebsite().setSimilarWebsites(similarWebsites);
            }
        }
    }
}
