package Day22;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Challenge22 {
	
	static List<String> input = new ArrayList<>();
	static List<Integer> player1;
	static List<Integer> player2;

	public static void main(String[] args) {
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
		initializeHands();
		
		List<Integer> winner = new ArrayList<>();
		
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
		BigInteger score = calculateScore(winner);
		System.out.println(score);
		
		
		// PART 2
		
		// Initialize players hands
		initializeHands();
		
		winner = new ArrayList<>();
		
		// Play recursive rounds until one of the 2 players hand is empty
		int winnerRecursive = playRecursiveGame(player1, player2);
		winner = winnerRecursive == 1 ? player1 : player2; 
		
		// Calculate score
		score = calculateScore(winner);
		System.out.println(score);
	}
	
	private static void initializeHands() {
		player1 = new ArrayList<>();
		player2 = new ArrayList<>();
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
	}
	
	private static BigInteger calculateScore(List<Integer> winner) {
		BigInteger score = new BigInteger("0");
		BigInteger j = new BigInteger("1");
		for(int i=winner.size()-1; i>=0; i--) {
			score = score.add((new BigInteger(winner.get(i)+"").multiply(j)));
			j = j.add(new BigInteger("1"));
		}
		return score;
	}
	
	private static int playRecursiveGame(List<Integer> player1, List<Integer> player2) {
		int winner = 0;
		HashSet<String> history = new HashSet<String>();
		while(player1.size() > 0 && player2.size() > 0) {
			int p1 = player1.remove(0);
			int p2 = player2.remove(0);
			
			// If the state was already played, player1 is the winner
			if (!history.add(player1.toString()+player2.toString())) {
				return 1;
			}
			
			/*
			 * If both players have at least as many cards remaining in their deck as the value of the card they just drew,
			 * the winner of the round is determined by playing a new game of Recursive Combat
			 * Otherwise the usual rules apply
			 */
			if(player1.size() >= p1 && player2.size() >= p2) {
				List<Integer> sub1 = new ArrayList<>();
				for(int i=0; i<p1; i++) {
					sub1.add(player1.get(i));
				}
				List<Integer> sub2 = new ArrayList<>();
				for(int i=0; i<p2; i++) {
					sub2.add(player2.get(i));
				}
				winner = playRecursiveGame(sub1, sub2);
			} else {
				if(p1>p2) {
					winner = 1;
				} else {
					winner = 2;
				}
			}
			
			if(winner == 1) {
				player1.add(p1);
				player1.add(p2);
			} else {
				player2.add(p2);
				player2.add(p1);
			}
		}
		return winner;
	}
}
