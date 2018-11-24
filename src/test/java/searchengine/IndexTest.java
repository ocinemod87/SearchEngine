package searchengine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

class IndexTest {
  Index simpleIndex = null;
  InvertedIndex hashIndex = null;
  InvertedIndex treeIndex = null;

  @BeforeEach
  void setUp() {
    simpleIndex = new SimpleIndex();
    hashIndex = new InvertedIndexHashMap();
    treeIndex = new InvertedIndexTreeMap();
  }

  @AfterEach
  void tearDown() {
    simpleIndex = null;
    hashIndex = null;
    treeIndex = null;
  }


  /**
   * Test if build method throws an IllegalArgumentException if supplied with a null argument.
   */
  private void buildNull(Index index) {
    assertThrows(IllegalArgumentException.class, () -> {
      index.build(null);
    });
  }

  @Disabled("We decided that this is currently not a requirement.")
  @Test
  void buildNullHash() {
    buildNull(hashIndex);
  }

  @Disabled("We decided that this is currently not a requirement.")
  @Test
  void buildNullTree() {
    buildNull(treeIndex);
  }

  @Disabled("We decided that this is currently not a requirement.")
  @Test
  void buildNullSimple() {
    buildNull(simpleIndex);
  }


  /**
   * Test if build method throws an IllegalArgumentException if supplied with a list of null
   * arguments.
   */
  private void buildListOfNulls(Index index) {
    // create a list of nulls
    ArrayList<Website> nullSites = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      nullSites.add(null);
    }

    assertThrows(IllegalArgumentException.class, () -> {
      index.build(nullSites);
    });
  }

  @Disabled("We decided that this is currently not a requirement.")
  @Test
  void buildListOfNullsHash() {
    buildListOfNulls(hashIndex);
  }

  @Disabled("We decided that this is currently not a requirement.")
  @Test
  void buildListOfNullsTree() {
    buildListOfNulls(treeIndex);
  }

  @Disabled("We decided that this is currently not a requirement.")
  @Test
  void buildListOfNullsSimple() {
    buildListOfNulls(simpleIndex);
  }


  /**
   * Check if it is possible to put things into the inverted indices without using the build
   * command.
   */
  private void addEntryToMapWithoutBuild(InvertedIndex index) {
    // ideally it shouldn't be allowed to add things to the map without using build.
    assertThrows(Exception.class, () -> {
      index.map.put("bypassing-build", new ArrayList<>());
    }, "Should not be able to put things into map without using the build method");
  }

  @Disabled("We decided that this is currently not a requirement.")
  @Test
  void addEntryHashIndex() {
    addEntryToMapWithoutBuild(hashIndex);
  }

  @Disabled("We decided that this is currently not a requirement.")
  @Test
  void addEntryTreeIndex() {
    addEntryToMapWithoutBuild(treeIndex);
  }


  // @Test
  // void buildSimpleIndex() {
  // assertEquals("SimpleIndex{sites=[Website{title='example1', url='example1.com', words=[word1,
  // word2, word1]}, Website{title='example2', url='example2.com', words=[word2, word3]}]}",
  // simpleIndex.toString());
  // }


  /**
   * 
   * @param index
   */
  private void lookupInEmptyIndex(Index index) {
    // the assertionError message is written as a lambda, so that the message is lazily evaluated
    // I.e the message is only constructed if it is needed, that is if the test fail.
    // See https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions
    assertNotNull(index.lookup("anyword"), () -> "lookup() should always return a collection");
    assertTrue(index.lookup("anyword") instanceof Collection,
        () -> "lookup() should always return a collection");
  }

  @Test
  void lookupInEmptyHashIndex() {
    lookupInEmptyIndex(hashIndex);
  }

  @Test
  void lookupInEmptyTreeIndex() {
    lookupInEmptyIndex(treeIndex);
  }

  @Disabled("We decided that this is currently not a requirement.")
  @Test // currently gives an ERROR because of a NullPointerException
  void lookupInEmptySimpleIndex() {
    lookupInEmptyIndex(simpleIndex);
  }


  /**
   * Test if lookup method returns something not null independent of whether it can find the word or
   * not. Also test if the collection returned has the correct number of websites.
   */
  private void lookup(Index index) {

    // setup for testing the lookup method
    List<Website> sites = new ArrayList<Website>();
    sites.add(new Website("example1.com", "example1", Arrays.asList("word1", "word2", "word1")));
    sites.add(new Website("example2.com", "example2", Arrays.asList("word2", "word3")));
    index.build(sites);

    // check return type
    assertAll("check return type", () -> {
      assertNotNull(index.lookup("word1"));
      assertNotNull(index.lookup("word55"));
      assertTrue(index.lookup("word1") instanceof Collection,
          "lookup must return a collection of websites");

      // if return type is correct, the following checks if size is correct.
      assertAll("check size of returned collection", () -> {
        assertEquals(1, index.lookup("word1").size());
        assertEquals(2, index.lookup("word2").size());
        assertEquals(0, index.lookup("word4").size());
      });
    });
  }

  @Test
  void lookupSimpleIndex() {
    lookup(simpleIndex);
  }

  @Test
  void lookupTreeIndex() {
    lookup(treeIndex);
  }

  @Test
  void lookupHashIndex() {
    lookup(hashIndex);
  }

}
