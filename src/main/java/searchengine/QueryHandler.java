package searchengine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class is responsible for answering queries to our search engine.
 */

public class QueryHandler {

  /** The index the QueryHandler uses for answering queries. */
  private Index idx = null;

  /** The corpus */
  private Corpus corpus = null;
  
  /**
   * The regex used to validate queries - and the corresponding {@code Pattern} and {@code Matcher}
   * objects.
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
   * Another constructor
   * 
   * @param idx The index used by the QueryHandler.
   */
  public QueryHandler(Index idx, Corpus corpus) {
    this.idx = idx;
    this.corpus = corpus;
    pattern = Pattern.compile(REGEX);
  }
  
  
  /**
   * getMachingWebsites answers queries of the type "subquery1 OR subquery2 OR subquery3 ...". A
   * "subquery" has the form "word1 word2 word3 ...". A website matches a subquery if all the words
   * occur on the website. A website matches the whole query, if it matches at least one subquery.
   * 
   * The query string will be converted to lowercase to match the case of the data
   *
   * @param line the query string
   * @return the list of websites that matches the query
   */
  public List<Website> getMatchingWebsites(String line) {
    line = line.toLowerCase();
    Set<Website> results = new HashSet<>();

    if (isValidInput(line)) {
      results.addAll(idx.lookup(matcher.group()));
    }
    
    // convert set of websites to a list since the sort method only works for list.
    List<Website> resultList = results.stream().collect(Collectors.toList());
    
    // rank the websites that matches the query
    Score.rankSites(resultList, corpus, line);  // using the static method Score.rankSites, maybe not OO approach
    
    // sort websites according to their rank.
    // results.sort( (site1, site2) -> Double.compare(site2.getRank(), site1.getRank()));  // using the static method Double.compare, maybe not OO approach
    
    // alternative approach to sorting the websites. 
    // make a Comparator from the static method Comparator.comparingDouble()
    Comparator<Website> rankComparator = Comparator.comparingDouble(Website::getRank); 
    resultList.sort(rankComparator.reversed());  // why do I need to reverse? take a look at ranking math again.
    
    return resultList;
  }

  /**
   * isValidInput takes a query and checks it against {@code REGEX} defining valid input
   * 
   * @param line the query string
   * @return true if valid, false if not
   */
  public boolean isValidInput(String line) {
    matcher = pattern.matcher(line);
    return matcher.find();
  }
}
