package searchengine;

import java.util.Set;


/**
 * A utility class with static methods that ranks a specific website (or a set of websites) relative
 * to a given query and corpus.
 * 
 */
public class Score {

  /**
   * Private empty constructor. A utility class should never be instantiated.
   */
  private Score() {}



  /**
   * Rank the sites according to the whole query. I.e calculate and set the rank field of each
   * website in the supplied set. The ranking algorithm we have chosen is TFIDF.
   * 
   * @param sites a set of websites that are to be ranked.
   * @param corpus the corpus of all websites.
   * @param query the searh query.
   * 
   * @return the method has no return value. But the methods side effect is to set the rank field
   *         for the supplied set of websites.
   */
  public static void rankSites(Set<Website> sites, Corpus corpus, String query) {
    for (Website site : sites) {
      site.setRank(rankQueryTFIDF(site, corpus, query)); // determine what ranking algorithm to use.
    }
  }


  /**
   * Rank a single website according to a query.
   * 
   * @param site a single website that will be ranked.
   * @param corpus of all websites that the search engine knows about.
   * @param query the search query.
   * @return the rank of the site. Rank will always be non-negative.
   */
  private static double rankQueryTFIDF(Website site, Corpus corpus, String query) {

    double maxScoreSubQuery = 0;

    // split the query into subqueries
    // this is duplicate code, the 3 lines are also present in QueryHandler.getMathcingWebsites()
    String[] subquerys = query.split("\\sOR\\s");
    for (int j = 0; j < subquerys.length; j++) {
      String[] words = subquerys[j].split("\\s");

      // score the individual words in the subquery and sum them.
      double sum = 0;
      for (int k = 0; k < words.length; k++) {
        if (site.getWords().contains(words[k])) {
          sum += rankSingleTFIDF(site, corpus, words[k]);
        }
      }
      assert sum >= 0 : "The rank of a site should always be non-negative."; // sanity check, that
                                                                             // rank is always
                                                                             // non-negative.

      // find the subquery with the highest score. This is the score to be returned.
      if (sum > maxScoreSubQuery) {
        maxScoreSubQuery = sum;
      }
    }
    return maxScoreSubQuery;
  }

  /**
   * Rank a single website according to a single word. Score the single word/term according to the
   * document frequency and inverse corpus frequency.
   * 
   * @param site a single website that will be ranked.
   * @param corpus of all websites that the search engine knows about.
   * @param a single word from the search query.
   * @return the rank of the site. Rank will always be non-negative.
   */
  private static double rankSingleTFIDF(Website site, Corpus corpus, String word) {

    // number of words on the site.
    int wordSize = site.getWordSize();

    // number of times word appear on website, i.e the term site count.
    double wordCount = (double) site.wordMap.get(word);

    // number of times word appear in corpus, i.e the term corpus count.
    double siteCount = (double) corpus.appearInSitesMap.get(word);

    // site frequency times logarithm to inverse corpus frequency.
    return (wordCount / wordSize) * Math.log(corpus.totalNumberOfSites / siteCount);
  }


  /**
   * 
   * Rank a single website according to a single word, using a "custom" ranking method. The term
   * (site) frequency is weighted with the inverse term (corpus) frequency.
   * 
   * @param site a single website that will be ranked.
   * @param corpus of all websites that the search engine knows about.
   * @param a single word from the search query.
   * @return the rank of the site. Rank will always be non-negative.
   */
  private static double rankSingleCustom(Website site, Corpus corpus, String word) {

    // number of words on the site.
    int wordSize = site.getWordSize();

    // number of times word appear on website.
    double wordCount = (double) site.wordMap.get(word);

    // number of times word appear in corpus
    double corpusCount = (double) corpus.index.get(word);

    return (wordCount / wordSize) * Math.log(corpus.wordSize / corpusCount);
  }
}
