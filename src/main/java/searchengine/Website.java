package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A website is the basic entity of the search engine. It has a url, a title, and a list of words.
 *
 * @author Martin Aumüller
 */
public class Website {

  /**
   * the website's title
   */
  private String title;

  /**
   * the website's url
   */
  private String url;


  /**
   * a list of words storing the words on the website
   */
  private List<String> words;
  
  /**
   * a map from word to wordcount
   */
  Map<String, Integer> wordCount; // package private

  /**
   * the number of words on the website.
   */
  private int wordSize = 0;
  
  /**
   * the current rank of the website. Depends on both the query and the other websites in the "corpus".
   */
  private double rank;
  
  /**
   * Creates a {@code Website} object from a url, a title, and a list of words that are contained on
   * the website.
   *
   * @param url the website's url
   * @param title the website's title
   * @param words the website's list of words
   */
  public Website(String url, String title, List<String> words) {
    this.url = url;
    this.title = title;
    this.words = words;
    this.wordSize = words.size();
    
    // build the map which holds words and corresponding word counts for the website.
    wordCount = new HashMap<>();
    int counter = 0;
    for (String word : words) {
      if (wordCount.containsKey(word)) {
        wordCount.put(word, wordCount.get(word) + 1);
      } else {
        wordCount.put(word, 1);
      }
      counter++;
    }
    assert(counter == wordSize); 
  }

  /**
   * Returns the website's title.
   *
   * @return the website's title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the website's url.
   *
   * @return the website's url.
   */
  public String getUrl() {
    return url;
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
  public int getWordSize() {
    return wordSize;
  }
  
  public double getRank() {
    return rank;
  }

  public void setRank(double rank) {
    this.rank = rank;
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

  @Override
  public String toString() {
    return "Website{" + "title='" + title + '\'' + ", url='" + url + '\'' + ", words=" + words
        + '}';
  }
  
}
