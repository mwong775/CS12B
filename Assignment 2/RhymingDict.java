import java.io.*;
import java.util.*;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RhymingDict { 	
  

	// Given a pronunciation, get the rhyme group
	// get the more *heavily emphasized vowel* and follwing syllables
	// For "tomato", this is "-ato", and not "-omato", or "-o"
	// Tomato shares a rhyming group with "potato", but not "grow"
	private static String getRhymeGroup(String line) {

		int firstSpace = line.indexOf(" "); 

		String pronunciation = line.substring(firstSpace + 1, line.length());

		int stress0 = pronunciation.indexOf("0");
		int stress1 = pronunciation.indexOf("1");
		int stress2 = pronunciation.indexOf("2");

		if (stress2 >= 0)
			return pronunciation.substring(stress2 - 2, pronunciation.length());
		if (stress1 >= 0)
			return pronunciation.substring(stress1 - 2, pronunciation.length());
		if (stress0 >= 0)
			return pronunciation.substring(stress0 - 2, pronunciation.length());
		
		// No vowels at all? ("hmmm", "mmm", "shh")
		return pronunciation;
	}

	private static String getWord(String line) {
		int firstSpace = line.indexOf(" ");

		String word = line.substring(0, firstSpace);

		return word; 
	}

	// Load the dictionary
	private static String[] loadDictionary() {
		// Load the file and read it

		String[] lines = null; // Array we'll return holding all the lines of the dictionary
		
		try {
			String path = "cmudict/cmudict-short.dict";
			// Creating an array of strings, one for each line in the file
			lines = new String(Files.readAllBytes(Paths.get(path))).split("\\r?\\n");
			
		}
		catch (IOException ex){
			ex.printStackTrace();
		}

		return lines; 
	}

	
	public static void main(String []args) {

		String[] dictionaryLines = loadDictionary();

		/* This code is in here to help you test MyLinkedList without having to mess around with the dictionary. 
		   Feel free to change this test code as you're testing your linked list. But be sure to comment this code
		   out when you submit it. 		
		MySortedLinkedList testList = new MySortedLinkedList(); 
		testList.add("hello");
		testList.add("world");
		// my own stuff 
		testList.add("llamas");
		testList.add("alpacas");
		testList.add("zoozooooos");
		testList.add("cowsRCool");
		//System.out.println(testList);
		testList.add("floofers");
		testList.add("blueberries");
		//System.out.println(testList);
		// end my own stuff 
		testList.add("!");
		System.out.println(testList);
		System.out.println("index 2 = " + testList.get(2));
		System.out.println("world at index " + testList.find("world"));
		System.out.println("hello at index " + testList.find("hello"));
		System.out.println("! at index " + testList.find("!"));
		System.out.println("wow at index " + testList.find("wow"));
		testList.remove(2);
		System.out.println(testList);
		testList.remove(0);
		System.out.println(testList);
		testList.remove(0);
		System.out.println(testList);
		System.out.println("hello at index " + testList.find("hello"));
		*/

		// List of rhyme groups. The items in this linked list will be RhymeGroupWords. 
		ListInterface rhymeGroups = new MyLinkedList(); 

		/* TODO: Add in your code to load the dictionary into your linked lists. Remember that rhymeGroups is a 
		   list of RhymeGroupWords. Inside each of this objects is another linked list which is a list of words within the same
		   rhyme group. I would recommend first getting this working with MyLinkedList for both lists (rhyme groups and 
		   word lists) then get it working using MySortedLinkedList for the word groups. */
		  
		   int rCount = 0;
		   for(int i = 0; i < dictionaryLines.length; i++){
		   	boolean duplicate = false;
		   	for(int k = 0; k < rhymeGroups.size(); k++){
		   		if(getRhymeGroup(dictionaryLines[i]).equals(((RhymeGroupWords)rhymeGroups.get(k)).getRhymeGroup())){ //if rhymegroup in lines is same as rhymegroup 
		   			duplicate = true;																				 //in list, duplicate is not added to list
		   			break;
		   			}
		   		} 
		   	if(duplicate == false) {	
		   		RhymeGroupWords r = new RhymeGroupWords(getRhymeGroup(dictionaryLines[i]), new MySortedLinkedList()); //if not a duplicaate, new rhymegroup to list
		   		rhymeGroups.add(rCount, r);																			  //rhymegroup is added to list as next object
		   		ListInterface words = ((RhymeGroupWords)rhymeGroups.get(rCount)).getWordList();
		   		rCount++; //increase number of rhymegroups - should end up with 8

		   	for(int j = 0; j < dictionaryLines.length; j++){
		   		if(getRhymeGroup(dictionaryLines[j]).equals(r.getRhymeGroup())){ //if rhymegroup in list matches rhymegroup of word in line,
		   			((MySortedLinkedList)words).add(getWord(dictionaryLines[j])); //add word to list for that rhymegroup
		   			}
		   		}
		  	 }
		  }
		   
		/* End TODO for adding dictionary in rhymeGroups. */

		// This code prints out the rhyme groups that have been loaded above. 
		for(int i =0; i < rhymeGroups.size(); i++) {
			RhymeGroupWords rg = (RhymeGroupWords) rhymeGroups.get(i);
			System.out.print(rg.getRhymeGroup() + ": ");
			System.out.println(rg.getWordList());
		}
		
		/* TODO: Add the code here to iterate through pairs of arguments, testing to see if they are in the same rhyme group or not.
		*/

		for(int i = 1; i < args.length; i += 2){ //loops through pairs of words 
			int indexW1 = -1;
			int indexW2 = -1;
			for(int j = 0; j < rhymeGroups.size(); j++){
				if(((RhymeGroupWords)rhymeGroups.get(j)).getWordList().find(args[i - 1]) != -1) //if word1 is found, save its index
					indexW1 = j;
				if(((RhymeGroupWords)rhymeGroups.get(j)).getWordList().find(args[i]) != -1) //if word1 is found, save its index
					indexW2 = j;
				if(indexW1 == indexW2 && indexW1 != -1){ //if both words are found at same index, they rhyme
				System.out.println(args[i-1] + " and " + args[i] + " rhyme");
				break; //stops looking for words
				}
			}
			if(indexW1 != -1 && indexW2 != -1 && indexW1 != indexW2) //if both words found at different indices, they don't rhyme
				System.out.println(args[i-1] + " and " + args[i] + " don't rhyme");
			if(indexW1 == -1)
				System.out.println(args[i - 1] + " is not in the dictionary"); //word1 not found
			if(indexW2 == -1)
				System.out.println(args[i] + " is not in the dictionary"); //word2 not found
		}
	}
}