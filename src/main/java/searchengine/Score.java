package searchengine;

import java.util.Collection;

public interface Score {

  public static double rankWord(Website site, Corpus corpus, String word) {
  
      // score single word
      int wordSize = site.getWordSize(); 
      double wordCount = (double) site.wordCount.get(word);
      double corpusCount = (double) corpus.index.get(word);
      return (wordCount/wordSize) / (corpusCount/corpus.wordSize); 
  }

  // rank the sites according to the single word.
  public static void rankSites(Collection<Website> sites, Corpus corpus, String word) {
    for (Website site : sites) {
      site.setRank(rankWord(site, corpus, word));
    }
  }
  
  //public static double rankQuery(Website site, Corpus corpus, String query);
}
