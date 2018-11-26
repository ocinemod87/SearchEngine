package searchengine;

public class TFScore implements Score {

  @Override
  public double rank(Website site, Corpus corpus, String word) {
    // score single word
    double wordCount = (double) site.wordCount.get(word);
    return wordCount/site.getWordSize(); 
  }

}
