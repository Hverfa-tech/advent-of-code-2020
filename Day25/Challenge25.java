package Day25;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge25 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
			File myObj = new File("src/Day25/input25.txt");
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
		long cardPublicKey = Long.parseLong(input.get(0));
		long doorPublicKey = Long.parseLong(input.get(1));
		long cardLoopSize = findLoopNumber(cardPublicKey, 7, 1000000000);
		long doorLoopSize = findLoopNumber(doorPublicKey, 7, 1000000000);
		System.out.println(cardLoopSize);
		System.out.println(doorLoopSize);
		System.out.println(findLoopNumber(-1, doorPublicKey, cardLoopSize));
		System.out.println(findLoopNumber(-1, cardPublicKey, doorLoopSize));
	}
	
	private static long findLoopNumber(long publicKey, long subjectNumber, long loopSize) {
		long value = 1, result = 0;
		for(int i=1; i<=loopSize; i++) {
			value *= subjectNumber;
			value %= 20201227;
			if(publicKey != -1 && value == publicKey) {
				result = i;
				break;
			} else {
				result = value;
			}
		}
		
		return result;
	}
}