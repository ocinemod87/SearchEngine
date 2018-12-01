package searchengine;

public interface Score {
  
  double rank(Website site, Corpus corpus, String query);

}
