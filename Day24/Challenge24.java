package Day24;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge24 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
			File myObj = new File("src/Day24/input24.txt");
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
		boolean[][] floor = new boolean[500][500];
		int centerX = floor.length/2, centerY = floor[0].length/2;
		
		for(String s : input) {
			String[] tile = getTile(s).split(" ");
			int x = Integer.parseInt(tile[0]);
			int y = Integer.parseInt(tile[1]);
			
			/*
			 * Fill floor with values after evaluating instructions
			 * The tile is flipped (!value) after evaluation
			 */
			floor[centerX+x][centerY+y] = !floor[centerX+x][centerY+y];
		}
		
		int count = countBlackTiles(floor);
		
		System.out.println(count);
		
		// PART 2
		for(int i=0; i<100; i++) {
			boolean[][] copy = new boolean[floor.length][floor[0].length];
			for (int r = 0; r < copy.length; r++) {
				for (int c = 0; c < copy[0].length; c++) {
					copy[r][c] = floor[r][c];
				}
			}
			/*
			 * Flip the tile (!value) following these rules:
			 * 1) Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white.
			 * 2) Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.
			 */
			for (int r=0; r<floor.length; r++) {
				for (int c=0; c<floor[0].length; c++) {
					boolean tileValue = floor[r][c];
					int blackNeighbours = countBlackNeighbours(r, c, floor);
					
					if(tileValue && (blackNeighbours == 0 || blackNeighbours > 2)) {
						copy[r][c] = false;
					}
					if (!tileValue && blackNeighbours == 2) {
						copy[r][c] = true;
					}
				}
			}
			
			floor = copy;
		}
		
		count = countBlackTiles(floor);

		System.out.println(count);
	}
	
	private static String getTile(String instructions) {
		int x=0, y=0, i=0;
		
		while(i<instructions.length()) {
			String in = "";
			
			if((i-1)>0 &&
				((instructions.charAt(i) == 'e' || instructions.charAt(i) == 'w') && 
				 (instructions.charAt(i-1) == 's' || instructions.charAt(i-1) == 'n'))) {
				in = instructions.charAt(i-1) + "" + instructions.charAt(i);
				i+=2;
			} else if((i+1)<instructions.length() &&
					(instructions.charAt(i) == 's' || instructions.charAt(i) == 'n')) {
				in = instructions.charAt(i) + "" + instructions.charAt(i+1);
				i+=2;
			}else {
				in = instructions.charAt(i) + "";
				i++;
			}
			
			switch(in) {
				case "e":
					x+=2;
					break;
				case "se":
					x+=1;
					y+=1;
					break;
				case "sw":
					x-=1;
					y+=1;
					break;
				case "w":
					x-=2;
					break;
				case "nw":
					x-=1;
					y-=1;
					break;
				case "ne":
					x+=1;
					y-=1;
					break;
			}
		}
		
		return x + " " + y;
	}
	
	
	private static int countBlackNeighbours(int x, int y, boolean[][] floor) {
		int count=0;
		if(x+2 < floor.length) {
			if(floor[x+2][y]) {
				count++;
			}
		}
		if(x+1 < floor.length && y+1 < floor[0].length) {
			if(floor[x+1][y+1]) {
				count++;
			}
		}
		if(x-1 >= 0 && y+1 < floor[0].length) {
			if(floor[x-1][y+1]) {
				count++;
			}
		}
		if(x-2 >= 0) {
			if(floor[x-2][y]) {
				count++;
			}
		}
		if(x-1 >= 0 && y-1 >= 0) {
			if(floor[x-1][y-1]) {
				count++;
			}
		}
		if(x+1 < floor.length && y-1 >= 0) {
			if(floor[x+1][y-1]) {
				count++;
			}
		}
		return count;
	}
	
	private static int countBlackTiles(boolean[][] floor) {
		int count=0;
		
		// Count Black tiles ('true' values in floor)
		for (int r = 0; r < floor.length; r++) {
			for (int c = 0; c < floor[0].length; c++) {
				if (floor[r][c])
					count++;
			}
		}
		
		return count;
	}
}