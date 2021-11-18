package com.company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.io.IOException;
import java.util.List;

public class TheRaven {
    /**
     * this is the main function who allows the application to operate, this is where Jsoup is implemented and user input is collected
     * this class will pass its hashmap to the
     * @param args this loop takes no arguments and args is left void
     * @throws IOException this is the IO Exception if Jsoup is not able to do its thing
     */
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        /**
         * The variable okayToGo is a value that checks if the program is clear to continue when conditions are checked.
         */
        int okayToGo = 0;
        //System.out.println("enter link to parse");
        String link = ("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm"); //reader.readLine(); //add readline to make dynamic
        String elementsToReturnString = null;
        while (okayToGo == 0) {
            System.out.println("enter  a number between 1 and 100 ");
            elementsToReturnString = reader.readLine();
            if (IsBetweenNumbers(elementsToReturnString) == 1) {
                okayToGo = 1;
            }
        }
        Document doc = null;

        int elementsToReturn = Integer.parseInt(elementsToReturnString); //change this to return more than 20 elements
        /**
         * within the try loop, a try condition is started to check the Web address the program is passed. this program  should open and print the
         * section. The purpose of this step is for the user to verify the correct element is parsed. If adding functionality, "Chapter" can be set as input by the user,
         * assuming the user has the knowledge and access to determine this element.
         */
        try { //try catch for error handling
            doc = Jsoup.connect(link).get(); // opens page //https://www.gutenberg.org/files/1065/1065-h/1065-h.htm

            System.out.println("This is what we pulled to parse:");
            System.out.println(doc.getElementsByClass("chapter").text()); //pulls just the text from class "poem" which is where the ravens body is stored
            Scanner sc = new Scanner(System.in);
            /**
             * this section is used to remove any non alphanumeric characters from the jsoup doc. The current method for divinding the string is by spaces
             */
            String text = doc.getElementsByClass("chapter").text(); //transfers the text to a string
            text = text.replaceAll("[^a-zA-Z0-9]", " ");    //removes special chars from string
            String newText = text.trim().replaceAll(" +", " "); //removes multiple spaces the way i was mapping the text it was reading two spaces as a word
            //System.out.print(newText); //used to confirm correct text output. leaving here for servicing
            //ArrayList<String> words = new ArrayList<String>(); leftover from earlier approach

            String[] ravenToArr = newText.split(" "); // splits string into  an array of words
            HashMap<String, Integer> freqMap = new HashMap<String, Integer>(); //creates a new hashmap to store values
            /**
             * This for loop iterates through the list of the program adding unique words and adding additional occurrences if it is already added to the hash map
             */
            for (int i = 0; i < ravenToArr.length; i++) {   //iterate through list
                String key = ravenToArr[i];
                int occurrences = freqMap.getOrDefault(key, 0); //check for key mapping and goes to default if doesn't exist
                freqMap.put(key, ++occurrences); //puts in key weather default or previous location and increments the count. on default its 0 -> 1 on ref, its +1
            }
            /**
             * this is the hasmap that is created to store word occurrences through the document
             */
            Map<String, Integer>  newMap = sortMap(freqMap); // passes map to sortMap and assigns return map to newMap
            System.out.println(" "); //new line to help readability
            int i = 0;
            for (Map.Entry<String, Integer> result : newMap.entrySet()) {
                if (i < elementsToReturn){
                    System.out.println(result.getValue() + " " + result.getKey()); //returns variables one at a time by String value then Occurrence
                }
                i++;
            }
            /**
             * this is the exception handling if there is an issue using jsoup
             */
        } catch (IOException E) { //IO error if page is broken
            E.printStackTrace();
        }
    }

    /**
     * This function checks to verify the user input is between the designated values
     * @param elementsToReturnString the elements the user is asking for the program to return
     * @return if the condition is true to accept input
     */
    public static int IsBetweenNumbers(String elementsToReturnString) {
        int condition = 0;
        if (Integer.parseInt(elementsToReturnString) <= 100 && Integer.parseInt(elementsToReturnString) >= 1) {
            condition = 1;
        }
        if (condition == 1) {
            System.out.println("Thanks");
        }
        return condition;
    }

    /**
     * This function takes an unsorted hash map of all work occurances and sorts them into a hash map to be returned from high to low.
     * @param oldMap the old hash map that is passed to the sortMap function to be sorted
     * @return the temporary array that holds the sorted hashmap
     */
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
