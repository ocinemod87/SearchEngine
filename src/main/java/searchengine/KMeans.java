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

public class KMeans {
    /**The list of website into the dataset*/
    private List<Website> dataset;
    /**The list of total words into the dataset*/
    private SortedSet<String> totalWords;
    /**The list of centroids created*/
    private List<Centroid> centroids;
    /**A list of old centroid used to be compared with the new one*/
    private List<Centroid> oldCentroids;
    /**The list of all the vectors*/
    private List<Vector> vectors;
    /**A cosine similarity object to calculate cosine similarity*/
    private CosineSimilarity cosineSimilarity;
    /**The corpus used for the kmeans*/
    private Corpus corpus;
    /**The score used in the kmeans*/
    private Score score;

    /**
     * Takes a list of Website a Corpus instance, a Score instance and it assigns them to the
     * respective objects declared int the class
     * 
     * @param dataset a List of Website
     * @param corpus  Corpus
     * @param score   Score
     */
    public KMeans(List<Website> dataset, Corpus corpus, Score score) {
        this.dataset = dataset;
        totalWords = new TreeSet<>();
        centroids = new ArrayList<>();
        vectors = new ArrayList<>();
        cosineSimilarity = new CosineSimilarity();
        oldCentroids = new ArrayList<>();
        this.corpus = corpus;
        this.score = score;
    }

