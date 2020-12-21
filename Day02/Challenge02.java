package Day02;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge02 {

	public static void main(String[] args) {
		List<String> passwords = new ArrayList<>();
		try {
	      File myObj = new File("src/Day02/input02.txt");
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	    	  passwords.add(myReader.nextLine());
	      }
	      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		
		int count1 = 0;
		int count2 = 0;
		
		for(int i=0; i<passwords.size(); i++) {
			
			String[] pwdSplit = passwords.get(i).split(" ");
			int startRange = Integer.parseInt(pwdSplit[0].split("-")[0]);
			int endRange = Integer.parseInt(pwdSplit[0].split("-")[1]);
			char letterToVerify = pwdSplit[1].split(":")[0].charAt(0);
			String pwd = pwdSplit[2];
			
			// PART 1
			int countOccurences = 0;
			// Count how many times the letter to verify occurs in the password
			for (int j = 0; j < pwd.length(); j++) {
			    if (pwd.charAt(j) == letterToVerify) {
			        countOccurences++;
			    }
			}
			
			// If the occurrences are in the given range, consider the password as valid
			if(countOccurences >= startRange && countOccurences <= endRange) {
				count1++;
			}
			
			// PART 2
			// If one of the 2 characters ate the given positions are equal to the letter to verify, consider the password as valid
			if(((pwd.charAt(startRange-1) == letterToVerify && pwd.charAt(endRange-1) != letterToVerify)
				|| (pwd.charAt(startRange-1) != letterToVerify && pwd.charAt(endRange-1) == letterToVerify))) {
				count2++;
			}
		}
		System.out.println(count1);
		System.out.println(count2);
	}

}
