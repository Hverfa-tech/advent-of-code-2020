package Day03;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge03 {

	public static void main(String[] args) {
		List<String> trees = new ArrayList<>();
		try {
	      File myObj = new File("src/Day03/input03.txt");
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	    	  trees.add(myReader.nextLine());
	      }
	      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		
		// PART 1
		int index = 0;
		int answer = 0;
		// Count the trees encountered following the slope of right (value to sum to index) 3 and down 1 (row)
		for (int row = 1; row < trees.size();row++ ) {
			index = (index + 3) % trees.get(row).length();
			if (trees.get(row).charAt(index)=='#') {
				answer++;
			}
		}
		
		System.out.println(answer);
		
		// PART 2
		// Same algorithm of part one but with different inputs:
		/*
		 * Right 1, down 1 = 104
		 * Right 3, down 1 = 230 (same value as in Part 1)
		 * Right 5, down 1 = 83
		 * Right 7, down 1 = 98
		 * Right 1, down 2 = 49		 
		*/
		// The result is the multiplication of the values found

	}

}
