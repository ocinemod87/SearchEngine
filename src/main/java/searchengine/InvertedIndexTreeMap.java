package searchengine;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class InvertedIndexTreeMap extends InvertedIndex {

  public InvertedIndexTreeMap() {
    this.map = new TreeMap<>();
  }

  @Override
  public String toString() {
    Set<Website> allSites = new HashSet<>();
    for (Set<Website> set : map.values()) {
      allSites.addAll(set);
    }

    return "InvertedIndexTreeMap{" + "sites=" + allSites.toString() + '}';
  }
}
