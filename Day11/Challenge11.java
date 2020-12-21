package Day11;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge11 {
	
	public static void main(String[] args) {
		List<String> input1 = new ArrayList<>();
		List<String> output1 = new ArrayList<>();
		List<String> input2 = new ArrayList<>();
		List<String> output2 = new ArrayList<>();
		try {
	      File myObj = new File("src/Day11/input11.txt");
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	    	  String nextLine = myReader.nextLine();
	    	  input1.add(nextLine);
	    	  output1.add(nextLine);
	    	  input2.add(nextLine);
	    	  output2.add(nextLine);
	      }
	      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		
		System.out.println(getFinalSeatsOccupied1(input1, output1));
		System.out.println(getFinalSeatsOccupied2(input1, output2));
		
	}
	
	// PART 1
	
	public static int getFinalSeatsOccupied1(List<String> input, List<String> output) {
		// Loop through the rows
		for(int i=0; i<input.size(); i++) {
			// Loop through the columns
			for(int j=0; j<input.get(i).length(); j++) {
				// Count all the occupied adjacent neighbors of the current seat
				int countOccupied = 0;
				for(int k=-1; k<2; k++) {
					for(int l=-1; l<2; l++) {
						if((i+k) >= 0 && (j+l) >= 0 && (i+k) < input.size() && (j+l) < input.get(i).length()
							&& ((i+k) != i || (j+l) != j) && input.get(i+k).charAt(j+l) == '#') {
							countOccupied++;
						}
					}
				}
				/*
				 * If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied
				 * If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty
				 */
				if(input.get(i).charAt(j) == 'L' && countOccupied == 0) {
					StringBuilder newString = new StringBuilder(output.get(i));
					newString.setCharAt(j, '#');
					output.set(i, newString.toString());
				} else if(input.get(i).charAt(j) == '#' && countOccupied >= 4) {
					StringBuilder newString = new StringBuilder(output.get(i));
					newString.setCharAt(j, 'L');
					output.set(i, newString.toString());
				}
			}
		}
		
		// Evaluate recursively the next list until the output stabilizes (it doesn't change anymore)
		if(!input.equals(output)) {
			input = new ArrayList<>();
			for(String s : output) {
				input.add(s);
			}
			getFinalSeatsOccupied1(input, output);
		}
		
		int result=0;
		for(String s : output) {
			for(int i=0; i<s.length(); i++) {
				if(s.charAt(i)=='#') {
					result++;
				}
			}
		}
		return result;
	}
	
	// PART 2
	public static int getFinalSeatsOccupied2(List<String> input, List<String> output) {
		// Loop through the rows
		for(int i=0; i<input.size(); i++) {
			// Loop through the columns
			for(int j=0; j<input.get(i).length(); j++) {
				// Count all the occupied neighbors of the current seat along every direction
				int countOccupied = 0;
				List<String> directions = new ArrayList<>();
				directions.add("1,0");
				directions.add("-1,0");
				directions.add("1,1");
				directions.add("-1,-1");
				directions.add("1,-1");
				directions.add("-1,1");
				directions.add("0,1");
				directions.add("0,-1");
				
				for(String s : directions) {
					String[] split = s.split(",");
					int k=Integer.parseInt(split[0]);
					int l=Integer.parseInt(split[1]);
					int posX = i+k;
	                int posY = j+l;
	                while(posX >= 0 && posY >= 0 && posX < input.size() && posY < input.get(i).length()) {
	                    if(input.get(posX).charAt(posY) == '#') {
	                    	countOccupied++;
	                        break;
	                    }
	                    if(input.get(posX).charAt(posY) == 'L') {
	                        break;
	                    }
	                    posX += k;
	                    posY += l;
	                }
				}
				
				/*
				 * If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied
				 * If a seat is occupied (#) and five or more seats adjacent to it are also occupied, the seat becomes empty
				 */
				if(input.get(i).charAt(j) == 'L' && countOccupied == 0) {
					StringBuilder newString = new StringBuilder(output.get(i));
					newString.setCharAt(j, '#');
					output.set(i, newString.toString());
				} else if(input.get(i).charAt(j) == '#' && countOccupied >= 5) {
					StringBuilder newString = new StringBuilder(output.get(i));
					newString.setCharAt(j, 'L');
					output.set(i, newString.toString());
				}
			}
		}
		// Evaluate recursively the next list until the output stabilizes (it doesn't change anymore)
		if(!input.equals(output)) {
			input = new ArrayList<>();
			for(String s : output) {
				input.add(s);
			}
			getFinalSeatsOccupied2(input, output);
		}
		
		int result=0;
		for(String s : output) {
			for(int i=0; i<s.length(); i++) {
				if(s.charAt(i)=='#') {
					result++;
				}
			}
		}
		return result;
	}
}
