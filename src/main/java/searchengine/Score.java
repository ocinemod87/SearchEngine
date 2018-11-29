package searchengine;

import java.util.Set;

public interface Score {

  public static double rankSingleCustom(Website site, Corpus corpus, String word) { // should probably
                                                                                  // be a private
                                                                                  // method, but
                                                                                  // this is not
                                                                                  // allowed in an
                                                                                  // interface.

    // score single word/term according to the document frequency and inverse corpus frequency.
    int wordSize = site.getWordSize();
    double wordCount = (double) site.wordMap.get(word); // number of times word appear on website.
    double corpusCount = (double) corpus.index.get(word); // number of times word appear in corpus
                                                          // of websites.
    return (wordCount / wordSize) * Math.log(corpus.wordSize / corpusCount); // not correct math
                                                                             // yet.
  }

  public static double rankSingleTFIDF(Website site, Corpus corpus, String word) {

    // score single word/term according to the document frequency and inverse corpus frequency.
    int wordSize = site.getWordSize();
    double wordCount = (double) site.wordMap.get(word); // number of times word appear on website.
    double siteCount = (double) corpus.appearInSitesMap.get(word); // number of times the word
                                                                   // appears in a corpus website.
    return (wordCount / wordSize) * Math.log(corpus.totalNumberOfSites / siteCount);
  }

  public static double rankQueryTFIDF(Website site, Corpus corpus, String query) {

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
  
  
  // setRank(). Rank the sites according to the whole query.
  public static void rankSites(Set<Website> sites, Corpus corpus, String query) {
    for (Website site : sites) {
      site.setRank(rankQueryTFIDF(site, corpus, query));
    }
  }

}
