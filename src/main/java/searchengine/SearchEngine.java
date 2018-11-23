package searchengine;

import java.util.List;
import java.util.ArrayList;

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
  public SearchEngine(List<Website> sites) {
    Index idx = new InvertedIndexTreeMap();
    idx.build(sites);
    queryHandler = new QueryHandler(idx);
  }

  /**
   * Returns the list of websites matching the query.
   *
   * @param query the query
   * @return the list of websites matching the query
   */
  public List<Website> search(String query) {
    if (query == null || query.isEmpty()) {
      return new ArrayList<Website>();
    }
    List<Website> resultList = queryHandler.getMatchingWebsites(query);
    return resultList;
  }
}
