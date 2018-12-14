package searchengine;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ScoreTest {
private TFScore tfScore;
private TFIDFScore tfidfScore;
private TFICFScore tficfScore;
private List<Website> sites;
private Corpus corpus;
private QueryHandler queryHandler;

  // instantiate variables
  @BeforeEach
  void setUp() {
    tfScore = new TFScore();
    tfidfScore = new TFIDFScore();
    tficfScore = new TFICFScore();

    // create the test database needed for many(all?) of the following tests.
    sites = new ArrayList<>();
    sites.add(
        new Website("example1.com", "example1", Arrays.asList("word1", "word2", "word1", "word1")));
    sites.add(
        new Website("example2.com", "example2", Arrays.asList("word2", "word2", "word3", "word3")));
    sites.add(new Website("example3.com", "example3",
        Arrays.asList("word2", "word2", "word3", "word3", "word4")));
    sites.add(new Website("example4.com", "example4",
        Arrays.asList("word2", "word2", "word2", "word3", "word4")));
    sites.add(new Website("example5.com", "example5",
        Arrays.asList("word2", "word2", "word3", "word3", "word4")));
    sites.add(new Website("example6.com", "example6",
        Arrays.asList("word2", "word2", "word3", "word3", "word4")));
    sites.add(new Website("example7.com", "example7",
        Arrays.asList("word2", "word3", "word4", "word5", "word6")));

    // build corpus for test database.
    corpus = new Corpus(new HashSet<>(sites));
    corpus.build();
    corpus.build2GramIndex();

    // instantiate queryHandler
    queryHandler = new QueryHandler(new InvertedIndexTreeMap(), corpus, new Fuzzy(corpus));
  }

  // make old objects available to be garbage collected, by removing the reference to them.
  @AfterEach
  void tearDown() {
    tfScore = null;
    tfidfScore = null;
    tficfScore = null;
  }

  /**
   * Test all our score(sub)classes which implements the Score interface.
   */
  private void testRankMethod(Score score) {

   double tolerance = 0.00001;

    List<List<String>> structuredQuery1 = queryHandler.getStructuredQuery("word1 word2");
    List<List<String>> structuredQuery2 = queryHandler.getStructuredQuery("word2 word1");
    List<List<String>> structuredQuery3 = queryHandler.getStructuredQuery("   word1    word2   ");
    List<List<String>> structuredQuery4 = queryHandler.getStructuredQuery(" word2  word1   ");

    // go through all websites in the example database.
    for (Website site : sites) {

      // Order of search words shouldn't matter.
      assertTrue(Math.abs(score.rank(site, corpus, structuredQuery1)
          - score.rank(site, corpus, structuredQuery2)) < tolerance);

      // whitespaces between, before, or after search words shouldn't matter.
      assertTrue(Math.abs(score.rank(site, corpus, structuredQuery3)
          - score.rank(site, corpus, structuredQuery4)) < tolerance);
    }
  }

  @Test
  void testTFScore() {
    testRankMethod(tfScore);
  }

  @Test
  void testTFIDFScore() {
    testRankMethod(tfidfScore);
  }

  @Test
  void testTFICFcore() {
    testRankMethod(tficfScore);
  }


  /**
   * Test all our score(sub)classes which implements the Score interface.
   */
  private void testRankMethod2(Score score) {

    Website site1 = sites.get(0);
    Website site2 = sites.get(1);
    List<List<String>> structuredQuery1 = queryHandler.getStructuredQuery("word1 OR word2");

    assertTrue(
        score.rank(site1, corpus, structuredQuery1) > score.rank(site2, corpus, structuredQuery1));
  }

  @Test
  void testTFScore2() {
    testRankMethod2(tfScore);
  }

  @Test
  void testTFIDFScore2() {
    testRankMethod2(tfidfScore);
  }

  @Test
  void testTFICFcore2() {
    testRankMethod2(tficfScore);
  }

}


