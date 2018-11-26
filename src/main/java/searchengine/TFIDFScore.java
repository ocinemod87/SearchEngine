package searchengine;

public class TFIDFScore implements Score {
  
  @Override
  public double rankWord(Website site, Corpus corpus, String word) {
    // score single word
    int wordSize = site.getWordSize(); 
    double wordCount = (double) site.wordCount.get(word);
    return (wordCount/wordSize) / (corpus.index.get(word)/corpus.getWordSize()); 
  }

}
