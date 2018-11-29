package searchengine;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/** This class holds information about the whole database (corpus) of websites.
 * It is fairly similar to the Index classes. 
 */
public class Corpus { 
  Map<String, Integer> index; // package private
  int wordSize = 0; // package private
  Set<Website> allSites;  // all websites in the corpus.
  Map<String, Integer> appearInSitesMap; // package private
  int totalNumberOfSites;
  
  public Corpus(Set<Website> sites) {
    index = new TreeMap<>();
    appearInSitesMap = new TreeMap<>();
    allSites = sites; 
  }

  // build the map of words
  public void build() {
    for (Website site : allSites) {
      
      // get unique words from the site, and add 1 to the number of sites that the word appear in.
      site.getWords().stream()
          .distinct()
          .forEach( w -> {
            if (appearInSitesMap.containsKey(w)) {
              appearInSitesMap.put(w, appearInSitesMap.get(w) + 1);
            } else {
              appearInSitesMap.put(w, 1);              
            }
          });   
      
      for (String word : site.getWords()) {

        // number of times the word occur on website site. 
        int n = site.wordMap.get(word); // Always >= 1.
        assert n >= 1 : "n should always be at least one";

        // update the "index"
        if (this.index.containsKey(word)) {
          this.index.put(word, this.index.get(word) + n);
        } else {
          this.index.put(word, n);
        }
        wordSize += n;
      }
    }
    totalNumberOfSites = allSites.size();
  }
}