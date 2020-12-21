package Day04;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Challenge04 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
	      File myObj = new File("src/Day04/input04.txt");
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	    	  input.add(myReader.nextLine());
	      }
	      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		
		List<String> fields = Arrays.asList("byr", "ecl", "eyr", "hcl", "hgt", "iyr", "pid");
		java.util.Collections.sort(fields);
		List<String> occourrences = new ArrayList<>();
		int count=0;
		
		// PART 1
		// Loop through the passports
		for(int i=0; i<input.size(); i++) {
			// If the line if empty, it means all the fields of the current passport has been evaluated
			if(input.get(i).isEmpty()) {
				// Sort the list of occurrences for the single passport and if it's the same as the list of valid fields, consider the passport as valid 
				java.util.Collections.sort(occourrences);
				if(fields.equals(occourrences)) {
					count++;
				}
				occourrences = new ArrayList<>();
				continue;
			}
			
			// If a line is not empty, retrieve the fields with their values
			String[] inputSplit = input.get(i).split(" ");
			
			// Loop through the fields and their values
			for(String s : inputSplit) {
				String[] sSplit = s.split(":");
				// If the list of valid fields contains the field evaluated, then add it to the list of occurrences
				if(fields.contains(sSplit[0])) {
					occourrences.add(sSplit[0]);
				}
			}
			// Consider the last position and perform the same operations in case of new line, since it has to be included for the last passport
			if(i==input.size()-1) {
				java.util.Collections.sort(occourrences);
				if(fields.equals(occourrences)) {
					count++;
				}
			}
			
		}
		System.out.println(count);
		
		// PART 2
		occourrences = new ArrayList<>();
		count=0;
		// Loop through the passports
		for(int i=0; i<input.size(); i++) {
			// If the line if empty, it means all the fields of the current passport has been evaluated
			if(input.get(i).isEmpty()) {
				// Sort the list of occurrences for the single passport and if it's the same as the list of valid fields, consider the passport as valid
				java.util.Collections.sort(occourrences);
				if(fields.equals(occourrences)) {
					count++;
				}
				occourrences = new ArrayList<>();
				continue;
			}
			// If a line is not empty, retrieve the fields with their values
			String[] inputSplit = input.get(i).split(" ");
			
			// Loop through the fields and their values
			for(String s : inputSplit) {
				String[] sSplit = s.split(":");
				
				// If the list of valid fields contains the field evaluated and the field evaluated respect the defined rules, add it to the list of occurrences
				/*
				 * Defined rules:
				 * byr (Birth Year) - four digits; at least 1920 and at most 2002.
				 * iyr (Issue Year) - four digits; at least 2010 and at most 2020.
				 * eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
				 * hgt (Height) - a number followed by either cm or in:
				 * 	- If cm, the number must be at least 150 and at most 193.
				 *  - If in, the number must be at least 59 and at most 76.
				 *  hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
				 *  ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
				 *  pid (Passport ID) - a nine-digit number, including leading zeroes.
				 *  cid (Country ID) - ignored, missing or not.
				 */
				boolean isHgtValid=false;
				if(sSplit[0].equals("hgt")) {
					if(sSplit[1].contains("cm")) {
						int cm = Integer.parseInt(sSplit[1].split("cm")[0]);
						isHgtValid = cm >= 150 && cm<= 193;
					} else if(sSplit[1].contains("in")) {
						int in = Integer.parseInt(sSplit[1].split("in")[0]);
						isHgtValid = in >= 59 && in<= 76;
					}
				}
				boolean isHclValid = false;
				if(sSplit[0].equals("hcl")) {
					if(sSplit[1].charAt(0)=='#' && sSplit[1].length() == 7) {
						for(int k=0; k<sSplit[1].length(); k++) {
							int a = sSplit[1].charAt(k);
							if((a >= 48 && a <= 57)
							|| (a >= 97 && a <= 102)) {
								isHclValid = true;
							}
						}
					}
				}
				List<String> eyeColors = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
				if(fields.contains(sSplit[0]) &&
					((sSplit[0].equals("byr") && Integer.parseInt(sSplit[1]) >= 1920 && Integer.parseInt(sSplit[1]) <= 2002)
					|| (sSplit[0].equals("iyr") && Integer.parseInt(sSplit[1]) >= 2010 && Integer.parseInt(sSplit[1]) <= 2020)
					|| (sSplit[0].equals("eyr") && Integer.parseInt(sSplit[1]) >= 2020 && Integer.parseInt(sSplit[1]) <= 2030)
					|| (isHgtValid)
					|| (isHclValid)
					|| (sSplit[0].equals("ecl") && eyeColors.contains(sSplit[1]))
					|| (sSplit[0].equals("pid") && sSplit[1].length() == 9 )
					))
				{
					occourrences.add(sSplit[0]);
				}
			}
			// Consider the last position and perform the same operations in case of new line, since it has to be included for the last passport
			if(i==input.size()-1) {
				java.util.Collections.sort(occourrences);
				if(fields.equals(occourrences)) {
					count++;
				}
			}
			
		}
		
		
		System.out.println(count);
		
	}

}
