package Day23;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge23 {

	public static void main(String[] args) {
		List<Integer> input = new ArrayList<>();
		String line = "";
		try {
			File myObj = new File("src/Day23/input23.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				line = myReader.nextLine();
				for(int i=0; i<line.length(); i++) {
					input.add(Integer.parseInt(line.charAt(i)+""));
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		// PART 1
		playGame(input, 100);
		
		String labelsAfterOne = "";
		int indexOne = input.indexOf(1);
		for(int i=indexOne+1; i<input.size(); i++) {
			labelsAfterOne += input.get(i);
		}
		for(int i=0; i<indexOne; i++) {
			labelsAfterOne += input.get(i);
		}
		System.out.println(labelsAfterOne);
		
		// PART 2
		int[] input2 = new int[1000001];
		int i=0;
		for (i=0; i<=line.length()-2; i++) {
			input2[Integer.parseInt(line.substring(i, i+1))] = Integer.parseInt(line.substring(i+1, i+2));
		}
		input2[Integer.parseInt(line.substring(i, i+1))] = 10;
		for (i = 10; i <= 1000000; i++) {
			input2[i] = i+1;
		}
		input2[1000000] = Integer.parseInt(line.substring(0, 1));
		
		List<Integer> input2List = new ArrayList<>();
		for(int in : input2) {
			input2List.add(in);
		}

		playGame2(line, input2,10000000);
		
		long result = (long) input2[1] * input2[input2[1]];

		System.out.println(result);
	}
	
	private static void playGame(List<Integer> input, int moves) {
		
		// Select current cup
		int current = input.get(0);
		
		for(int move=1; move<=moves; move++) {
			// Pick up 3 cups clockwise of the current cup
			List<Integer> pickUps = new ArrayList<>();
			int indexToRemove = input.indexOf(current)+1;
			for(int i=0; i<3; i++) {
				if(indexToRemove>=input.size()) {
					indexToRemove = 0;
				}
				pickUps.add(input.remove(indexToRemove));
			}
			
			// Select destination cup
			/*
			 * the cup with a label equal to the current cup's label minus one. If this would select one of the cups that was just picked up,
			 * the crab will keep subtracting one until it finds a cup that wasn't just picked up. If at any point in this process the value goes 
			 * below the lowest value on any cup's label, it wraps around to the highest value on any cup's label instead.
			 */
			int destination = current-1;
			int destinationIndex = -1;
			if(input.contains(destination)) {
				destinationIndex = input.indexOf(destination);
			} else {
				while(!input.contains(destination)) {
					destination--;
					if(destination<=0) {
						int max = 0;
						for(int in : input) {
							if(in > max) {
								max = in;
							}
						}
						destination = max;
					}
				}
				destinationIndex = input.indexOf(destination);
			}
			
			// Place the pick ups clockwise of the destination cup
			while(!pickUps.isEmpty()) {
				input.add(++destinationIndex, pickUps.remove(0));
			}
			
			// Select current cup
			int indexCurrent = input.indexOf(current)+1;
			if(indexCurrent>=input.size()) {
				indexCurrent = 0;
			}
			current = input.get(indexCurrent);
		}
	}
	
	private static void playGame2(String inputString, int[] input, int moves) {

		// Same steps of playGame, but adapted to use array instead to increase performance
		int current = Integer.parseInt(inputString.substring(0, 1));
		
		for (int move = 1; move <= moves; move++) {
			int a = input[current];
			int b = input[a];
			int c = input[b];
			
			int destination = current - 1;

			if (destination <= 0) {
				destination = input.length - 1;
			}
			
			while (destination == a || destination == b || destination == c) {
				destination--;
				if (destination <= 0) {
					destination = input.length - 1;
				}
			}

			input[current] = input[c];
			int temp = input[destination];
			input[destination] = a;
			input[c] = temp;
			current = input[current];

		}

	}
}