package searchengine;
import java.util.List;

/**
 * The index data structure provides a way to build an index from
 * a list of websites. It allows to lookup the websites that contain a query word.
 *
 * @author Martin Aumüller
 */
public interface Index {
    /**
     * The build method processes a list of websites into the index data structure.
     *
     * @param sites The list of websites that should be indexed
     */
    void build(List<Website> sites);

    /**
     * Given a query string, returns a list of all websites that contain the query.
     * @param query The query
     * @return the list of websites that contains the query word.
     */

    List<Website> lookup(String query);
}
