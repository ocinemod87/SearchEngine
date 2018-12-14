package searchengine;

import java.util.TreeMap;
/**
 * A {@code InvertedIndexTreeMap} is an subclass of the {@code InvertedIndex} superclass, which is instantiated using a {@code TreeMap}.
 *
 * @author André Mortensen Kobæk
 * @author Domenico Villani
 * @author Flemming Westberg
 * @author Mikkel Buch Smedemand
 */
public class InvertedIndexTreeMap extends InvertedIndex {

  /* Creates a {@code InvertedIndexTreeMap} using a {@code TreeMap} */
  public InvertedIndexTreeMap() {
    this.map = new TreeMap<>();
  }
}
