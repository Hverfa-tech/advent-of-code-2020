package Day05;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge05 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
	      File myObj = new File("src/Day05/input05.txt");
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
		List<Double> seats = new ArrayList<>();
		for(int i=0; i<input.size(); i++) {
			String rows = input.get(i).substring(0,7);
			String columns = input.get(i).substring(7);
			double start=0, end=127, row= 0;
			
			/* Looping through the rows, starting with the range 0-127.
			 * Find the value of your seat considering that:
			 *  - 'F' instruction means to take the lower half
			 *  - 'B' instruction means to take the upper half
			 */
			for(int j=0; j<rows.length(); j++) {
				if(rows.charAt(j) == 'F') {
					end = ((end-start)/2) - 0.5 + start;
				} else if(rows.charAt(j) == 'B') {
					start = ((end-start)/2) + 0.5 + start;
				}
			}
			row=start;
			
			start=0;
			end=7;
			double column= 0;
			/* Looping through the columns, starting with the range 0-7.
			 * Find the value of your seat considering that:
			 *  - 'L' instruction means to take the lower half
			 *  - 'R' instruction means to take the upper half
			 */
			for(int j=0; j<columns.length(); j++) {
				if(columns.charAt(j) == 'L') {
					end = ((end-start)/2) - 0.5 + start;
				} else if(columns.charAt(j) == 'R') {
					start = ((end-start)/2) + 0.5 + start;
				}
			}
			column = start;
			
			// Multiply the row by 8, then add the column and add the result to the list of seats
			seats.add((row*8)+column);
		}
		
		double max=0;
		// Find the highest seat ID on a boarding pass
		for(double i : seats) {
			if(i>max) {
				max=i;
			}
		}
		
		System.out.println(max);
		
		// PART 2
		java.util.Collections.sort(seats);
		double missing = 0;
		
		/*
		 * Find your seat: it's the one missing with the seats at its left and right present in the list of seats
		 */
		for(double i=0; i<max; i++) {
			if(!seats.contains(i) && seats.contains(i+1) && seats.contains(i-1)) {
				missing = i;
			}
		}
		System.out.println(missing);
	}

}
