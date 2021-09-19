package com.company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.io.IOException;
import java.util.ArrayList;

class TheRaven {

    public static void main(String[] args) {
        Document doc = null;
        int elementsToReturn = 20; //change this to return more than 20 elements

        try { //try catch for error handling
            doc = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").get(); // opens page


            System.out.println(doc.getElementsByClass("poem").text()); //pulls just the text from class "poem" which is where the ravens body is stored
            Scanner sc = new Scanner(System.in);

            String text = doc.getElementsByClass("poem").text(); //transfers the text to a string
            text = text.replaceAll("[^a-zA-Z0-9]", " ");    //removes special chars from string
            String newText = text.trim().replaceAll(" +", " "); //removes multiple spaces the way i was mapping the text it was reading two spaces as a word
            //System.out.print(newText); //used to confirm correct text output. leaving here for servicing
            //ArrayList<String> words = new ArrayList<String>(); leftover from earlier approach

            String[] ravenToArr = newText.split(" "); // splits string into  an array of words
            HashMap<String, Integer> freqMap = new HashMap<String, Integer>(); //creates a new hashmap to store values
            for (int i = 0; i < ravenToArr.length; i++) {   //iterate through list
                String key = ravenToArr[i];
                int occurrences = freqMap.getOrDefault(key, 0); //check for key mapping and goes to default if doesn't exist
                freqMap.put(key, ++occurrences); //puts in key weather default or previous location and increments the count. on default its 0 -> 1 on ref, its +1
            }
            Map<String, Integer>  newMap = sortMap(freqMap); // passes map to sortMap and assigns return map to newMap
            System.out.println(" "); //new line to help readability
            int i = 0;
            for (Map.Entry<String, Integer> result : newMap.entrySet()) {
                if (i < 20) System.out.println(result.getValue() + " " + result.getKey()); //returns variables one at a time by String value then Occurrence
                i++;
            }

        } catch (IOException e) { //IO error if page is broken
            e.printStackTrace();
        }
    }
    public static HashMap<String, Integer> sortMap(HashMap<String, Integer> oldMap) //takes hashmap and returns newly sorted map
    {
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(oldMap.entrySet());
        //creates a new list interface from elements of the hash map

        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { //this portion sorts the list
            public int compare(Map.Entry<String, Integer> first,
                               Map.Entry<String, Integer> second)
            {
                return (second.getValue()).compareTo(first.getValue()); // sort in ascending order
            }
        });
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); //moves data from sorted list back to hash map to be returned
        for (Map.Entry<String, Integer> aa : list) temp.put(aa.getKey(), aa.getValue());
        return temp;
    }


}