    /**
     * It starts the K-means algorithm performing the initial settings and then getting into the
     * loop which recalculates centroids and every time reassigns the websites to the new calculated
     * centroids until the n and n-1 iterations have the same centroids as result.
     * 
     * @param k the number of cluster to create
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
                    Double similarity = cosineSimilarity.calculateCS(
                            centroids.get(c).getCentroidValues(), vectors.get(v).getVectorValues());
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
            for (Centroid c : centroids) {
                oldCentroids.add(new Centroid(c));
            }


            // If the list of website assigned to the centroid is not empty
            // if(!centroids.get(c).getWebsiteVectors().isEmpty()){

            for (int z = 0; z < centroids.size(); z++) {
                System.out.println("---------Centroid: " + centroids.get(z).getClusterName()
                        + "--------------");
                for (int i = 0; i < centroids.get(z).getWebsiteVectors().size(); i++) {
                    System.out.println("Centroid " + centroids.get(z).getClusterName()
                            + " Vector web: "
                            + centroids.get(z).getWebsiteVectors().get(i).getWebsite().getTitle()
                            + " distance: "
                            + cosineSimilarity.calculateCS(centroids.get(z).getCentroidValues(),
                                    centroids.get(z).getWebsiteVectors().get(i).getVectorValues()));
                }
            }

            // Recalculate the centroids
            for (int c = 0; c < centroids.size(); c++) {

                ArrayList<Double> temporaryCentroid = new ArrayList<>();

                // For every word in the dataset
                for (int w = 0; w < totalWords.size(); w++) {
                    double tempVector = 0;
                    double tempCentroidValue = 0;
                    // For every vector
                    for (int v = 0; v < centroids.get(c).getWebsiteVectors().size(); v++) {
                        // System.out.println("Recalculating CENTROI size:
                        // "+centroids.get(c).getWebsiteVectors().size()+" Iteration: "+v);

                        // Add the value of a single word from every vector into a temporary var
                        if (centroids.get(c).getWebsiteVectors().get(v).getVectorValues().get(w)
                                .isNaN()
                                || centroids.get(c).getWebsiteVectors().get(v).getVectorValues()
                                        .get(w) == null) {
                            // Do nothing
                        } else {
                            tempVector += Math.abs(centroids.get(c).getWebsiteVectors().get(v)
                                    .getVectorValues().get(w));
                        }
                    }

                    // Calculate temporary centroid median and add it to the temporary vector
                    // Add the value of the temporary divided by the number of vectors into the
                    // centroid
                    // to a single temporary var
                    tempCentroidValue =
                            Math.abs(tempVector / centroids.get(c).getWebsiteVectors().size());
                    // System.out.println("TempVector: "+ tempVector+ " Divided by:
                    // "+centroids.get(c).getWebsiteVectors().size()+ " Result:
                    // "+tempCentroidValue);

                    temporaryCentroid.add(tempCentroidValue);
                }

                // Add the vector resetting the centroid
                centroids.get(c).setNewValues(temporaryCentroid);
                // tempCentroidValue = tempCentroidValue/totalWords.size();
            }


            if (compareCentroids(centroids, oldCentroids)) {
                for (Centroid c : centroids) {
                    c.clearListOfWebsites();
                }
            } else {
                condition = false;
            }
            iteration++;
        }

        // Debug information on the Terminal
        for (Centroid c : centroids) {
            System.out.println("---------Centroid: " + c.getClusterName() + "--------------");
            for (Vector v : c.getWebsiteVectors()) {
                System.out.println("Website: " + v.getWebsite().getTitle() + " distance: "
                        + cosineSimilarity.calculateCS(c.getCentroidValues(), v.getVectorValues()));
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
    public boolean compareCentroids(List<Centroid> centroids1, List<Centroid> centroids2) {

        int compared = 0;

        for (int i = 0; i < centroids1.size(); i++) {
            for (int x = 0; x < centroids1.get(i).getCentroidValues().size(); x++) {

                Double a = centroids1.get(i).getCentroidValues().get(x);
                Double b = centroids2.get(i).getCentroidValues().get(x);
                if (a.isNaN()) {
                    a = 0.0;
                }
                if (b.isNaN()) {
                    b = 0.0;
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
        for (Double d : distance) {
            System.out.println("Distance: " + d);
        }
        System.out.println("Chosen distance: " + Collections.max(distance));
        // Retrieves the index position of the maximum value in the list and assign it to an int to
        // be returned
        index = distance.indexOf(maxValue);
        System.out.println("Chosen distance: " + Collections.max(distance) + " Chosen index: "
                + distance.indexOf(maxValue));

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
     * Fore every Website in the dataset creates a Vector containing the Website and calculating the
     * Tf-Idf value of every word contained into the Website. In case the word from the dataset is
     * not contained into the Website its value is 0
     */
    public void createVectors() {
        // for every website calculate its vector-representation according to database/corpus.
        for (Website website : dataset) {
            // System.out.println(website.toString());
            Vector vector = new Vector(website);
            for (String word : totalWords) {

                if (website.containsWord(word)) {
                    // calculate a score and put it in the vector.
                    vector.addVectorValue(word, score.rankSingle(website, corpus, word));
                } else {
                    vector.addVectorValue(word, 0.0);
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
    public List<Centroid> calculateInitialCentroids(int cardinality) {
        List<Centroid> initialCentroids = new ArrayList<>();
        List<Double> distance = new ArrayList<>(); // Not used?
        int x = 0;

        while (x < cardinality) {
            String clusterName = "cluster " + x;
            Centroid c = randomPoint(clusterName);
            initialCentroids.add(c);
            x++;
        }

        for (int z = 0; z < initialCentroids.size(); z++) {
            for (int i = 0; i < vectors.size(); i++) {
                System.out.println("Initial Centroid " + initialCentroids.get(z).getClusterName()
                        + " Vector web: " + vectors.get(i).getWebsite().getTitle() + " distance: "
                        + cosineSimilarity.calculateCS(initialCentroids.get(z).getCentroidValues(),
                                vectors.get(i).getVectorValues()));
            }
        }

        return initialCentroids;
    }

    /**
     * Takes a string as name for the cluster, generates a random integer between zero and the size
     * of the dataset and takes a vector from the vectors list using that randomly generated int.
     * Then takes the value of the randomly chosen vector and assaign them to a new Centroid
     * instance with the String name. The new instance is returned from the method.
     * 
     * @param clusterName Stirng
     * @return Centroid
     */
    public Centroid randomPoint(String clusterName) {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(dataset.size());
        Vector v = vectors.get(index);
        System.out.println("Initial Cluster random: " + v.getWebsite().getTitle());
        return new Centroid(v.getVectorValues(), clusterName);
    }
}

