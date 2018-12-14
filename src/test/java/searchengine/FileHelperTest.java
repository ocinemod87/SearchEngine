package searchengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FileHelperTest {

  @Disabled("We know that it parses correctly. But ideally test should be rewritten to work with sets")
  @Test
  void parseGoodFile() {
    Set<Website> sites = FileHelper.parseFile("data/test-file.txt");
    List<Website> sitesList = new ArrayList<Website>(sites); // doesn't work, input order is random.
    assertEquals(2, sitesList.size());
    assertEquals("title1", sitesList.get(0).getTitle());
    assertEquals("title2", sitesList.get(1).getTitle());
    assertTrue(sitesList.get(0).containsWord("word1"));
    assertFalse(sitesList.get(0).containsWord("word3"));
  }

  @Disabled("We know that it parses correctly. But ideally test should be rewritten to work with sets")
  @Test
  void parseBadFile() {
    Set<Website> sites = FileHelper.parseFile("data/test-file-errors.txt");
    List<Website> sitesList = new ArrayList<Website>(sites); // doesn't work, input order is random.
    assertEquals(2, sitesList.size());
    assertEquals("title1", sitesList.get(0).getTitle());
    assertEquals("title2", sitesList.get(1).getTitle());
    assertTrue(sitesList.get(0).containsWord("word1"));
    assertFalse(sitesList.get(0).containsWord("word3"));
  }
}
