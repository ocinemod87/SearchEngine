package searchengine;
import java.util.ArrayList;
import java.util.Collection;

public class SimpleIndex implements Index {

    /**
     * The list of websites stored in the index.
     */
    private Collection<Website> sites = null;

    /**
     * The build method processes a list of websites into the index data structure.
     *
     * @param sites The list of websites that should be indexed
     */
    @Override
    public void build(Collection<Website> sites) {
        this.sites = sites;
    }

    /**
     * Given a query string, returns a list of all websites that contain the query.
     *
     * @param query The query
     * @return the list of websites that contains the query word.
     */
    @Override
    public Collection<Website> lookup(String query) {
        Collection<Website> result = new ArrayList<Website>();
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
