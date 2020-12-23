package Day19;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Challenge19 {
	static Map<String, String> rules = new HashMap<>();
	static List<String> messages = new ArrayList<>();
	static Map<String, List<String>> allRules = new HashMap<>();
	static List<String> matches = new ArrayList<>();

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
			File myObj = new File("src/Day19/input19.txt");
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
		
		// Initialize rules and messages		
		for(String s:input) {
			if(s.contains(":")) {
				String[] sp = s.split(":");
				rules.put(sp[0], sp[1].trim());
			} else if(s.isEmpty()) {
				continue;
			} else {
				messages.add(s);
			}
		}
		
		// Retrieve all possible rule combinations
		String rule0 = rules.get("0");
		String[] singleRule0 = rule0.split(" ");
		for(int i=0; i<singleRule0.length; i++) {
			String comb = findRules(singleRule0[i], rules.get(singleRule0[i]), "");
			if(!matches.contains(comb)) {
				matches.add(comb);
			}
			if(matches.size() > 1) {
				matches.set(matches.size()-1, matches.get(matches.size()-1)+")");
			}
			allRules.put(i+"", matches);
			matches = new ArrayList<>();
		}

		// Count matches
		String pattern = "^";
		
		for(Map.Entry<String, List<String>> entry : allRules.entrySet()) {
			for(String s : entry.getValue()) {
				pattern += s;
			}
		}
		pattern += "$";
		
		final String patternFinal = pattern.replaceAll(" ", "");

        System.out.println(messages.stream().filter(a -> a.matches(patternFinal)).count());
        
        // PART 2
        rules.put("8", "42 | 42 8");
        rules.put("11", "42 31 | 42 11 31");

        findModifiedRules();
	}

	
	private static String findRules(String level, String rule, String comb) {
		// rule has numbers separated by spaces and | (with sub-rules)
		if(rule.contains("|")) {
			String[] parts = rule.split(" ");
			comb+="(";
			for(String p:parts) {
				if(p.equals("|")) {
					comb += " | ";
				} else {
					comb = findRules(level, p.trim(), comb);
				}
			}
			matches.add(comb);
			comb = ")";
		} else {
			// rule has a letter in double quotes (final value)
			if(rule.contains("\"")) {
				comb += "(" + rule.replaceAll("\"", "") + ")";
			} else {
				// rule has only numbers separated by spaces (no sub-rules)
				String[] parts = rule.split(" ");
				for(String p:parts) {
					comb = findRules(p, rules.get(p), comb);
				}
			}
		}
		
		return comb;
	}
	
	private static void findModifiedRules() {
		
	}
	
}