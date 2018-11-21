package searchengine;
import java.util.TreeMap;

public class InvertedIndexTreeMap extends InvertedIndex {
    public InvertedIndexTreeMap() {
        this.map = new TreeMap<>();
    }
}
