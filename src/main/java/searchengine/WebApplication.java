package searchengine;

// Spring Imports (first Application, then Controller)
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

// Other Imports
import java.util.List;

/**
 * Turns our search engine into a Web application, using Spring
 * Boot. Web requests are received and handled by an instance of this
 * class, which Spring makes sure to construct (through an elaborate
 * component-scan).
 *
 * @author Willard Rafnsson
 * @author Martin Aumüller
 * @author Leonid Rusnac
 */
@RestController
@SpringBootApplication
public class WebApplication {
    // Search Engine instance
    public static SearchEngine searchengine;

    /**
     * Starts a web server that serves our search engine to the
     * Web. It reads the list of websites from the input file given as
     * argument, constructs an instance of {@code SearchEngine} using
     * that, and finally, starts a web server.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the SearchEngine!");
        
        System.out.println("Reading database...");
        List<Website> sites = FileHelper.parseFile(args);
        
        System.out.println("Building the search engine...");
        searchengine = new SearchEngine(sites);

        System.out.println("Search engine is ready to receive queries.");
        System.out.println("Starting web server:");
        
        // run the search engine
        SpringApplication.run(WebApplication.class, args);
    }
    
    /**
     * Uses the search engine to search for the list of websites
     * matching the given query. This method is invoked by the web
     * server whenever it receives an HTTP request to "/search".
     * @param query the query string
     * @return the list of websites that matches the query
     */
    @CrossOrigin(origins = "*")
    @RequestMapping("/search")
    public List<Website> search(@RequestParam(value="query", defaultValue="") String query) {
        
        System.out.println("Handling request for query word \"" + query + "\"");

        List<Website> resultList = searchengine.search(query);
        
        System.out.println("Found " + resultList.size() + " websites.");
        
        return resultList;
    }
}
