package searchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


/**
 * The InvertedIndex data structure provides a way to build an index from a list of websites. It
 * allows the option to lookup the websites that contain a query word. The index is implemented using a {@code Map}.
 */
public abstract class InvertedIndex implements Index {

  /**
   * The data structure of the index.
   */
  protected Map<String, Set<Website>> map;

  /**
   * Builds an inverted index, mapping a word to the {@code Websites} containing it, given a {@code Set<Websites>}
   *
   * @param sites A set of websites to be indexed.
   */
  @Override
  public void build(Set<Website> sites) {

    if (sites == null) {
      throw new IllegalArgumentException();
    }

    if (sites.contains(null)) {
      throw new IllegalArgumentException();
    }

    for (Website site : sites) {
      for (String word : site.getWords()) {

        // check if the map contains the key word
        if (map.containsKey(word)) {
          // if yes takes the value (a set of websites) add the site to it.
          // if the website already is in the value-set nothing happens.
          map.get(word).add(site);
        } else {
          // If not, create a new HashSet at the position of the key
          Set<Website> webTemp = new HashSet<>();
          webTemp.add(site);
          map.put(word, webTemp);
        }
      }
    }
  }
  /**
   * Returns the set of websites which match the {@code query}, returns an empty
   * {@code Set} if the {@code query} is not contained in the {@code Map}.
   * 
   * @param query The query to be looked up.
   * @return the {@code Set<Website>} that contain the query word.
   */
  @Override
  public Set<Website> lookup(String query) {
    if (map.containsKey(query)) {
      return map.get(query);
    } else {
      return Collections.emptySet();
    }
  }
}
