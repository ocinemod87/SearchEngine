package searchengine;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Corpus { // could fairly easy be made to implement Index, but cannot see why it should.
  Map<String, Integer> index; // package private
  int wordSize = 0; // package private

  public Corpus() {
    this.index = new TreeMap<>();
  }

  // build the map of words
  public void build(Collection<Website> sites) {
    for (Website site : sites) {
      for (String word : site.getWords()) {

        // number of times the word occur on website site.
        int n = site.wordCount.get(word);

        // update the "index"
        if (this.index.containsKey(word)) {
          this.index.put(word, this.index.get(word) + n);
        } else {
          this.index.put(word, n);
        }
        wordSize += n;
      }
    }
  }

}
