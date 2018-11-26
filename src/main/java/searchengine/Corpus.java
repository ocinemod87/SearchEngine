package searchengine;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Corpus { // could fairly easy be made to implement Index
  private Collection<Website> sites;
  Map<String, Integer> index; // package private
  int wordsize = 0;
  
  // what will happend if class is instatiated with empty constructor???
  // is it compile-time Error or will it call super()?

  public Corpus(Collection<Website> sites) {
    this.sites = sites;
    this.index = new TreeMap<>();
  }

  public void build() {
    for (Website site : sites) {
      for (String word : site.getWords()) {
        if (index.containsKey(word)) {
          index.put(word, index.get(word) + 1);
        } else {
          index.put(word, 1);
        }
        wordsize++;
      }
    }
  }
  
}
