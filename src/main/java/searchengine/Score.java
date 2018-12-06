package searchengine;

public interface Score {
  
  Double rank(Website site, Corpus corpus, String query);

}
