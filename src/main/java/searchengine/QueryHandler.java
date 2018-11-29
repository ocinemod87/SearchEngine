package searchengine;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is responsible for answering queries to our search engine.
 */

public class QueryHandler {

  /** The index the QueryHandler uses for answering queries. */
  private Index idx = null;

//  /** The corpus */
//  private Corpus corpus = null;

  /**
   * The constructor
   * 
   * @param idx The index used by the QueryHandler.
   */
  public QueryHandler(Index idx) {
    this.idx = idx;
  }

//  /**
//   * Another constructor
//   * 
//   * @param idx The index used by the QueryHandler.
//   */
//  public QueryHandler(Index idx, Corpus corpus) {
//    this.idx = idx;
//    this.corpus = corpus;
//  }

  /**
   * getMachingWebsites answers queries of the type "subquery1 OR subquery2 OR subquery3 ...". A
   * "subquery" has the form "word1 word2 word3 ...". A website matches a subquery if all the words
   * occur on the website. A website matches the whole query, if it matches at least one subquery.
   *
   * @param query the query string
   * @return the set of websites that matches the query
   */
  public Set<Website> getMatchingWebsites(String query) {
    Set<Website> results = new HashSet<>();

    String[] subquerys = query.split("\\sOR\\s");
    for (int j = 0; j < subquerys.length; j++) {
      String[] words = subquerys[j].split("\\s");
      results.addAll(intersect(words));
    }
    return results;
  }

  private Set<Website> intersect(String[] words) {
    Set<Website> results = new HashSet<>();

    // intersection of sets of websites containing the words
    results.addAll(idx.lookup(words[0]));
    for (int i = 1; i < words.length; i++) {
      results.retainAll(idx.lookup(words[i]));
    }
    return results;
  }

}
