package searchengine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InvertedIndexTreeMap extends InvertedIndex {

    public InvertedIndexTreeMap() {

        this.map = new TreeMap<>();
    }

    /**
     * 
     * @param sites The list of websites that should be indexed
     */
    public void build (List<Website> sites){

        for(Website w: sites){
            for(String s: w.getWords()){

                //check if the map contains already an entry of this type
                if(map.containsKey(s)){
                    //if yes takes the value from this entry and add the website to it
                    List<Website> websites = map.get(s);
                    websites.add(w);
                    map.put(s, websites);
                }else{
                    //if not create a new website list with the new website and add to the new entry
                    List<Website> webTemp = new ArrayList<>();
                    webTemp.add(w);
                    map.put(s,webTemp);
                }
            }
        }
    }
}
