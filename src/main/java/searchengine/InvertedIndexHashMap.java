package searchengine;
import java.util.HashMap;

public class InvertedIndexHashMap extends InvertedIndex {
    public InvertedIndexHashMap() {
        this.map = new HashMap<>();
    }
}
