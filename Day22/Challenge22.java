package Day22;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge22 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
			File myObj = new File("src/Day22/input22.txt");
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
		
		// Initialize players hands
		List<Integer> player1 = new ArrayList<>();
		List<Integer> player2 = new ArrayList<>();
		List<Integer> winner = new ArrayList<>();
		boolean player1Done = false;
		
		for(String s:input) {
			if(s.isEmpty()) {
				continue;
			}
			if(s.contains("Player 1")) {
				continue;
			}
			if(s.contains("Player 2")) {
				player1Done = true;
				continue;
			}
			if(!player1Done) {
				player1.add(Integer.parseInt(s));
			}
			if(player1Done) {
				player2.add(Integer.parseInt(s));
			}
		}
		
		// Play rounds until one of the 2 players hand is empty
		while(player1.size() > 0 && player2.size() > 0) {
			int p1 = player1.get(0);
			int p2 = player2.get(0);
			
			if(p1>p2) {
				player1.add(p1);
				player1.add(p2);
			} else {
				player2.add(p2);
				player2.add(p1);
			}
			player1.remove(0);
			player2.remove(0);
			if(player1.size() == 0) {
				winner = player2;
			} else {
				winner = player1;
			}
		}
		
		// Calculate score
		BigInteger score = new BigInteger("0");
		int j=1;
		for(int i=winner.size()-1; i>=0; i--) {
			score = score.add(new BigInteger((winner.get(i)*j)+""));
			j++;
		}
		System.out.println(score);
	}
}