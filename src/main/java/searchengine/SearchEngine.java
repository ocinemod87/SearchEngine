package searchengine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * The search engine. Upon receiving a list of websites, it performs the necessary configuration
 * (i.e. building an {@code Index} and a {@code QueryHandler}) to then be ready to receive search
 * queries.
 *
 * @author André Mortensen Kobæk
 * @author Domenico Villani
 * @author Flemming Westberg
 * @author Mikkel Buch Smedemand
 */
public class SearchEngine {
  /** The {@code Index} used by the {@code SearchEngine} */
  private Index idx;
  /** The {@code Corpus} used by the {@code SearchEngine} */
  private Corpus corpus;
  /** The {@code Score} used by the {@code SearchEngine} */
  private Score score;
  /** The {@code QueryHandler} used by the {@code SearchEngine} */
  private QueryHandler queryHandler;
  private KMeansMap kMeans;

  /**
   * Creates a {@code SearchEngine} object from a list of {@code websites}.
   *
   * @param sites the set of websites
   */
  public SearchEngine(Set<Website> sites) {
    idx = new InvertedIndexTreeMap();
    System.out.println("Building index...");
    idx.build(sites);

    corpus = new Corpus(sites);
    System.out.println("Building corpus...");
    corpus.build(); // corpus is kept in SearchEngine since this is where ranking is done.

    System.out.println("Building 2-gram index, this may take a while...(~30 secs for medium database.)");
    corpus.build2GramIndex(); // build 2gram inverse index, for fuzzy matching.

    score = new TFIDFScore(); // choose the scoring algorithm to use.
    queryHandler = new QueryHandler(idx, corpus, new Fuzzy(corpus));

    // Activate k-means or not
    Scanner input = new Scanner(System.in);
    System.out.println("Do you want to run the k-means algorithm (it will take a very long time for medium/large datasets)? [Y/N]");
      if (input.next().equals("Y")) {
        kMeans = new KMeansMap(new ArrayList<Website>(sites), corpus, score);
        System.out.println("Building the k-means index, this may take even longer...");
        kMeans.startKMeans(200);
        System.out.println("Assigning similar websites based on the k-means index, this might make everything crash...");
        kMeans.assignSimilarWebsites();
        System.out.println("Success");
      }
      input.close();

  }


  /**
   * Returns a {@code SearchResult} matching the query.
   *
   * @param query the query
   * 
   * @return a {@code SearchResult matching the query}
   */
  public List<Website> search(String query) {
    if (query == null || query.isEmpty()) {
      return new ArrayList<>();
    }

    List<Website> results = queryHandler.getMatchingWebsites(query);
    List<List<String>> structuredQuery = queryHandler.getStructuredQuery(query);

    // The websites are ordered according to rank and returned as a {@code SearchResult}.
    return orderWebsites(results, structuredQuery);
  }


  /**
   * Rank a list of websites, according to the query, also using information about the whole
   * database from corpus object.
   *
   * @param list            List of {@code websites} to be ordered according to rank.
   * @param structuredQuery The search query.
   * @return Returns the list of {@code websites} reordered according to rank.
   */
  private List<Website> orderWebsites(List<Website> list, List<List<String>> structuredQuery) {

    // create a nested Comparator class
    class RankComparator implements Comparator<Website> {
      public int compare(Website site, Website otherSite) {
        return score.rank(site, corpus, structuredQuery)
            .compareTo(score.rank(otherSite, corpus, structuredQuery));
      }
    }

    // sort the websites according to their rank.
    list.sort(new RankComparator().reversed()); // why do we need to reverse?
    return list;
  }

}
