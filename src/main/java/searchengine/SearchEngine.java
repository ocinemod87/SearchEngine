package searchengine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The search engine. Upon receiving a list of websites, it performs the necessary configuration
 * (i.e. building an index and a query handler) to then be ready to receive search queries.
 *
 * @author Willard Rafnsson
 * @author Martin Aumüller
 * @author Leonid Rusnac
 */
public class SearchEngine {

  private Corpus corpus;
  private QueryHandler queryHandler;

  /**
   * Creates a {@code SearchEngine} object from a list of websites.
   *
   * @param sites the list of websites
   */
  public SearchEngine(Set<Website> sites) {
    Index idx = new InvertedIndexTreeMap();
    idx.build(sites);
    corpus = new Corpus(sites);
    corpus.build(); // corpus is kept in SearchEngine since this is where ranking is done.
    queryHandler = new QueryHandler(idx);  // index is passed to QueryHandler since this is where lookup is done.
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
    List<Website> results = queryHandler.getMatchingWebsites(query);
    
    // the websites are ordered according to rank. 
    // The rank is calculated by the Score that belongs to the website.  
    return orderWebsites(results, query);
  }
  
  
  private List<Website> orderWebsites(List<Website> resultList, String query) {
    
    // create a nested Comparator class
    class RankComparator implements Comparator<Website>{
      public int compare(Website site, Website otherSite) {
        return site.getRank(query, corpus).compareTo(otherSite.getRank(query, corpus));
      }
    }
    
    // sort the websites according to their rank.
    resultList.sort(new RankComparator().reversed());  // why do we need to reverse? 
    
    return resultList;
  }
  
}
