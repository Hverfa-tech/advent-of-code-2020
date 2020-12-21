package Day09;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Challenge09 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
	      File myObj = new File("src/Day09/input09.txt");
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
		Stack<Integer> sumNumbers = new Stack<>();
		int preamble = 25, wrongNum = 0;
		
		/* 
		 * Once initialized the preamble (sumNumbers), evaluate if the current number it's the sum of the previous 2 numbers in the preamble
		 * If not, store the current number as the "wrong number" (number that is not the sum of two of the numbers before it in the preamble)
		 */
		for(int i=0; i<input.size(); i++) {
			int currNum = Integer.parseInt(input.get(i));
			if(i<preamble) {
				sumNumbers.add(currNum);
			} else {
				int j=1, k=0;
				for(int n1 : sumNumbers) {
					while(j<sumNumbers.size()) {
						if(n1 != sumNumbers.get(j) && currNum == (n1+sumNumbers.get(j))) {
							wrongNum = 0;
							break;
						} else {
							wrongNum = currNum;
						}
						j++;
					}
					k++;
					j = k+1;
					if(wrongNum == 0) {
						break;
					}
				}
				if(wrongNum == 0) {
					sumNumbers.remove(0);
					sumNumbers.add(currNum);
				} else {
					break;
				}
				
			}
		}
		
		System.out.println(wrongNum);
		
		// PART 2
		double smallest = 0, largest = 0, sum = 0;
		List<Double> nums = new Stack<>();
		for(int i=0; i<input.size(); i++) {
			double num = Double.parseDouble(input.get(i));
			nums.add(num);
			if(num>smallest) {
				smallest = num;
			}
		}
		
		/*
		 * To find a contiguous list of numbers which values sum up to the wrong number, there are 3 cases to consider:
		 * 1 - If the sum if less than the wrong number, then the current number must be added to the sum
		 * 2 - If the sum it's greater than the wrong number, then the list of numbers it's not contiguous;
		 * 	   hence the sum must be reinitialized to 0 and the first position of the list removed, in order to consider the next value
		 * 3 - If the sum is equal to the wrong number, then the list of numbers is contiguous;
		 * 	   hence it's possible to find it's smallest and largest numbers
		 */
		for(int i=0; i<nums.size(); i++) {
			if(sum < wrongNum) {
				sum += nums.get(i);
			} else if(sum > wrongNum) {
				sum=0;
				nums.remove(0);
				i=-1;
			} else {
				for(int j=0; j<i-1; j++) {
					if(nums.get(j)<smallest) {
						smallest = nums.get(j);
					}
					if(nums.get(j)>largest) {
						largest = nums.get(j);
					}
				}
				break;
			}
		}
		
		System.out.println(smallest+largest);
	}
}
