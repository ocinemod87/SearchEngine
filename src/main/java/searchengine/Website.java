package searchengine;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A {@code Website} is the basic entity of the search engine. It has a url, a title, a list of
 * words, and various metadata.
 *
 * @author André Mortensen Kobæk
 * @author Domenico Villani
 * @author Flemming Westberg
 * @author Mikkel Buch Smedemand
 */
public class Website {

  /** The website's title */
  private String title;

  /** The website's url */
  private String url;

  /** A list of words storing the words on the website */
  private List<String> words;

  /** A map from word to wordcount */
  private Map<String, Integer> wordsToOccurences;

  /** A list of URLs for similar websites */
  private List<String> similarWebsites;

  /**
   * Creates a {@code Website} object from a url, a title, and a list of words that are contained on
   * the website.
   *
   * @param url   the website's url
   * @param title the website's title
   * @param words the website's list of words
   */
  public Website(String url, String title, List<String> words) {
    this.url = url;
    this.title = title;
    this.words = words;
    similarWebsites = new ArrayList<>();

    // build the map which holds words and corresponding word counts for the website.
    wordsToOccurences = new HashMap<>();
    for (String word : words) {
      if (wordsToOccurences.containsKey(word)) {
        wordsToOccurences.put(word, wordsToOccurences.get(word) + 1);
      } else {
        wordsToOccurences.put(word, 1);
      }
    }
  }

  /**
   * Returns the title of the {@code Website}
   *
   * @return the title of the {@code Website}
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the URL of the {@code Website}
   *
   * @return the URL of the {@code Website}
   */
  public String getUrl() {
    return url;
  }

  /**
   * Returns the {@code Map} of words to its occurences
   *
   * @return Returns the {@code Map} of words to its occurences
   */
  public Map<String, Integer> getWordsToOccurences() {
    return wordsToOccurences;
  }

  /**
   * Returns the website's list of words
   * 
   * @return website's list of words
   */
  public List<String> getWords() {
    return words;
  }

  /**
   * Returns the number of words in list of words.
   * 
   * @return number of words in list of words.
   */
  public int getWordCount() {
    return words.size();
  }

  /**
   * Checks whether a word is present on the website or not.
   *
   * @param word the query word
   * @return True, if the word is present on the website
   */
  public Boolean containsWord(String word) {
    return words.contains(word);
  }

  /**
   * Assign a list with similar websites to this object
   *
   * @param websiteUrls the list of URLs for similar websites from the centroid
   */
  public void setSimilarWebsites(List<String> websiteUrls) {
    similarWebsites.addAll(websiteUrls);
    similarWebsites.remove(url); // Remove the URL of this website if it is present in the list - to
                                 // avoid circular dependencies
  }

  /**
   * Return a list of URLs for websites similar to this one
   *
   * @return the list of URLs
   */
  public List<String> getSimilarWebsites() {
    return similarWebsites;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Website website = (Website) o;
    return title.equals(website.title) && url.equals(website.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, url);
  }

  @Override
  public String toString() {
    return "Website{" + "title='" + title + '\'' + ", url='" + url + '\'' + ", words=" + words
        + '}';
  }

}
