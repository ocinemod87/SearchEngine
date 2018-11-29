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
    Set<Website> results = queryHandler.getMatchingWebsites(query);
    
    // rank the websites that matches the query
    Score.rankSites(results, this.corpus, query); // using the static method Score.rankSites, maybe not be OO-style.
    
    // OBS: convert set of websites to a list since the sort method only works for list.
    // this can potentially take some time if many websites has been returned. 
    // But the stream could also just be limited to a fixed number.  
    List<Website> resultList = results.stream().collect(Collectors.toList());  

    // make a Comparator from the static method Comparator.comparingDouble()
    Comparator<Website> rankComparator = Comparator.comparingDouble(Website::getRank);
    resultList.sort(rankComparator.reversed()); // why do I need to reverse?
    
    return resultList;
  }
}
