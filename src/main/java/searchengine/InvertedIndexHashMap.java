package searchengine;

import java.util.HashMap;
/**
 * A {@code InvertedIndexHashMap} is an subclass of the {@code InvertedIndex} superclass, which is instantiated using a {@code HashMap}.
 *
 * @author André Mortensen Kobæk
 * @author Domenico Villani
 * @author Flemming Westberg
 * @author Mikkel Buch Smedemand
 */

public class InvertedIndexHashMap extends InvertedIndex {

  /* Creates a {@code InvertedIndexHashMap} using a {@code HashMap} */
  public InvertedIndexHashMap() {
    this.map = new HashMap<>();
  }
}
