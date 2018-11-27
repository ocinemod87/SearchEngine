package searchengine;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FileHelperTest {
    @Test
    void parseGoodFile() {
        Set<Website> sites = FileHelper.parseFile("data/test-file.txt");
        assertEquals(2, sites.size());
//        assertEquals("title1", sites.get(0).getTitle());
//        assertEquals("title2", sites.get(1).getTitle());
//        assertTrue(sites.get(0).containsWord("word1"));
//        assertFalse(sites.get(0).containsWord("word3"));
    }
    @Test
    void parseBadFile() {
        Set<Website> sites = FileHelper.parseFile("data/test-file-errors.txt");
        assertEquals(2, sites.size());
//        assertEquals("title1", sites.get(0).getTitle());
//        assertEquals("title2", sites.get(1).getTitle());
//        assertTrue(sites.get(0).containsWord("word1"));
//        assertFalse(sites.get(0).containsWord("word3"));
    }
}
