package Day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge01 {

	public static void main(String[] args) {
		List<Integer> numbers = new ArrayList<>();
		try {
	      File myObj = new File("src/Day01/input01.txt");
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	        numbers.add(Integer.parseInt(myReader.nextLine()));
	      }
	      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		
		// PART 1
		// Find the 2 entries that sum to 2020
		long result = 0;
		for(int i=0; i<numbers.size(); i++) {
			for(int j=0; j<numbers.size(); j++) {
				if(i != j && numbers.get(i) + numbers.get(j) == 2020) {
					result = numbers.get(i) * numbers.get(j);
					break;
				}
			}
		}
		
		System.out.println(result);
		
		// PART 2
		// Find the 3 entries that sum to 2020
		result = 0;
		for(int i=0; i<numbers.size(); i++) {
			for(int j=0; j<numbers.size(); j++) {
				for(int k=0; k<numbers.size(); k++) {
					if(i != j && numbers.get(i) + numbers.get(j) + numbers.get(k) == 2020) {
						result = numbers.get(i) * numbers.get(j) * numbers.get(k);
						break;
					}
				}
			}
		}
		System.out.println(result);
	}

}
