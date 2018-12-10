package searchengine;

public class TFIDFScore implements Score {

  // Rank the site according to the whole query, and the Corpus.
  @Override
  public Double rank(Website site, Corpus corpus, String query) {
    return rankQueryTFIDF(site, corpus, query);
  }


  /**
   * Rank a single website according to a single word. Ranking algorithm is TFIDF.
   * 
   * @param site a single website that will be ranked.
   * @param corpus of all websites that the search engine knows about.
   * @param a single word from the search query.
   * @return the rank of the site. Rank will always be non-negative.
   */
  private Double rankSingleTFIDF(Website site, Corpus corpus, String word) {

    // number of words on the site.
    int wordSize = site.getWordSize();

    // number of times word appear on website, i.e the term site count.
    double wordCount = (double) site.wordMap.get(word);

    // number of times word appear on a website in the corpus, i.e the site/document count.
    double siteCount = (double) corpus.appearInSitesMap.get(word);

    // site frequency times logarithm to inverse corpus site/document frequency.
    return (wordCount / wordSize) * Math.log(corpus.totalNumberOfSites / siteCount);
  }


  /**
   * Rank the site according to the whole query. I.e calculate and set the rank field of a website.
   * The ranking algorithm is TFIDF.
   * 
   * @param site the website that are to be ranked.
   * @param corpus the corpus of all websites.
   * @param query the search query string.
   * 
   * @return Double value
   */
  private Double rankQueryTFIDF(Website site, Corpus corpus, String query) {

    double maxScoreSubQuery = 0;

    // split the query into subqueries
    // FIX ME!!. This should be changed to be aligned with how the query is split in queryHandler.
    String[] subquerys = query.split("\\sOR\\s");
    for (int j = 0; j < subquerys.length; j++) {
      String[] words = subquerys[j].split("\\s");

      // sum the scores for the individual words in the subquery.
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



}
