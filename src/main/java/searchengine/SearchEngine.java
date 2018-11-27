package searchengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The search engine. Upon receiving a list of websites, it performs the necessary configuration
 * (i.e. building an index and a query handler) to then be ready to receive search queries.
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
   * @param sites the list of websites
   */
  public SearchEngine(Set<Website> sites) {
    Index idx = new InvertedIndexTreeMap();
    Corpus corpus = new Corpus();
    idx.build(sites);
    corpus.build(sites);
    queryHandler = new QueryHandler(idx, corpus);
  }

  /**
   * Returns the list of websites matching the query.
   *
   * @param query the query
   * @return the list of websites matching the query
   */
  public List<Website> search(String query) {
    if (query == null || query.isEmpty()) {
      return new ArrayList<>();
    }
    List<Website> resultList = queryHandler.getMatchingWebsites(query);
    return resultList;
  }
}
