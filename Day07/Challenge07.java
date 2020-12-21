package Day07;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Challenge07 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
	      File myObj = new File("src/Day07/input07.txt");
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
		List<String> searches = new ArrayList<>();
		searches.add("shiny gold");
		int total = occourrences(0, input, searches);
		System.out.println(total);
		
		// PART 2
		int bags = 0;
		Stack<String> bagsToProcess = new Stack<String>();
		Stack<Integer> nums = new Stack<Integer>();
		bagsToProcess.add("shiny gold");
		nums.add(1);
		
		 /* For each line, find which and how many bags it contains.
		  * If it doesn't contain any bags, continue to next line;
		  * Otherwise, store its contained bags as elements to loop through and repeat recursively the process (if the stack of elements to search is not empty)
		  * 
		 */
		while (!bagsToProcess.isEmpty()) {
		    String bag = bagsToProcess.pop();
		    int num = nums.pop();
		    
		    int j = 0;
		    while (!input.get(j).startsWith(bag)) {
		        j++;
		    }

		    String in = input.get(j);
		    String[] contents = in.substring(in.indexOf("contain") + 8).replace(".", "").split(", ");
		   
		    if (!contents[0].equals("no other bags")) {
		        for (String c : contents) {
		            String b = c.substring(c.indexOf(" ")).replace("bags", "").trim();
		            int n = Integer.parseInt(c.substring(0, c.indexOf(" ")).trim());
		            bagsToProcess.add(b);
		            nums.add(n * num);
		            bags += n * num;
		        }
		    }
		}
		System.out.println(bags);
		
	}

	private static int occourrences(int count, List<String> input, List<String> searches) {
		// Use a support list, since the list of elements to search will be modified
		List<String> searches2 = new ArrayList<>();
		
		/* 
		 * For each line, check if the line contains the elements to search (searches list):
		 * 	- If NO, continue to next line;
		 * 	- If YES, store its contained bags as elements to search and repeat recursively the process (if the list of elements to search is not empty)
		 */
		for(int i=0; i<input.size(); i++) {
			for(int j=0; j<searches.size(); j++) {
				if(input.get(i).contains(searches.get(j)) && !input.get(i).split("bags")[0].trim().equals(searches.get(j).trim())) {
					count++;
					searches2.add(input.get(i).split("bags")[0].trim());
					input.remove(i);
					i--;
					break;
				}
			}
		}
		
		if(searches2.size()>0) {
			count = occourrences(count, input, searches2);
		}
		return count;
	}
	
}
