package Day20;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Challenge20 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
			File myObj = new File("src/Day20/input20.txt");
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
		Map<String, String[][]> tiles = new HashMap<>();
		String tileName = "";
		String[][] tileMatrix = null;
		int i=0;
		for(String s : input) {
			if(s.startsWith("Tile")) {
				tileName = s;
			} else if(s.isEmpty()) {
				int tmLength = tileMatrix.length;
				tiles.put(tileName, tileMatrix);
				tileMatrix = new String[tmLength][tmLength];
				i=0;
			} else {
				if(tileMatrix==null) {
					tileMatrix = new String[s.length()][s.length()];
				}
				for(int j=0; j<s.length(); j++) {
					tileMatrix[i][j] = s.charAt(j)+"";
				}
				i++;
			}
		}
		tiles.put(tileName, tileMatrix);
		
		BigInteger mult = new BigInteger("1");
		
		for(Map.Entry<String, String[][]> entry : tiles.entrySet()) {
			String tileName1 = entry.getKey().split(" ")[1].split(":")[0];
			String[][] tileMatrix1 = entry.getValue();
			String s1_1 = "";
			String s2_1 = "";
			String s3_1 = "";
			String s4_1 = "";
			
			for(int j=0; j<tileMatrix1.length; j++) {
				s1_1 += tileMatrix1[0][j];
			}
			for(int j=0; j<tileMatrix1.length; j++) {
				s2_1 += tileMatrix1[j][tileMatrix1.length-1];
			}
			for(int j=0; j<tileMatrix1.length; j++) {
				s3_1 += tileMatrix1[tileMatrix1.length-1][j];
			}
			for(int j=0; j<tileMatrix1.length; j++) {
				s4_1 += tileMatrix1[j][0];
			}
			int countS1 = 0, countS2 = 0, countS3 = 0, countS4 = 0;
			for(Map.Entry<String, String[][]> entry2 : tiles.entrySet()) {
				String tileName2 = entry2.getKey().split(" ")[1].split(":")[0];
				if(!tileName1.equals(tileName2)) {
					String[][] tileMatrix2 = entry2.getValue();
					String s1_2 = "";
					String s1_2_reversed = "";
					String s2_2 = "";
					String s2_2_reversed = "";
					String s3_2 = "";
					String s3_2_reversed = "";
					String s4_2 = "";
					String s4_2_reversed = "";
					
					for(int j=0; j<tileMatrix2.length; j++) {
						s1_2 += tileMatrix2[0][j];
					}
					StringBuilder sb = new StringBuilder(s1_2);
					s1_2_reversed = sb.reverse().toString();
					for(int j=0; j<tileMatrix2.length; j++) {
						s2_2 += tileMatrix2[j][tileMatrix2.length-1];
					}
					sb = new StringBuilder(s2_2);
					s2_2_reversed = sb.reverse().toString();
					for(int j=0; j<tileMatrix2.length; j++) {
						s3_2 += tileMatrix2[tileMatrix2.length-1][j];
					}
					sb = new StringBuilder(s3_2);
					s3_2_reversed = sb.reverse().toString();
					for(int j=0; j<tileMatrix2.length; j++) {
						s4_2 += tileMatrix2[j][0];
					}
					sb = new StringBuilder(s4_2);
					s4_2_reversed = sb.reverse().toString();
					
					if(s1_1.equals(s1_2) || s1_1.equals(s2_2) || s1_1.equals(s3_2) || s1_1.equals(s4_2)
						|| s1_1.equals(s1_2_reversed) || s1_1.equals(s2_2_reversed) || s1_1.equals(s3_2_reversed) || s1_1.equals(s4_2_reversed)) {
						countS1++;
					}
					if(s2_1.equals(s1_2) || s2_1.equals(s2_2) || s2_1.equals(s3_2) || s2_1.equals(s4_2)
						|| s2_1.equals(s1_2_reversed) || s2_1.equals(s2_2_reversed) || s2_1.equals(s3_2_reversed) || s2_1.equals(s4_2_reversed)) {
						countS2++;
					}
					if(s3_1.equals(s1_2) || s3_1.equals(s2_2) || s3_1.equals(s3_2) || s3_1.equals(s4_2)
						|| s3_1.equals(s1_2_reversed) || s3_1.equals(s2_2_reversed) || s3_1.equals(s3_2_reversed) || s3_1.equals(s4_2_reversed)) {
						countS3++;
					}
					if(s4_1.equals(s1_2) || s4_1.equals(s2_2) || s4_1.equals(s3_2) || s4_1.equals(s4_2)
						|| s4_1.equals(s1_2_reversed) || s4_1.equals(s2_2_reversed) || s4_1.equals(s3_2_reversed) || s4_1.equals(s4_2_reversed)) {
						countS4++;
					}
				}
			}
			
			if((countS1 == 0 && countS4 == 0)
				|| (countS1 == 0 && countS2 == 0)
				|| (countS3 == 0 && countS4 == 0)
				|| (countS2 == 0 && countS3 == 0)) {
				mult = mult.multiply(new BigInteger(tileName1));
			}
		}
		
		System.out.println(mult);
	}
}