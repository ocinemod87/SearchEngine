package searchengine;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Turns our search engine into a command-line application. Queries are received on standard input,
 * and the result of the query is printed to standard output.
 *
 * @author Willard Rafnsson
 */
public class CLIApplication {
  // Search Engine instance
  public static SearchEngine searchengine;

  /**
   * Starts a web server that serves our search engine to the Web. It reads the set of websites from
   * the input file given as argument, constructs an instance of {@code SearchEngine} using that,
   * and finally, starts a web server.
   *
   * @param args command-line arguments.
   */
  public static void main(String[] args) {
    System.out.println("Welcome to the SearchEngine!");

    System.out.println("Reading database...");
    Set<Website> sites = FileHelper.parseFile(args);

    System.out.println("Building the search engine...");
    searchengine = new SearchEngine(sites);

    System.out.println("Search engine is ready to receive queries.");
    System.out.println("Starting command-line interface:");
    System.out.println();

    // Run the search engine
    Scanner input = new Scanner(System.in);
    String query;
    System.out.println("Please enter your query (Q to quit), then press [Enter].");
    while (input.hasNextLine()) {
      query = input.nextLine();
      if (query.equals("Q")) {
        break;
      }
      System.out.println();
      List<Website> resultList = searchengine.search(query);
      System.out.println("Found " + resultList.size() + " websites matching the query.");
      for (Website w : resultList) {
        System.out.println(w.getTitle() + ":");
        System.out.println("  " + w.getUrl());
      }
      System.out.println();
      System.out.println("Please enter your query (Q to quit), then press [Enter].");
    }
    // Close the Scanner object to prevent ressource leaks
    input.close();
  }
}
