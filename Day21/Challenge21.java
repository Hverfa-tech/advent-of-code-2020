package Day21;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Challenge21 {
	
	static List<String> allergens = new ArrayList<>();

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
			File myObj = new File("src/Day21/input21.txt");
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
		
		/* For Part 2*/
		Map<String,List<String>> canonicalDangerousIngredients = new HashMap<>();
		
		// Find all allergens
		for(String s:input) {
			String[] line_allergens = s.split("contains ")[1].split(",");
			for(String a:line_allergens) {
				a=a.replaceAll("[) ]", "");
				if(!allergens.contains(a)) {
					allergens.add(a);
				}
			}
		}
		
		for(String a: allergens) {
			
			// Find the foods that contain the allergen
			List<String> foods = new ArrayList<>();
			for(String s:input) {
				if(s.indexOf(" " + a) != -1) {
					foods.add(s);					
				}
			}
			
			
			/*
			 * Compare the ingredients contained in the foods list retrieved and find the common elements
			 */
			List<String> ingredients = new ArrayList<>(Arrays.asList(foods.get(0).split("[(]")[0].split(" ")));
			
			if(foods.size() != 1) {
				for(int i=1; i<foods.size(); i++) {
					List<String> s_ingredients = new ArrayList<>(Arrays.asList(foods.get(i).split("[(]")[0].split(" ")));
					ingredients.retainAll(s_ingredients);
				}
			}
			
			canonicalDangerousIngredients.put(a, ingredients);
			
		}
		
		/*
		 * Order alphabetically the map allergen - list of ingredients
		 */
		
		List<Map.Entry<String, List<String>>> canonicalDangerousIngredientsOrdered =
			    canonicalDangerousIngredients.entrySet()
			       .stream()
			       .sorted(Map.Entry.comparingByKey())
			       .collect(Collectors.toList());
		
		/*
		 * Starting from the lists of ingredients that contain only one element, remove this element from the other lists
		 * Continue until every list contains only one element
		 */
		int countIngredients = 0;
		while(countIngredients != canonicalDangerousIngredientsOrdered.size()) {
			countIngredients = 0;
			for(Map.Entry<String,List<String>> entry : canonicalDangerousIngredientsOrdered) {
				countIngredients += entry.getValue().size();
			}

			for(Map.Entry<String,List<String>> entry : canonicalDangerousIngredientsOrdered) {
				List<String> ingredients = entry.getValue();
				if(ingredients.size() == 1) {
					for(Map.Entry<String,List<String>> entry2 : canonicalDangerousIngredientsOrdered) {
						if(!entry.equals(entry2)) {
							entry2.getValue().remove(ingredients.get(0));
						}
					}
				}
			}
		}
		
		// Count ingredients
		int count=0;
		for(Map.Entry<String,List<String>> entry : canonicalDangerousIngredientsOrdered) {
			List<String> ingredients = entry.getValue();
			for(String ing_all : ingredients) {
				for(int i=0; i<input.size(); i++) {
					if(input.get(i).contains(ing_all)) {
						String replaced = input.get(i).replace(ing_all + " ", "");
						input.set(i, replaced);
					}
				}
			}
		}
		for(String s:input) {
			List<String> s_ingredients = new ArrayList<>(Arrays.asList(s.split("[(]")[0].split(" ")));
			for(String ing: s_ingredients) {
				if(!ing.isEmpty()) {
					count++;
				}
			}
		}
		
		System.out.println(count);
		
		// PART  2
		
		/*
		 * Produce the canonical dangerous ingredient list
		 */		
		String result = "";
		
		for(Map.Entry<String,List<String>> entry : canonicalDangerousIngredientsOrdered) {
			for(String s : entry.getValue()) {
				result += s+",";
			}
		}
		result = result.substring(0, result.length()-1);
		
		System.out.println(result);
	}
}
