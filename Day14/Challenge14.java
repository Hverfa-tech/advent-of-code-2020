package Day14;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Challenge14 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
			File myObj = new File("src/Day14/input14.txt");
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

		// PART 1
		long sum=0;
		Map<Integer, Long> positionsOccupied = new HashMap<>();
		String mask = "";
		for(int i=0; i<input.size(); i++) {
			// Retrieve the mask
			if(input.get(i).contains("mask")) {
				mask = input.get(i).split("= ")[1];
			} else {
				// Retrieve the memory position to write in
				int pos = Integer.parseInt(input.get(i).split("\\[")[1].split("\\]")[0]);
				// Retrieve the binary value to be written and add the leading zeros to match it with the mask
				String valueBinary = Integer.toBinaryString(Integer.parseInt(input.get(i).split("= ")[1]));
				int leadingZeros = mask.length()-valueBinary.length();
				for(int j=0; j<leadingZeros; j++) {
					valueBinary = "0"+valueBinary;
				}
				// Calculate the binary value result after applying the mask to the binary value retrieved 
				String resultBinary = "";
				for(int j=0; j<valueBinary.length(); j++) {
					if(mask.charAt(j) == 'X') {
						resultBinary += valueBinary.charAt(j);
					} else {
						resultBinary += mask.charAt(j);
					}
				}
				long result = Long.parseLong(resultBinary,2);
				
				/*
				 * Add the converted decimal value of result to the position-result map
				 * Map is used because one or more of the next instructions could write in the same memory position
				 */
				positionsOccupied.put(pos, result);
			}
		}
		
		// Sum up every decimal value of every position
		for (Map.Entry<Integer, Long> entry : positionsOccupied.entrySet()) {
	        sum += entry.getValue();
	    }
		System.out.println(sum);
		
		// PART 2
		sum=0;
		Map<Long, Long> positionsOccupied2 = new HashMap<>();
		mask = "";
		for(int i=0; i<input.size(); i++) {
			// Retrieve the mask
			if(input.get(i).contains("mask")) {
				mask = input.get(i).split("= ")[1];
			} else {
				// Retrieve the memory position to write in
				int pos = Integer.parseInt(input.get(i).split("\\[")[1].split("\\]")[0]);
				String posBinary = Integer.toBinaryString(pos);
				// Retrieve the binary value to be written and add the leading zeros to match it with the mask
				String valueBinary = Integer.toBinaryString(Integer.parseInt(input.get(i).split("= ")[1]));
				int leadingZeros = mask.length()-posBinary.length();
				for(int j=0; j<leadingZeros; j++) {
					posBinary = "0"+posBinary;
				}
				// Calculate the binary value result after applying the mask to the binary value retrieved 
				String resultBinary = "";
				for(int j=0; j<posBinary.length(); j++) {
					if(mask.charAt(j) == 'X') {
						resultBinary += 'X';
					} else if(mask.charAt(j) == '1') {
						resultBinary += mask.charAt(j);
					} else {
						resultBinary += posBinary.charAt(j);
					}
				}
				
				// Generate all the possible binary combination considering the floating values of the mask (X)
				List<String> binaryList = findBinary(resultBinary);
				
				/*
				 * Add the converted decimal value of result to the position-result map (exclude the binary values that still contains the "X")
				 * Map is used because one or more of the next instructions could write in the same memory position
				 */
				for(String s:binaryList) {
					if(!s.contains("X")) {
						long posDecimal = Long.parseLong(s,2);
						long result = Long.parseLong(valueBinary,2);
						positionsOccupied2.put(posDecimal, result);
					}
				}
			}
		}
		// Sum up every decimal value of every position
		for (Map.Entry<Long, Long> entry : positionsOccupied2.entrySet()) {
	        sum += entry.getValue();
	    }
		System.out.println(sum);
	}
	
	private static List<String> findBinary(String s) {
		
		List<String> binaryList = new ArrayList<>();
		int index = s.indexOf('X');
		String s1 = s.substring(0, index) + '0' + (index+1<s.length() ? s.substring(index+1) : "");
		String s2 = s.substring(0, index) + '1' + (index+1<s.length() ? s.substring(index+1) : "");
		binaryList.add(s1);
		binaryList.add(s2);
		
		if(s1.contains("X")) {
			binaryList.addAll(findBinary(s1));
			binaryList.addAll(findBinary(s2));
		}
		
		return binaryList;
	}
}