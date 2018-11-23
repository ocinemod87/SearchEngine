
package searchengine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class IndexTest {
    Index simpleIndex = null;
    Index treeIndex = null;
    Index hashIndex = null;

    
    @BeforeEach
    void setUp() {
        Set<Website> sites = new HashSet<>();
        sites.add(new Website("example1.com", "example1", Arrays.asList("word1", "word2", "word1")));
        sites.add(new Website("example2.com", "example2", Arrays.asList("word2", "word3")));
        simpleIndex = new SimpleIndex();
        treeIndex = new InvertedIndexTreeMap();
        hashIndex = new InvertedIndexHashMap();
        simpleIndex.build(sites);
        treeIndex.build(sites);
        hashIndex.build(sites);
    }

    @AfterEach
    void tearDown() {
        simpleIndex = null;
    }

    @Test
    void buildSimpleIndex() {
        assertEquals("SimpleIndex{sites=[Website{title='example1', url='example1.com', words=[word1, word2, word1]}, Website{title='example2', url='example2.com', words=[word2, word3]}]}", simpleIndex.toString());
    }

    @Test
    void buildtreeIndex() {
        assertEquals("InvertedIndexTreeMap{sites=[Website{title='example1', url='example1.com', words=[word1, word2, word1]}, Website{title='example2', url='example2.com', words=[word2, word3]}]}", treeIndex.toString());
    }
    
    @Test
    void buildhashIndex() {
        assertEquals("InvertedIndexHashMap{sites=[Website{title='example1', url='example1.com', words=[word1, word2, word1]}, Website{title='example2', url='example2.com', words=[word2, word3]}]}", hashIndex.toString());
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
    
    private void lookup(Index index) {
        assertEquals(1, index.lookup("word1").size());
        assertEquals(2, index.lookup("word2").size());
        assertEquals(0, index.lookup("word4").size());
    }

}