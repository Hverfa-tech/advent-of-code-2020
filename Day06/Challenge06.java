package Day06;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Challenge06 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
	      File myObj = new File("src/Day06/input06.txt");
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	    	  input.add(myReader.nextLine());
	      }
	      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		
		// PART 1
		int sum=0;
		Set<Character> answers = new HashSet<>();
		// Loop through the answers
		for(int i=0; i<input.size(); i++) {
			// If the line if empty, it means all the values of the current answers group has been evaluated
			if(input.get(i).isEmpty()) {
				// Sum the content of the Set
				sum+=answers.size();
				answers = new HashSet<>();
				continue;
			}
			// Add the values of the answer to a Set, to avoid duplicate values 
			for(int j=0; j<input.get(i).length(); j++) {
				answers.add(input.get(i).charAt(j));
			}
			// Consider the last position and perform the same operations in case of new line, since it has to be included for the last answer
			if(i==input.size()-1) {
				sum+=answers.size();
			}
			
		}
		System.out.println(sum);
		
		// PART 2
		sum=0;
		int groupSize=0;
		Map<Character, Integer> count = new HashMap<>();
		answers = new HashSet<>();
		// Loop through the answers
		for(int i=0; i<input.size(); i++) {
			// If the line if empty, it means all the values of the current answers group has been evaluated
			if(input.get(i).isEmpty()) {
				// For each group, check if all answers contains the same value (everyone replied "yes")
				for (Map.Entry<Character, Integer> me : count.entrySet()) {
					if(me.getValue() == groupSize) {
						answers.add(me.getKey());
					}
				}
				sum+=answers.size();
				groupSize = 0;
				answers = new HashSet<>();
				count = new HashMap<>();
				continue;
			}
			
			// Loop through the values of the answer
			for(int j=0; j<input.get(i).length(); j++) {
				char key = input.get(i).charAt(j);
				int value = 1;
				// Add the value (key) and its occurrences in the answer (value) in the Map
				if(count.containsKey(key)) {
					for (Map.Entry<Character, Integer> me : count.entrySet()) {
						if(me.getKey() == key) {
							value = me.getValue()+1;
						}
					}
				}
				count.put(key, value);
			}
			groupSize++;
			// Consider the last position and perform the same operations in case of new line, since it has to be included for the last answer
			if(i==input.size()-1) {
				for (Map.Entry<Character, Integer> me : count.entrySet()) {
					if(me.getValue() == groupSize) {
						answers.add(me.getKey());
					}
				}
				sum+=answers.size();
			}
		}
		System.out.println(sum);
	}

}
