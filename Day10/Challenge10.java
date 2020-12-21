package Day10;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Challenge10 {
	
	static Long[] cache = null;
	static long highest = 0;

	public static void main(String[] args) {
		List<Integer> input = new ArrayList<>();
		try {
	      File myObj = new File("src/Day10/input10.txt");
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	    	  input.add(Integer.parseInt(myReader.nextLine()));
	      }
	      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		
		// PART 1
		/*
		 * To count all the 1-jolt and 3-jolts difference, there are 3 cases to be considered:
		 * 1 - The (0) adapter -> the first difference is calculated comparing the first value of the input with the value 0
		 * 2 - All the adapter in the input -> their differences are calculated comparing 2 consecutive positions (since its content has been sorted)
		 * 3 - The (22) adapter -> the last difference it will a 3-jolts one, so the counter for 3-jolts starts from 1
		 */
		java.util.Collections.sort(input);
		int count1 = 0, count3 = 1, j=1;
		if(Math.abs(input.get(0)-0) == 1) {
			count1++;
		} else if(Math.abs(input.get(0)-0) == 3) {
			count3++;
		}
		for(int i=0; i<input.size(); i++) {
			int diff = Math.abs(input.get(i)-input.get(j));
			if(diff == 1) {
				count1++;
			} else if(diff == 3) {
				count3++;
			}
			if(j<input.size()-1) {
				j++;
			}
		}
		System.out.println(count1*count3);
		
		// PART 2
		/*
		 * To find all the combinations, it's necessary to recursively check if the sum of the current number and the values from 1 to 3 is present in the list
		 * If the sum is equal to the highest number in the list, a combination has been found
		 * A cache with the remaining values is used to improve the performance
		 */
		for (int i=0; i<input.size(); i++) {
			if (input.get(i) > highest) {
				highest = input.get(i);
			}
		}
		cache = new Long[Integer.parseInt(highest+"")];
		System.out.println(combinations(input, 0));
		
	}
	
	private static long combinations(List<Integer> input, int currValue) {
		if (currValue == highest) {
			return 1;
		}
		long result=0;
		for(int diff=1; diff<=3; diff++) {
			if(currValue+diff>cache.length) {
				return result;
			}
			if(input.contains(currValue+diff)) {
				int num = currValue+diff;
				List<Integer> remaining = input.stream().filter(value -> value>num).collect(Collectors.toList());
				if (cache[num-1] == null) {
					cache[num-1] = combinations(remaining, num);
				}
				result += cache[num-1];
			}
		}
		return result;
	}
}
