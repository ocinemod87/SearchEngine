package searchengine;

import java.util.List;

/**
 * The {@code TFScore} is an implementation of the {@code Score interface}. It calculates a rank
 * for a {@code Website}, given a {@code Corpus} and either a {@code structuredQuery} or a single word using the TFICF algorithm.
 *
 * @author André Mortensen Kobæk
 * @author Domenico Villani
 * @author Flemming Westberg
 * @author Mikkel Buch Smedemand
 */

public class TFScore implements Score {

  /**
   * Calculates the rank of the {@code Website}, given a {@code Corpus}, and a {@code structuredQuery} using the TF ranking algorithm.
   *
   * @param site {@code Website} that is to be ranked.
   * @param corpus {@code Corpus} is a collection of all {@code Websites} in the {@code SearchEngine}. Which is not used for anything.
   * @param structuredQuery A {@code List<List<String>>} collection of query words organised in {@code subQueries}.
   * @return reference type {@code Double}. {@code Double} is chosen over the primitive typ because the
   * {@code .compareTo()} method of {@code Double} is used to sort the {@code Websites} according to
   * rank.
   */
  @Override
  public Double rank(Website site, Corpus corpus, List<List<String>> structuredQuery) {
    return rankQueryTF(site, structuredQuery);
  }

  /**
   * Calculates the rank of a given {@code Website}, given a {@code Corpus}, and a single word using the TF ranking algorithm.
   *
   * @param site {@code Website} that is to be ranked.
   * @param corpus {@code Corpus} is a collection of all {@code Websites} in the {@code SearchEngine}. Which is not used for anything.
   * @param word A query word.
   * @return reference type {@code Double}. {@code Double} is chosen over the primitive typ because the
   * {@code .compareTo()} method of {@code Double} is used to sort the {@code Websites} according to
   * rank.
   */
  @Override
  public Double rankSingle(Website site, Corpus corpus,  String word) {
    return rankSingleTF(site, word);
  }


  /**
   * Rank a single {@code Website} given the {@code Corpus} and a single query word. The ranking algorithm applied is TFICF (i.e Inverse
   * Corpus Term Frequency).
   *
   * @param site The {@code Website} to be ranked.
   * @param word The query word.
   * @return The ranking of the {@code Website}.
   */
  private Double rankSingleTF(Website site, String word) {

    // score single word/term according to the document frequency and inverse corpus frequency.
    int wordSize = site.getWordCount();

    // number of times word appear on website, i.e the term site count.
    double wordCount = (double) site.getWordsToOccurences().get(word);

    // the site term frequency.
    return (wordCount / wordSize);
  }

  /**
   * Rank a single {@code Website} given the {@code Corpus} and a {@code structuredQuery}. The ranking algorithm applied is TFICF (i.e Inverse
   * Corpus Term Frequency).
   *
   * @param site The {@code Website} to be ranked.
   * @param structuredQuery A {@code List<List<String>>} collection of query words organised in {@code subQueries}.
   * @return The ranking of the {@code Website}.
   */

  private Double rankQueryTF(Website site, List<List<String>> structuredQuery) {

    double maxScoreSubQuery = 0;

    for (List<String> subquery : structuredQuery) {
      // sum the scores for the individual words in the subquery.
      double sum = 0;
      for (String word : subquery) {
        if (site.getWords().contains(word)) {
          sum += rankSingleTF(site, word);
        }
      }
      assert sum >= 0 : "The rank of a site should always be non-negative."; // sanity check, that
                                                                             // rank is always
                                                                             // non-negative.

      // find the subquery with the highest score. This is the score to be returned.
      if (sum > maxScoreSubQuery) {
        maxScoreSubQuery = sum;
      }
    }
    return maxScoreSubQuery;
  }
}
