package searchengine;
import java.util.HashSet;
import java.util.Set;

public class SimpleIndex implements Index {

    /**
     * The list of websites stored in the index.
     */
    private Set<Website> sites = null;

    /**
     * The build method processes a list of websites into the index data structure.
     *
     * @param sites The list of websites that should be indexed
     */
    @Override
    public void build(Set<Website> sites) {
        this.sites = sites;
    }

    /**
     * Given a query string, returns a list of all websites that contain the query.
     *
     * @param query The query
     * @return the list of websites that contains the query word.
     */
    @Override
    public Set<Website> lookup(String query) {
        Set<Website> result = new HashSet<>();
        for (Website w: sites) {
            if (w.containsWord(query)) {
                result.add(w);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "SimpleIndex{" +
                "sites=" + sites +
                '}';
    }
}
