package searchengine;

import java.util.HashSet;
import java.util.Set;


/**
 * The {@code Fuzzy} class takes an inputted word that is deemed unrecognisable. The word is then expanded into
 * grams and similar words are found.
 *
 * @author Domenico Villani
 * @author Mikkel Buch Smedemand
 */
public class Fuzzy {

  /** The Corpus used for fuzzy search. */
  private Corpus corpus;

  /**
   * Creates a {@code Fuzzy} object that can be used for Fuzzy expansion of a word query.
   * @param corpus The {@code Corpus} used in the {@code SearchEngine}
   */
  public Fuzzy(Corpus corpus) {
    this.corpus = corpus;
  }

  /**
   * If a word doesn't appear in the corpus, the expand method tries to find other
   * words in the corpus that are close to it. As a measure of the distance between words,
   * we use the Levenshtein distance.   
   * 
   * @param unknownWord an unknown word to be expanded by the fuzzy algorithm
   * @return a set of strings with words that are close to the mispelled word.
   */
  public Set<String> expand(String unknownWord) {

    // Set for storing the fuzzy strings
    Set<String> fuzzyStrings = new HashSet<>();

    // maximum allowed edit distance.
    int delta;
    
    // delta is assigned based on the length of the word
    switch (unknownWord.length()) {
      case 3:
        delta = 1;
        break;
      case 2:
        delta = 1;
        break;
      case 1:
        fuzzyStrings.add(unknownWord);
        return fuzzyStrings;
      default:
        delta = 2;
        break;
    }

    // only looking at 2-grams for now
    int gramSize = 2;
    
    // number of unique words in the corpus
    int ncols = corpus.getWordCountUnique();

    Set<String> approximateStrings = new HashSet<>();

    // the summedRowVector keeps track of how many grams each 
    // word in the corpus has in common with the unknownWord.
    int[] summedRowVector = new int[ncols];
    
    // go through all bigrams for unknownWord
    for (String bigram : calculate2Gram(unknownWord)) {
      
      // get "boolean" vector of all the words containing the bigram.
      int[] rowVector = corpus.getBiGramMap().get(bigram);
      for (int ncol = 0; ncol < ncols; ncol++) {
        // add vector for bigram to the summed vector. 
        summedRowVector[ncol] += rowVector[ncol];
      }
    }

    // add approximate words
    for (int i = 0; i < summedRowVector.length; i++) {
      // words that have too few grams in common with unknownWord, are ignored.
      int commonGramsBound =
          Math.max(unknownWord.length(), corpus.getWordsInCorpus().get(i).length()) - 1
              - (delta - 1) * gramSize;
      if (summedRowVector[i] >= commonGramsBound) {
        // words that have sufficient grams, might be within allowed edit distance. 
        approximateStrings.add(corpus.getWordsInCorpus().get(i));
      }
    }

    // only keep the approximate strings with edit distance less or equal to allowed.
    for (String approxString : approximateStrings) {

      // check if editDistance is smaller than delta
      int editDistance = editDistance(unknownWord, approxString);
      if (editDistance <= delta) {
        fuzzyStrings.add(approxString);
      } else {
         // do nothing
      }
    }
    System.out.println("Cannot find word: " + unknownWord + " Instead I'll try to search for:");
    System.out.println(fuzzyStrings.toString());
    return fuzzyStrings;
  }


  /**
   * Calculate edit distance for two strings x and y. Algorithm from reference: "The
   * String-to-string correction problem", R. A. Wagner and M. J. Fischer
   * 
   * Allowed edit operations are: delete, insert, change. 
   * The cost for all edit operations are chosen equal to 1.  
   * 
   * @param x {@code String} word, serving as first point in the distance measuring.
   * @param y {@code String} word, serving as the second point in the distance measuring.
   * 
   * @return the edit distance between the two input words.
   */
  private int editDistance(String x, String y) {

    // cost "function" for allowed edits. All edits have the same cost.
    int deleteCost = 1;
    int insertCost = 1;
    int changeCost = 1;

    // instantiate matrix D
    int[][] D = new int[x.length() + 1][y.length() + 1];

    // loop over string x.length. Populate first column.
    for (int i = 1; i < x.length() + 1; i++) {
      D[i][0] = D[i - 1][0] + deleteCost;
    }

    // loop over string y.length. Populate first row.
    for (int j = 1; j < y.length() + 1; j++) {
      D[0][j] = D[0][j - 1] + insertCost;
    }

    // calculate remaining matrix elements.
    for (int i = 1; i < x.length() + 1; i++) {
      for (int j = 1; j < y.length() + 1; j++) {

        // calculate
        int equalIndicator = changeCost;
        if (x.substring(i - 1, i).equals(y.substring(j - 1, j))) {
          equalIndicator = 0;
        }

        // calculate m1, m2 and m3 and put the minimum value into the matrix element Dij. 
        int m1 = D[i - 1][j - 1] + equalIndicator;
        int m2 = D[i - 1][j] + deleteCost;
        int m3 = D[i][j - 1] + insertCost;
        D[i][j] = Math.min(m1, Math.min(m2, m3));
      }
    }
    // return the value from the lower right corner of the D-matrix.
    return D[x.length()][y.length()];
  }


  /**
   * Calculate 2-grams for a word.
   * 
   * @param word for which 2-grams is to be calculated.
   * @return set of 2-grams that the word contains, including "$s1", and "$sn" where
   * s1 is the first letter, and sn is the last letter in the word.  
   */
  private Set<String> calculate2Gram(String word) {

    Set<String> biGrams = new HashSet<>();

    // If the word has < 1 character, return an empty set
    if (word.length() < 1) {
      return biGrams;
    }
    
    // If the word has == 1 character
    if (word.length() == 1) {
      biGrams.add("$"+word);
      return biGrams;
    }

    biGrams.add("$" + word.charAt(0));
    for (int i = 0; i < word.length() - 1; i++) {
      String biGram = word.substring(i, i + 2);
      biGrams.add(biGram);
    }
    biGrams.add(word.charAt(word.length() - 1) + "$");
    return biGrams;
  }
}
