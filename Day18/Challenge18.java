package Day18;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge18 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
			File myObj = new File("src/Day18/input18.txt");
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

		BigInteger sum= new BigInteger("0");
		for(String s:input) {
			sum = sum.add(new BigInteger(getResult(s)));
		}
		System.out.println(sum);
	}
	
	private static String getResult(String s) {
		BigInteger result= new BigInteger("0");
		String finalString = "";
		/*
		 * If the initial string contains a parenthesis, then divide in 3 parts:
		 * 1 - Initial part before the most inner parenthesis
		 * 2 - Middle part (the most inner parenthesis)
		 * 3 - End part after the most inner parenthesis
		 */
		if(s.contains("(")) {
			String start = s.substring(0, s.lastIndexOf('('));
			int innerClosingParenthesis = s.substring(s.lastIndexOf('(')).indexOf(')') + start.length();
			String inner = s.substring((s.lastIndexOf('(')+1), innerClosingParenthesis);
			String end = s.substring(innerClosingParenthesis+1);
			
			/*
			 * Evaluate expression in most inner parenthesis
			 */
			result = evaluateExpression(inner);
			
			finalString = start + result + end;
			
			// If the final string still contains parenthesis, then 
			if(finalString.contains("(")) {
				finalString = getResult(finalString);
			} else {
				finalString = evaluateExpression(finalString)+"";
			}
		} else {
			finalString = evaluateExpression(s)+"";
		}
				
		
		return finalString;
	}
	
	private static BigInteger evaluateExpression(String s) {
		// PART 1
		/*
		 * The operators have the same precedence and are evaluated left-to-right regardless of the order in which they appear.
		 */
		/*
		String[] sSplit = s.split(" ");
		BigInteger result = new BigInteger(sSplit[0]);
		for(int i=1; i<sSplit.length; i++) {
			if((i+1)<sSplit.length) {
				if(sSplit[i].equals("+")) {
					result = result.add(new BigInteger(sSplit[i+1]));
					i++;
				} else {
					result = result.multiply(new BigInteger(sSplit[i+1]));
					i++;
				}
			}
		}
		
		return result;
		*/
		
		// PART 2
		/*
		 * Addition is evaluated before multiplication.
		 */
		BigInteger result= new BigInteger("1");
		if(s.contains("*")) {
			String[] sSplitMult = s.split("[*]");
			for(int i=0; i<sSplitMult.length; i++) {
				BigInteger c;
				if(sSplitMult[i].contains("+")) {
					c = calculateSum(sSplitMult[i].trim());
				} else {
					c = new BigInteger(sSplitMult[i].replaceAll("\\s+", ""));
				}
				result = result.multiply(c);
			}
		} else {
			result = calculateSum(s);
		}
		
		return result;
		
	}
	
	private static BigInteger calculateSum(String s) {
		String[] sSplit = s.split(" ");
		BigInteger sum = new BigInteger("0");
		for(String sp:sSplit) {
			if(!sp.equals("+")) {
				sum = sum.add(new BigInteger(sp));
			}
		}
		return sum;
	}
}