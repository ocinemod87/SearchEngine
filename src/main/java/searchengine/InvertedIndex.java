package searchengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The InvertedIndex data structure provides a way to build an index from a list of websites. It
 * allows to lookup the websites that contain a query word. The InvertedIndex maps query words to
 * websites allowing for a more effective lookup
 */
public abstract class InvertedIndex implements Index {

  protected Map<String, Collection<Website>> map;

  /**
   * Takes a list of websites and creates a map. The keys are the words contained in these websites,
   * the value is a list of all the websites containing that key.
   * 
   * @param sites The list of websites that should be indexed
   */
  @Override
  public void build(Collection<Website> sites) {

    for (Website site : sites) {
      for (String word : site.getWords()) {

        // Check if the map contains the current key
        if (map.containsKey(word)) {
          // If so, map the current site to this key
          map.get(word).add(site);
        } else {
          // If not, create a new ArrayList at the position of the key
          List<Website> webTemp = new ArrayList<>();
          webTemp.add(site);
          map.put(word, webTemp);
        }
      }
    }
  }

  /**
   * Returns the collection of websites mapped to the query string, returns an empty
   * {@code ArrayList} if the query string is not contained in the indexed map.
   * 
   * @param query the query string
   * @return a {@code Collection<Website>} of websites that contain the query word
   */
  @Override
  public Collection<Website> lookup(String query) {

    if (map.containsKey(query)) {
      return map.get(query);
    }
    // Return an empty ArrayList
    return new ArrayList<>();
  }
}
