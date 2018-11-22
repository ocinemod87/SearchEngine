package searchengine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InvertedIndexTreeMap extends InvertedIndex {

    public InvertedIndexTreeMap() {

        this.map = new TreeMap<>();
    }
}
