package searchengine;

public class TFIDFScore implements Score{
  
  // this class has no fields! so perhaps it should be just a utility class?

  private double rankSingleTFIDF(Website site, Corpus corpus, String word) {
    
    // score single word/term according to the document frequency and inverse corpus frequency.
    int wordSize = site.getWordSize();
    double wordCount = (double) site.wordMap.get(word); // number of times word appear on website.
    double siteCount = (double) corpus.appearInSitesMap.get(word); // number of times the word
                                                                   // appears in a corpus website.
    return (wordCount / wordSize) * Math.log(corpus.totalNumberOfSites / siteCount);
  }

  private double rankQueryTFIDF(Website site, Corpus corpus, String query) {

    double maxScoreSubQuery = 0;

    // split the query into subqueries
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

      if (sum > maxScoreSubQuery) {
        maxScoreSubQuery = sum;
      }
    }
    return maxScoreSubQuery;
  }
  
  // setRank(). Rank the site according to the whole query.
  public double rank(Website site, Corpus corpus, String query) {
      return rankQueryTFIDF(site, corpus, query);
  }

}
