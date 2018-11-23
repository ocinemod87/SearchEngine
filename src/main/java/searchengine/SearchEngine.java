package searchengine;

import java.util.Set;
import java.util.HashSet;

/**
 * The search engine. Upon receiving a set of websites, it performs
 * the necessary configuration (i.e. building an index and a query
 * handler) to then be ready to receive search queries.
 *
 * @author Willard Rafnsson
 * @author Martin Aumüller
 * @author Leonid Rusnac
 */
public class SearchEngine {
    private QueryHandler queryHandler;

    /**
     * Creates a {@code SearchEngine} object from a list of websites.
     *
     * @param sites the set of websites
     */
    public SearchEngine(Set<Website> sites) {
        Index idx = new InvertedIndexHashMap();
        idx.build(sites);
        queryHandler = new QueryHandler(idx);
    }

    /**
     * Returns the set of websites matching the query.
     *
     * @param query the query
     * @return the set of websites matching the query
     */
    public Set<Website> search(String query) {
        if (query == null || query.isEmpty() ) {
            return new HashSet<>();  // or perhaps return Collections.emptySet(); ??         
        }
        return queryHandler.getMatchingWebsites(query);
    }
}
