package Day13;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge13 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
			File myObj = new File("src/Day13/input13.txt");
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
		/*
		 * To calculate the BusId with the least waiting, it's enough to find the first multiple of the current busId greater than the starting timestamp
		 * The value considered for finding the multiples is incremented at every iteration
		 */
		int timestamp = Integer.parseInt(input.get(0));
		String[] busIds = input.get(1).split(",");
		int minTime = 0, i=timestamp+1, minutes=0;
		while(minTime == 0) {
			for(String s : busIds) {
				if(!s.equals("x")) {					
					if(i%Integer.parseInt(s) == 0) {
						minutes=i-timestamp;
						minTime = Integer.parseInt(s);  
					}					
				}
			}
			i++;
		}		
		System.out.println(minTime*minutes);
		
		// PART 2
		/*
		 * To calculate the earliest timestamp such that all of the listed bus IDs depart at offsets matching their positions in the list,
		 * it's necessary to consider 2 cases:
		 * 1 - The BusId is -1, meaning its value is "x", therefore it shouldn't be considered
		 * 2 - The BusId is not -1, therefore it's enough to find the first multiple of the current busId in relation to the sum of its time difference and its index
		 */
        final List<Long> ids = new ArrayList<>();
        for (String id : input.get(1).split(",")) {
            ids.add(id.equals("x") ? -1 : Long.parseLong(id));
        }
		long offset = -1, time = -1;
        int index = 0;
        while (true) {
        	final long id = ids.get(index);
            if (id == -1) {
                index++;
                continue;
            }

            if (offset == -1) {
                offset = id;
                time = id - index;
                index++;
                continue;
            }

            if ((time + index) % id == 0) {
                if (++index >= ids.size()) {
                    System.out.println(time);
                    return;
                }

                offset *= id;
                continue;
            }

            time += offset;
        }
	}
}