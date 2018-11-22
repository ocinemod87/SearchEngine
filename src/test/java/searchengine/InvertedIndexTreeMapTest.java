package searchengine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Unit tests for InvertedIndexTreeMap")
class InvertedIndexTreeMapTest {
	
	// declare variables
	InvertedIndexTreeMap index;
	List<Website> sites;
	List<Website> nullSites;
	
	@BeforeEach
	void setUp() {
		// initialize variables that is used in several tests.
		index = new InvertedIndexTreeMap();
		
		sites = new ArrayList<>();
        sites.add(new Website("example1.com", "example1", Arrays.asList("word1", "word2", "word1")));
        sites.add(new Website("example2.com", "example2", Arrays.asList("word2", "word3")));
	}
	
    @AfterEach
    void tearDown() {
        index = null;
        sites = null;
    }
	
	
	@Test
	void addEntryToMapWithoutBuild() {
		// ideally it shouldn't be allowed to add things to the map without using build.
		assertThrows(Exception.class, () -> { 
				index.map.put("bypassing-build", nullSites);
		}, "Should not be able to put things into map without using the build method"); 
	}		
	
	@Test
	void lookupInEmptyIndex() {
		// the assertionError message is written as a lambda, so that the message is lazily evaluated
		// I.e the message is only constructed if it is needed, that is if the test fail.
		// See https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions
		assertNotNull(index.lookup("anyword"), () -> "lookup() should always return a list");
		assertTrue(index.lookup("anyword") instanceof List<?>, () -> "lookup() should always return a list");
	}
	
	/**
	 * Test if build method throws an IllegalArgumentException if supplied with a null argument.
	 */
	@Test
	void buildNull() {
			assertThrows(IllegalArgumentException.class, () -> {
            index.build(null);
        });
 	}
	
	/**
	 * Test if build method throws an IllegalArgumentException if supplied with a list of null arguments.
	 */
	@Test
	void buildListOfNulls() {
		// create a list of nulls
		nullSites = new ArrayList<>();
		for(int i=0; i<10; i++) {
			nullSites.add(null);
		}
		
		assertThrows(IllegalArgumentException.class, () -> {
			index.build(nullSites);
        });
 	}
	
	
	/**
	 * Test if lookup method returns something not null,
	 * independent of whether it can find the word. 
	 */
    @Test
    void lookupWord01() {
    	index.build(sites);
    	assertNotNull(index.lookup("word1"));
    	assertNotNull(index.lookup("word55"));
    }
	
    /**
	 * Test if lookup method returns correct number of websites.
	 */
    @Test
    @Disabled("Disabled until lookupWord01 is passed")
    void lookupWord02() {
    	index.build(sites);
    	assertEquals(1, index.lookup("word1").size());
        assertEquals(2, index.lookup("word2").size());
        assertEquals(0, index.lookup("word4").size());
    }
    
    
}
