import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException; 
import java.util.Arrays;
import java.util.ArrayList;

public class Palindrome {

	static WordDictionary dictionary = new WordDictionary(); 

	
	// Get all words that can be formed starting at this index
	public static String[] getWords(String text, int index) {
		ArrayList<String> words = new ArrayList<String>();
		for (int i = 0; i <= text.length() - index; i++) {
			String maybeWord = text.substring(index, index + i);
			if (dictionary.isWord(text.substring(index, index + i))) {
				words.add(maybeWord);
			}
		}

		return words.toArray(new String[0]);
	}


	public static String stackToReverseString(MyStack stack) {
		/* 
		* TODO 3
		*/
		// Create an empty string
		String read = "";

		// Create a new temporary stack
		MyStack temp = new MyStack();

		// Pop everything from original stack onto new stack
		while(!stack.isEmpty()) {
			Object popped = stack.pop();
			temp.push(popped);
		}

		// Pop everything from new stack back onto original stack AND add to string
		while(!temp.isEmpty()) {
			Object popped = temp.pop();
			read += (String)popped + " ";
			stack.push(popped);
		}

		return read;

		/* 
		* TODO 3
		*/
	}

	public static String reverseStringAndRemoveNonAlpha(String text) {
		/* 
		* TODO 4
		*/

		// Create new stack
		MyStack revStack = new MyStack();

		// iterate thru string, push alpha chars
		for(int i = 0; i < text.length(); i++) {
			if(Character.isAlphabetic(text.charAt(i))) { // if char in str is alphabetic
				char alpha = text.charAt(i);
				Character character = new Character(alpha); // wrapper class 
				revStack.push(character);
			}
		}

		text = ""; // emptying & reusing string

		// pop everything from stack to reconstruct string in reverse
		while(!revStack.isEmpty()) {
			Object popped = revStack.pop();
			text += popped; 
		}

		return text;

		/* 
		* TODO 4
		*/
	}



	// Returns true if the text is a palindrome, false if not.
	public static boolean isPalindrome(String text) {
		/* 
		* TODO 5: Implement "explorePalindrome"
		*/
		// convert string to lowercase
		text = text.toLowerCase();

		// create new stack and queue
		MyStack stack = new MyStack();
		MyQueue queue = new MyQueue();

		// enqueue and push alpha chars
		for(int i = 0; i < text.length(); i++) {
			if(Character.isAlphabetic(text.charAt(i))) {
				char alpha = text.charAt(i);
				Character character = new Character(alpha); //wrapper class
				stack.push(character);
				queue.enqueue(character);
			}

		}

		// pop and dequeue chars until one or both empty

		boolean charEqual = true; // used to check comparisons of chars

		// Object variables to store popped and dequeued objects:
		Object qFront;
		Object sTop;

		while(!stack.isEmpty() && !queue.isEmpty()) {
			qFront = queue.dequeue();
			sTop = stack.pop();

			if(!qFront.equals(sTop)) { // If chars different
				charEqual = false;
				break; // stops comparing chars - words are different
			}
		}

		return charEqual;

		/* 
		* TODO 5: Implement "explorePalindrome"
		*/
	}




	public static void explorePalindrome(String text) {

	/* 
	* TODO 6: Implement "explorePalindrome" & helper function
	*/
	// convert string to lowercase
		//text = text.toLowerCase();

		// convert string to lowercase, use reverseStringAndRemoveNonAlpha	
		String revText = reverseStringAndRemoveNonAlpha(text.toLowerCase());

		decomposeText(text, revText, 0, new MyStack()); //starts at 0 = first letter of word
	}

	//helper function
	public static void decomposeText(String originalText, String textToDecompose,int index, MyStack decomposition) {
		//if index at end of word, print out words
		if (index == textToDecompose.length()) {
			System.out.println(originalText + ": " + stackToReverseString(decomposition));
		}
			//find potential words at index
			String[] words = getWords(textToDecompose, index);

		/*for each word: 
			- push to stack
			- recurse at next index (after found word): index + length of word
			- pop from stack */
			for(int i = 0; i < words.length; i++) {
				decomposition.push(words[i]);
				decomposeText(originalText, textToDecompose, index + words[i].length(), decomposition);
				decomposition.pop();
			}

	/* 
	* TODO 6
	*/

	}


	// This function looks at the arguments that are passed 
	// and decides whether to test palindromes or expand them
	public static void main(String[] args) throws IOException {

		if (args.length == 0) {
			System.out.println("ERROR: Remember to set the mode with an argument: 'test' or 'expand'");
		} else {
			String mode = args[0];

			// Default palindromes to use if none are provided
			String[] testPalindromes = {"A", "ABBA", "oh no an oboe", "salami?", "I'm alas, a salami"};
			if (args.length > 1)
				testPalindromes = Arrays.copyOfRange(args, 1, args.length);

			// Test whether the provided strings are palindromes
			if (mode.equals("test")) {

				for (int i = 0; i < testPalindromes.length; i++) {
					String text = testPalindromes[i];
					boolean result = isPalindrome(text);
					System.out.println("'" + text + "': " + result);
				}

			} else if (mode.equals("expand")) {
				for (int i = 0; i < testPalindromes.length; i++) {

					explorePalindrome(testPalindromes[i]);
				}	
			}
			else {
				System.out.println("unknown mode: " + mode);
			}
		}
	}
}