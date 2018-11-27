package searchengine;

import java.util.Collection;

public interface Score {

  public static double rankWord(Website site, Corpus corpus, String word) {

    // score single word/term according to the TFIDF
    int wordSize = site.getWordSize();
    double wordCount = (double) site.wordCount.get(word);
    double corpusCount = (double) corpus.index.get(word);
    return (wordCount / wordSize) / (corpusCount / corpus.wordSize); // not correct math yet.
  }

  // rank the sites according to the single word.
  public static void rankSites(Collection<Website> sites, Corpus corpus, String word) {
    for (Website site : sites) {
      site.setRank(rankWord(site, corpus, word));
    }
  }

  // rank the sites according to the whole query.
  public static void rankQuery(Collection<Website> sites, Corpus corpus, String query) {

  }

}
