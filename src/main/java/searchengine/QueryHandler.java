package searchengine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for answering queries to our search engine.
 */

public class QueryHandler {

    /** The index the QueryHandler uses for answering queries. */
    private Index idx = null;

    /**
     * The regex used to validate queries - and the corresponding {@code Pattern} and
     * {@code Matcher} objects.
     */
    private final String REGEX = "\\b([-\\w]+)\\b";
    private Pattern pattern;
    private Matcher matcher;

    /**
     * The constructor
     * 
     * @param idx The index used by the QueryHandler.
     */
    public QueryHandler(Index idx) {
        this.idx = idx;
        pattern = Pattern.compile(REGEX);
    }

    /**
     * getMachingWebsites answers queries of the type "subquery1 OR subquery2 OR subquery3 ...". A
     * "subquery" has the form "word1 word2 word3 ...". A website matches a subquery if all the
     * words occur on the website. A website matches the whole query, if it matches at least one
     * subquery.
     * 
     * The query string will be converted to lowercase to match the case of the data
     *
     * @param line the query string
     * @return the list of websites that matches the query
     */
    public List<Website> getMatchingWebsites(String line) {
        line = line.toLowerCase();
        matcher = pattern.matcher(line);
        List<Website> results = null;

        while (matcher.find()) {

            // If the list with results is null, initialize it and add all the results from the
            // first lookup
            if (results == null) {
                results = new ArrayList<>();
                results.addAll(idx.lookup(matcher.group()));
            } else {
                // Else, retain the intersection of the following sets
                results.retainAll(idx.lookup(matcher.group()));
            }
        }

        // Prevent returning null
        if (results == null) {
            results = new ArrayList<>();
        }

        return results;
    }
}
