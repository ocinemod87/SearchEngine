package searchengine;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * The class holds information about the whole database (corpus) of websites. This information is
 * used by ranking methods to calculate the rank of individual websites in the corpus. The Corpus
 * class is fairly similar to the Index classes, and the two could perhaps be merged.
 */
public class Corpus {

  /**
   * A map which relates a word in the corpus to the number of times the word appear in the corpus.
   * The map is build when the corpus is instantiated. The map is made package private to allow
   * convenient access for external ranking methods.
   */
  Map<String, Integer> index; // package private. Word is mapped to the number of times it appear in
                              // the corpus.

  /**
   * A map which relates a word in the corpus to the number of times the word appear in the corpus.
   * The map is build when the corpus is instantiated. The map is made package private to allow
   * convenient access for external ranking methods.
   */
  int wordSize = 0; // package private

  /**
   * A set containing all the websites in the corpus. This set must be given to the Corpus when it
   * is instantiated.
   */
  Set<Website> allSites; // all websites in the corpus.

  /**
   * A map which relates a word to the number of sites/documents in which the word appears. The map
   * is build when the corpus is instantiated. The map is made package private to allow convenient
   * access for external ranking methods.
   */
  Map<String, Integer> appearInSitesMap; // package private


  /**
   * The total number of websites in the corpus.
   */
  int totalNumberOfSites;


  /**
   * A constructor that instantiates the corpus.
   * 
   * NOTE: Most of the fields described above is first instantiated when the build method is
   * invoked. This is because the build method is potentially an "expensive" operation, and
   * therefore the programmer should actively think about when to invoke the method. If the build
   * method was "hid" in the constructor, this concern is more likely to be overlooked. This is
   * similar to the way the InvertedIndex is designed.
   * 
   * @param sites the sites that can be searched by the search engine. The sites must be supplied
   *        from an external database.
   */
  public Corpus(Set<Website> sites) {
    index = new TreeMap<>();
    appearInSitesMap = new TreeMap<>();
    allSites = sites;
    totalNumberOfSites = allSites.size();
  }

  // build the map of words
  public void build() {
    for (Website site : allSites) {

      // get unique words from the site, and add 1 to the number of sites that the word appear in.
      // NB: according to "Effective Java" by Joshua Bloch the forEach terminator shouldn't be used
      // in cases like this (Item ...). Code should probably be refactored.
      site.getWords().stream().distinct().forEach(w -> {
        if (appearInSitesMap.containsKey(w)) {
          appearInSitesMap.put(w, appearInSitesMap.get(w) + 1);
        } else {
          appearInSitesMap.put(w, 1);
        }
      });

      // take the number of times a word appear on the site,
      // and add it to the map that counts the number of times the word appears in the corpus.
      site.getWords().stream().distinct().forEach(w -> {
        // number of times the word occur on site.
        int n = site.wordMap.get(w);
        assert n >= 1 : "n should always be at least one"; // word is on the site, and hence a
                                                           // key/value pair should exist in the
                                                           // map.

        // update the "index" which counts word occurrences in the whole corpus.
        if (this.index.containsKey(w)) {
          this.index.put(w, this.index.get(w) + n);
        } else {
          this.index.put(w, n);
        }
        wordSize += n;
      });
    }
    assert wordSize == allSites.stream().map(Website::getWordSize).reduce(0,
        (total, count) -> total + count); // sanity check, that wordSize is calculated correctly.
  }
}
