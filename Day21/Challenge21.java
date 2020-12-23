package Day21;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge21 {

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
		
	}
}