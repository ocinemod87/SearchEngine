package searchengine;

public class TFScore implements Score {

  // Rank the site according to the whole query, and the corpus.
  @Override
  public Double rank(Website site, Corpus corpus, String query) {
    return rankQueryTF(site, corpus, query);
  }


  /**
   * Rank a single website according to a single word. Ranking algorithm is TF
   * 
   * @param site a single website that will be ranked.
   * @param corpus of all websites that the search engine knows about. NB: this is actually not used
   *        in this simple algorithm. But to comply with the Score interface the corpus parameter
   *        still needs to be there (ugly, we know).
   * @param a single word from the search query.
   * @return the rank of the site. Rank will always be non-negative.
   */
  private Double rankSingleTF(Website site, Corpus corpus, String word) {

    // score single word/term according to the document frequency and inverse corpus frequency.
    int wordSize = site.getWordSize();

    // number of times word appear on website, i.e the term site count.
    double wordCount = (double) site.wordMap.get(word);

    // the site term frequency.
    return (wordCount / wordSize);
  }


  /**
   * Rank the site according to the whole query. I.e calculate and set the rank field of a website.
   * The ranking algorithm is TF.
   * 
   * @param site the website that are to be ranked.
   * @param corpus the corpus of all websites.
   * @param query the search query string.
   * 
   * @return Double value
   */
  private Double rankQueryTF(Website site, Corpus corpus, String query) {

    double maxScoreSubQuery = 0;

    // split the query into subqueries
    // FIX ME: this should be done exactly the same way as in the queryhandler!
    String[] subquerys = query.split("\\sOR\\s");
    for (int j = 0; j < subquerys.length; j++) {
      String[] words = subquerys[j].split("\\s");


      // sum the scores for the individual words in the subquery.
      double sum = 0;
      for (int k = 0; k < words.length; k++) {
        if (site.getWords().contains(words[k])) {
          sum += rankSingleTF(site, corpus, words[k]);
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
