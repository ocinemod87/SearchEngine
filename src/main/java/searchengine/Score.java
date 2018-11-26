package searchengine;

public interface Score {

  public double rankWord(Website site, Corpus corpus, String word);
  
  //public double rankQuery(Website site, Corpus corpus, String query);
  
  
}
