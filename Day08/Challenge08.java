package Day08;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge08 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		List<String> inputCopy = new ArrayList<>();
		try {
	      File myObj = new File("src/Day08/input08.txt");
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	    	  String line = myReader.nextLine();
	    	  input.add(line);
	    	  inputCopy.add(line);
	      }
	      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		
		// PART 1
		System.out.println(validate(input));
		
		
		// PART 2
		int result = 0;
		/*
		 * For every line, if the operation is "nop" or "jmp" evaluate if inverting its value will avoid the infinite loop
		 * Basically, the input list gets evaluated at every position with only a value inverted, in order to identify which single value will avoid the infinite loop to occur
		 */
		for(int i = 0; i < input.size(); i++) {
			if(i-1>=0) {
				String s = inputCopy.get(i-1);
				input.set(i-1, s);
			}

			String action = input.get(i).split(" ")[0];
			String offset = input.get(i).split(" ")[1];

		    if(action.equals("jmp")) {
		    	input.set(i, "nop "+offset);
		    	List<Integer> output = validate2(input);
		    	result = output.get(0);
		    	if(output.get(1) == 1) {
		    		break; 
		    	}
		    }
		    else if(action.equals("nop") && Integer.parseInt(offset) != 0) {
		    	input.set(i, "jmp "+offset);
		    	List<Integer> output = validate2(input);
		    	result = output.get(0);
		    	if(output.get(1) == 1) {
		    		break; 
		    	}
		    }
		}

		System.out.println(result);
		
	}
	
	private static int validate(List<String> input) {
		int accumolator = 0;
		/* In order to identify an operation that has already occurred, a special char (!) will be added at the end of the line
		 * If the operation doesn't contain the special char, the corresponding values will be calculated
		 */
		for(int i=0; i<input.size(); i++) {
			if(!input.get(i).contains("!")) {
				String action = input.get(i).split(" ")[0];
				if(action.equals("nop")) {
					input.set(i, input.get(i)+"!");
					continue;
				}
				int offset = Integer.parseInt(input.get(i).split(" ")[1]);
				if(action.equals("acc")) {
					accumolator+=offset;
					input.set(i, input.get(i)+"!");
				}
				if(action.equals("jmp")) {
					input.set(i, input.get(i)+"!");
					i+=(offset-1);
				}
			} else {
				break;
			}
		}
		return accumolator;
	}
	
	private static List<Integer> validate2(List<String> input2) {
		/* In order to identify an operation that has already occurred, a special char (!) will be added at the end of the line
		 * If the operation doesn't contain the special char, the corresponding values will be calculated
		 * Additionally, a value (terminated) it's considered to identify the position to change to avoid the infinite loop
		 */
		int accumolator = 0, terminated = 0;
		List<Integer> result = new ArrayList<>();
		for(int i=0; i<input2.size(); i++) {
			if(!input2.get(i).contains("!")) {
				String action = input2.get(i).split(" ")[0];
				if(action.equals("nop")) {
					input2.set(i, input2.get(i)+"!");
					continue;
				}
				int offset = Integer.parseInt(input2.get(i).split(" ")[1]);
				if(action.equals("acc")) {
					accumolator+=offset;
					input2.set(i, input2.get(i)+"!");
				}
				if(action.equals("jmp")) {
					input2.set(i, input2.get(i)+"!");
					i+=(offset-1);
				}
				terminated = i==input2.size()-1 ? 1 : 0;
			} else {
				break;
			}
		}
		/*
		 * The input is cleaned of the special chars in case the function will be invoked again, since the same input list is considered every time 
		 */
		for(int i=0; i<input2.size(); i++) {
			if(input2.get(i).contains("!")) {
				input2.set(i, input2.get(i).substring(0, input2.get(i).length()-1));
			}
		}
		result.add(accumolator);
		result.add(terminated);
		return result;
	}
}
