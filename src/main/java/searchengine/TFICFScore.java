package searchengine;

public class TFICFScore implements Score {

  @Override
  public Double rank(Website site, Corpus corpus, String query) {
    return rankQueryTFICF(site, corpus, query);
  }

  private Double rankSingleTFICF(Website site, Corpus corpus, String word) {
    
    // score single word/term according to the document frequency and inverse corpus frequency.
    int wordSize = site.getWordSize(); // number of words on the site.
    double wordCount = (double) site.wordMap.get(word); // number of times word appear on website.
    double corpusCount = (double) corpus.index.get(word); // number of times word appear in corpus.
    int corpusSize = corpus.wordSize; // number of words in the corpus. 
        
    return (wordCount / wordSize) * Math.log(corpusSize/corpusCount);
  }

  private Double rankQueryTFICF(Website site, Corpus corpus, String query) {

    double maxScoreSubQuery = 0;

    // split the query into subqueries
    String[] subquerys = query.split("\\sOR\\s");
    for (int j = 0; j < subquerys.length; j++) {
      String[] words = subquerys[j].split("\\s");

      // sum the scores for the individual words in the subquery.
      double sum = 0;
      for (int k = 0; k < words.length; k++) {
        if (site.getWords().contains(words[k])) {
          sum += rankSingleTFICF(site, corpus, words[k]);
        }
      }

      if (sum > maxScoreSubQuery) {
        maxScoreSubQuery = sum;
      }
    }
    return maxScoreSubQuery;
  }
}
