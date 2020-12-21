package Day15;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Challenge15 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
			File myObj = new File("src/Day15/input15.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String nextLine = myReader.nextLine();
				input.add(nextLine);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		
		int num = 0;
		List<String> numbers = Arrays.asList(input.get(0).split(","));
		Map<Integer, List<Integer>> numbersTurns = new HashMap<>();
		Map<Integer, Integer> numbersCounter = new HashMap<>();
		// PART 1
		// for(int i=1; i<=2020; i++) {
		// PART 2
		for(int i=1; i<=30000000; i++) {
			// Last turn
			if(i<=numbers.size()) {
				num = Integer.parseInt(numbers.get(i-1));
				List<Integer> turns = new ArrayList<>();
				turns.add(i);
				numbersTurns.put(num, turns);
				numbersCounter.put(num, 1);
			} else {
				/*
				 * Every turn:
				*/
				
				/*
				 * If the number has been spoken for the first time --> increment the counter
				 * If the number has been already spoken --> retrieve the last 2 turns it has been spoken and return their difference
				 */
				if(numbersCounter.containsKey(num) && numbersCounter.get(num) == 1 || !numbersCounter.containsKey(num)) {
					numbersCounter.put(num, numbersCounter.get(num)+1);
					num = 0;
				} else if(numbersCounter.containsKey(num) && numbersCounter.get(num) > 1) {
					int p1 = numbersTurns.get(num).get(numbersTurns.get(num).size()-2);
					int p2 = numbersTurns.get(num).get(numbersTurns.get(num).size()-1);
					num = p2-p1;
				}
				
				/*
				 * If the number has been spoken for the first time --> save the current turn
				 * If the number has been already spoken --> add the current turn to its existing ones
				 */
				if(numbersTurns.containsKey(num)) {
					numbersTurns.get(num).add(i);
				} else {
					List<Integer> turns = new ArrayList<>();
					turns.add(i);
					numbersTurns.put(num, turns);
				}
				/*
				 * If the number has been spoken for the first time --> set the counter to 1
				 * If the number has been already spoken --> increment its counter
				 */
				if(numbersCounter.containsKey(num)) {
					numbersCounter.put(num, numbersCounter.get(num)+1);
				} else {
					numbersCounter.put(num, 1);
				}
			}
		}
		System.out.println(num);
	}
}