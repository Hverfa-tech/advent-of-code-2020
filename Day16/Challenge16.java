package Day16;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Challenge16 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
			File myObj = new File("src/Day16/input16.txt");
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

		// PART 1 + PART 2
		List<String> rules = new ArrayList<>();
		List<String> rules2 = new ArrayList<>();
		List<String> nearbyTickets = new ArrayList<>();
		String myTicket = "";
		boolean passedNearby = false, passedMine = false;
		
		// Initialize list of rules, list of nearby tickets and myTicket for part1 and part2
		for(String s:input) {
			if(s.contains("or")) {
				rules.add(s.split(": ")[1].split(" or ")[0]);
				rules.add(s.split(": ")[1].split(" or ")[1]);
				rules2.add(s);
			} else if(s.contains("nearby")) {
				passedNearby = true;
				continue;
			} else if(s.contains("your")) {
				passedMine = true;
				continue;
			}
			if(passedNearby) {
				nearbyTickets.add(s);
			}
			if(passedMine) {
				myTicket = s;
				passedMine = false;
			}
		}
		
		// Find the numbers that makes a nearby ticket invalid and add it to the invalid list
		List<Integer> invalids = new ArrayList<>();
		for(String n:nearbyTickets) {
			String[] tickets = n.split(",");
			for(String t:tickets) {
				int num = Integer.parseInt(t);
				boolean isValid = false;
				for(String r:rules) {
					int r1 = Integer.parseInt(r.split("-")[0]);
					int r2 = Integer.parseInt(r.split("-")[1]);
					if(num>=r1 && num<=r2) {
						isValid = true;
						break;
					}
				}
				if(!isValid) {
					invalids.add(num);
				}
			}
		}
		
		// Calculate the error rate as the sum of the invalid numbers found
		int errorRate=0;
		for(int i:invalids) {
			errorRate+=i;
		}
		
		System.out.println(errorRate);
		
		// PART 2
		
		// Map to save the columns (key) and the list of rules (value) for which the column is valid
		Map<Integer,List<String>> rulePosition = new HashMap<>();
		List<String> valids = new ArrayList<>();
		
		// Find the valid tickets and add them to the valid list
		for(String n:nearbyTickets) {
			String[] tickets = n.split(",");
			int countValid = 0;
			for(String t:tickets) {
				int num = Integer.parseInt(t);
				for(String r:rules) {
					int r1 = Integer.parseInt(r.split("-")[0]);
					int r2 = Integer.parseInt(r.split("-")[1]);
					if(num>=r1 && num<=r2) {
						countValid++;
						break;
					}
				}
			}
			if(countValid==tickets.length) {
				valids.add(n);
			}
		}

		int column = 0;
		// Loop through every column of the valid tickets list
		while(column<valids.get(0).split(",").length) {
			List<String> validRules = new ArrayList<>();
			
			// For every columns, loop through the rules
			for (int j = 0; j < rules2.size(); j++) {
				
				String ruleName = rules2.get(j).split(": ")[0];
				int r1 = Integer.parseInt(rules2.get(j).split(": ")[1].split(" or ")[0].split("-")[0]);
				int r2 = Integer.parseInt(rules2.get(j).split(": ")[1].split(" or ")[0].split("-")[1]);
				int r3 =Integer.parseInt(rules2.get(j).split(": ")[1].split(" or ")[1].split("-")[0]);
				int r4 = Integer.parseInt(rules2.get(j).split(": ")[1].split(" or ")[1].split("-")[1]);
				
				int countValid = 0;
				
				// Find for every row the number in the considered column.
				for(int i=0; i<valids.size(); i++) {
					String[] splt = valids.get(i).split(",");
					int num = Integer.parseInt(splt[column]);
					
					if((num>=r1 && num<=r2) || (num>=r3 && num<=r4)) {
						countValid++;
					}
				}
				
				// If the number at the selected column is valid for all the rows, add it to the list of rules for that column
				if(countValid == valids.size()) {
					validRules.add(ruleName);
				}
			}
			
			// Add the column and the list of its rules to the map
			rulePosition.put(column, validRules);
			column++;
		}
		
		// Retrieve the correct rules order
		rulePosition = findOrderedList(rulePosition);
		
		String[] myTicketFields = myTicket.split(",");
		long mult=1;
		
		// Calculate the multiplication of myTicket values based on the rules order found
		int i=0;
		for(Map.Entry<Integer, List<String>> entry : rulePosition.entrySet()) {
			if(entry.getValue().get(0).contains("departure")) {
				mult*=Integer.parseInt(myTicketFields[i]);
			}
			i++;
		}
		System.out.println(mult);
		
	}
	
	private static Map<Integer, List<String>> findOrderedList(Map<Integer, List<String>> rulePosition ) {
		
		// Loop through the column-rules map
		for(Map.Entry<Integer, List<String>> entry : rulePosition.entrySet()) {
			// If the rules list has one element, remove it from the rules list of the other columns
			if(entry.getValue().size() == 1) {
				String valueToRemove = entry.getValue().get(0);
				for(Map.Entry<Integer, List<String>> entry2 : rulePosition.entrySet()) {
					if(entry!=entry2) {
						entry2.getValue().remove(valueToRemove);
					}
				}
			}
		}
		
		// Count how many rules list have only 1 element
		int count = 0;
		for(Map.Entry<Integer, List<String>> entry : rulePosition.entrySet()) {
			if(entry.getValue().size() == 1) {
				count++;
			}
		}
	
		// If every rules list has only 1 element return the list, otherwise retrieve it recursively 
		if(count == rulePosition.size()) {
			return rulePosition;
		}  else {
			rulePosition = findOrderedList(rulePosition);
		}
		
		
		return rulePosition;
		
	}
}