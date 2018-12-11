package searchengine;

/**
 * An Interface which defines a Score type. This is a type that knows how to calculate a rank (or
 * score) for a website, given a Corpus (database of sites) and a search query.
 * 
 * @author André Mortensen Kobæk
 * @author Domenico Villani
 * @author Flemming Westberg Sørensen
 * @author Mikkel Buch Smedemand
 * 
 */
public interface Score {

  /**
   * Calculate the rank of the website, given a corpus/databse of websites, and a search query.
   * 
   * @param site Website that needs to be ranked.
   * @param corpus Corpus is the collection of websites that the searchEngine knows about.
   * @param query String of search words possibly separated by white space and/or OR.
   * @return reference type Double. The reference type is Double instead of the primitive type
   *         double because the compareTo method of Double is used to sort the websites according to
   *         rank.
   */
  Double rank(Website site, Corpus corpus, String query);

}
