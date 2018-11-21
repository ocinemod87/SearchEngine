package searchengine;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHelperTest {
    @Test
    void parseGoodFile() {
        List<Website> sites = FileHelper.parseFile("data/test-file.txt");
        assertEquals(2, sites.size());
        assertEquals("title1", sites.get(0).getTitle());
        assertEquals("title2", sites.get(1).getTitle());
        assertTrue(sites.get(0).containsWord("word1"));
        assertFalse(sites.get(0).containsWord("word3"));
    }
    @Test
    void parseBadFile() {
        List<Website> sites = FileHelper.parseFile("data/test-file-errors.txt");
//        assertEquals(2, sites.size());
//        assertEquals("title1", sites.get(0).getTitle());
//        assertEquals("title2", sites.get(1).getTitle());
//        assertTrue(sites.get(0).containsWord("word1"));
//        assertFalse(sites.get(0).containsWord("word3"));
    }

}
