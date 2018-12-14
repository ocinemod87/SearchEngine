package searchengine;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class QueryHandlerTest {

  // declare variables to be used in the tests
  private List<Website> sites;
  private InvertedIndex idx;
  private Corpus corpus;
  private QueryHandler queryHandler;
  private Fuzzy fuzzy;

  @BeforeEach
  void setUp() {
    // create the test database needed for many(all?) of the following tests.
    sites = new ArrayList<>();
    sites.add(new Website("1.com", "example1", Arrays.asList("word1", "word2")));
    sites.add(new Website("2.com", "example2", Arrays.asList("word2", "word3")));
    sites.add(new Website("3.com", "example3", Arrays.asList("word3", "word4", "word5")));
    sites.add(new Website("3.com", "example3", Arrays.asList("word3", "word4", "word5")));

    // build index for test database.
    idx = new InvertedIndexTreeMap();
    idx.build(new HashSet<Website>(sites));


    // build corpus for test database.
    corpus = new Corpus(new HashSet<Website>(sites));
    corpus.build();
    corpus.build2GramIndex();

    fuzzy = new Fuzzy(corpus);
    // instantiate queryHandler
    queryHandler = new QueryHandler(idx, corpus ,fuzzy);
  }

  @Test
  void testSingleWord() {
    assertEquals(1, queryHandler.getMatchingWebsites("word1").size());
    assertEquals(2, queryHandler.getMatchingWebsites("word2").size());
  }

  @Test
  void testInvalidInput() {
    assertEquals(1, queryHandler.getMatchingWebsites("WORD1").size());
    assertEquals("example1", queryHandler.getMatchingWebsites("WoRd1").get(0).getTitle());
    assertEquals(2, queryHandler.getMatchingWebsites("woRD2").size());
    assertEquals(1, queryHandler.getMatchingWebsites("word-3").size()); // Separated by dash
    assertEquals(1, queryHandler.getMatchingWebsites("wOrD_4").size()); // Random case and separated by underscore
    assertEquals(0, queryHandler.getMatchingWebsites("").size()); // The empty string
  }

  @Test
  void testMultipleWords() {
    assertEquals(1, queryHandler.getMatchingWebsites("word1 word2").size());
    assertEquals(1, queryHandler.getMatchingWebsites("word3 word4").size());
    assertEquals(1, queryHandler.getMatchingWebsites("word4 word3 word5").size());
    assertEquals(1, queryHandler.getMatchingWebsites("word4 WORD3 word5?").size()); // Same as above - but with special characters
  }

  @Test
  void testORQueries() {
    assertEquals(3, queryHandler.getMatchingWebsites("word2 OR word3").size());
    assertEquals(2, queryHandler.getMatchingWebsites("word1 OR word4").size());
    // Corner case: Does code remove duplicates?
    assertEquals(1, queryHandler.getMatchingWebsites("word1 OR word1").size());
    // Corner case: Does a standalone 'OR' entail a size of 0?
    assertEquals(0, queryHandler.getMatchingWebsites("OR").size());
    // Corner case: Does the code support 'OR' at the beginning og end of the query?
    assertEquals(3, queryHandler.getMatchingWebsites("OR word2 OR word3").size());
    assertEquals(2, queryHandler.getMatchingWebsites("word1 OR word4 OR ").size());
  }


  @Test
  void testUrlSearch(){
    // Check against non existing site.
    assertEquals(0, queryHandler.getMatchingWebsites("site:sjdka.com word3").size());
    // Check against existing site with existing word
    assertEquals(1, queryHandler.getMatchingWebsites("site:3.co word3").size());
    // Check against existing site with nonexistent word
    assertEquals(0, queryHandler.getMatchingWebsites("site:2.com wordWoRD").size());
  }
}