package searchengine;

import java.util.List;

/**
 * An {@code Interface} which defines a {@code Score} type. The {@code Score} type knows how to calculate a rank (or
 * score) for a {@code Website}, given a {@code Corpus} and a {@code structuredQuery}.
 *
 * @author André Mortensen Kobæk
 * @author Domenico Villani
 * @author Flemming Westberg
 * @author Mikkel Buch Smedemand
 */
public interface Score {

  /**
   * Calculates the rank of the {@code Website}, given a {@code Corpus}, and a {@code structuredQuery}.
   * 
   * @param site {@code Website} that is to be ranked.
   * @param corpus {@code Corpus} is a collection of all {@code Websites} in the {@code SearchEngine}.
   * @param structuredQuery A {@code List<List<String>>} collection of query words organised in {@code subQueries}.
   * @return reference type {@code Double}. {@code Double} is chosen over the primitive typ because the
   * {@code .compareTo()} method of {@code Double} is used to sort the {@code Websites} according to
   * rank.
   */
  Double rank(Website site, Corpus corpus, List<List<String>> structuredQuery);

  /**
   * Calculates the rank of a given {@code Website}, given a {@code Corpus}, and a single word.
   *
   * @param site {@code Website} that is to be ranked.
   * @param corpus {@code Corpus} is a collection of all {@code Websites} in the {@code SearchEngine}.
   * @param word A query word.
   * @return reference type {@code Double}. {@code Double} is chosen over the primitive typ because the
   * {@code .compareTo()} method of {@code Double} is used to sort the {@code Websites} according to
   * rank.
   */
  Double rankSingle(Website site, Corpus corpus, String word);
}
