package searchengine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class InvertedIndex implements Index {

    protected Map<String, Collection<Website>> map;
    /**
     *Takes a list of websites and creates a map. The keys are the words contained in these websites,
     * the value is a list of all the websites containing that key word.
     * @param sites The list of websites that should be indexed
     */
    @Override
    public void build (Collection<Website> sites){

        for(Website site : sites){
            for(String word: site.getWords()){

                //check if the map contains already an entry of this type
                if(map.containsKey(word)){
                    //if yes takes the value from this entry and add the website to it
                    Collection<Website> websites = map.get(word);
                    websites.add(site);
                    map.put(word, websites);
                }else{
                    //if not create a new website list with the new website and add to the new entry
                    List<Website> webTemp = new ArrayList<>();
                    webTemp.add(site);
                    map.put(word,webTemp);
                }
            }
        }
    }

    /**
     * Returns the List value mapped to the query String, returns null if the map does not contain the key
     * @param query The query
     * @return the List of website that contain the query word
     */
    @Override
    public Collection<Website> lookup(String query){

        if(map.containsKey(query)){
            return map.get(query);
        }
        return null;
    }
}
