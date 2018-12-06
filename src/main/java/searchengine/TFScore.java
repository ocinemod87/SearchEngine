package searchengine;

public class TFScore implements Score {

  @Override
  public Double rank(Website site, Corpus corpus, String query) {
    return rankQueryTF(site, corpus, query);
  }
  
private Double rankSingleTF(Website site, Corpus corpus, String word) {
    
    // score single word/term according to the document frequency and inverse corpus frequency.
    int wordSize = site.getWordSize();
    double wordCount = (double) site.wordMap.get(word); // number of times word appear on website.
    return (wordCount / wordSize);
  }

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

      if (sum > maxScoreSubQuery) {
        maxScoreSubQuery = sum;
      }
    }
    return maxScoreSubQuery;
  }
}
