package searchengine;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class InvertedIndexHashMap extends InvertedIndex {
	
    public InvertedIndexHashMap() {
        this.map = new HashMap<>();
    }
    
    @Override
    public String toString() {	
    	Set<Website> allSites = new HashSet<>();
    	for (Set<Website> set : map.values()) {
    		allSites.addAll(set);
    	}
    			
        return "InvertedIndexHashMap{" +
                "sites=" + allSites.toString() +
                '}';
    }
}
