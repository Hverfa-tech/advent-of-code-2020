package Day17;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge17 {
	
	static int size;
	static int[][][] cube;
	static int[][][] cube_save;
	static int[][][][] hypercube;

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
			File myObj = new File("src/Day17/input17.txt");
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
		size = (2*7)+input.size();
		cube = new int[size][size][size];
		cube_save = new int[size][size][size];
		
		// Initialize tridimensional Array
		for (int x = 0; x < size; x++) {
		    for (int y = 0; y < size; y++) {
		    	for (int z = 0; z < size; z++) {
			        cube[x][y][z] = 0;
			        cube_save[x][y][z] = 0;
			    } 
		    }
		}
		
		int indexZ = (size-1)/2;
		int indexX = ((size-1)/2)-1;
		for(int i=0; i<input.size(); i++) {
			int c= -1;
			for(int j=0; j<input.size(); j++) {
				int indexY = indexZ + c;
				cube[indexZ][indexY][indexX] = input.get(i).charAt(j) == '#' ? 1 : 0;
				cube_save[indexZ][indexY][indexX] = input.get(i).charAt(j) == '#' ? 1 : 0;
				c++;
			}
			indexX++;
		}
		
		// Cycles
		for(int i=0; i<6; i++) {
			int[][][] cube2 = new int[size][size][size];
			for (int x = 0; x < size; x++) {
			    for (int y = 0; y < size; y++) {
			    	for (int z = 0; z < size; z++) {
				        cube2[x][y][z] = 0;
				    } 
			    }
			}
			
			for (int x = 0; x < size; x++) {
			    for (int y = 0; y < size; y++) {
			    	for (int z = 0; z < size; z++) {
				        cube2[x][y][z] = updatePoint(x, y, z);
				    } 
			    }
			}
			
			cube = cube2;
		}
		
		int result = 0;
		for (int x = 0; x < size; x++) {
		    for (int y = 0; y < size; y++) {
		    	for (int z = 0; z < size; z++) {
		    		result+=cube[x][y][z];
		    	}
		    }
		}
		
		System.out.println(result);
		
		// PART 2
		hypercube = new int[size][size][size][size];
		
		// Initialize quadrimensional Array
		for (int x = 0; x < size; x++) {
		    for (int y = 0; y < size; y++) {
		    	for (int z = 0; z < size; z++) {
		    		for (int w = 0; w < size; w++) {
		    			hypercube[x][y][z][w] = 0;
		    		}
			    } 
		    }
		}
		
		
		int indexW = (size-1)/2;		
		hypercube[indexW] = cube_save;
		
		// Cycles
		for(int i=0; i<6; i++) {
			int[][][][] hypercube2 = new int[size][size][size][size];
			for (int x = 0; x < size; x++) {
			    for (int y = 0; y < size; y++) {
			    	for (int z = 0; z < size; z++) {
			    		for (int w = 0; w < size; w++) {
			    			hypercube2[x][y][z][w] = 0;
			    		}
				    } 
			    }
			}
			
			for (int x = 0; x < size; x++) {
			    for (int y = 0; y < size; y++) {
			    	for (int z = 0; z < size; z++) {
			    		for (int w = 0; w < size; w++) {
			    			hypercube2[x][y][z][w] = updatePoint(x, y, z, w);
			    		}
				    } 
			    }
			}
			
			hypercube = hypercube2;
		}
		
		result = 0;
		for (int x = 0; x < size; x++) {
		    for (int y = 0; y < size; y++) {
		    	for (int z = 0; z < size; z++) {
		    		for (int w = 0; w < size; w++) {
		    			result+=hypercube[x][y][z][w];
		    		}
		    	}
		    }
		}
		
		System.out.println(result);
		
	}
	
	private static int updatePoint(int x, int y, int z) {
		
		// Count all the occupied adjacent neighbors of the current seat
		int currentpoint = cube[x][y][z];
		int countActive = -currentpoint;
		for(int m=-1; m<2; m++) {
			for(int l=-1; l<2; l++) {
				for(int n=-1; n<2; n++) {
					if((x+m) >= 0 && (y+l) >= 0 && (z+n) >= 0 
						&& (x+m) < size && (y+l) < size && (z+n) < size) {
						countActive+=cube[x+m][y+l][z+n];
					}
				}
			}
		}
			
		/*
		 * If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active. Otherwise, the cube becomes inactive.
		 * If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active. Otherwise, the cube remains inactive.
		 */
		if(currentpoint == 1) {
			return (countActive == 2 || countActive == 3) ? 1 : 0;			
		} else {
			return (countActive == 3) ? 1 : 0;	
		}
	}
	
	private static int updatePoint(int x, int y, int z, int w) {
		
		// Count all the occupied adjacent neighbors of the current seat
		int currentpoint = hypercube[x][y][z][w];
		int countActive = -currentpoint;
		for(int m=-1; m<2; m++) {
			for(int l=-1; l<2; l++) {
				for(int n=-1; n<2; n++) {
					for(int o=-1; o<2; o++) {
						if((x+m) >= 0 && (y+l) >= 0 && (z+n) >= 0 && (w+o) >= 0
							&& (x+m) < size && (y+l) < size && (z+n) < size && (w+o) < size) {
							countActive+=hypercube[x+m][y+l][z+n][w+o];
						}
					}
				}
			}
		}
			
		/*
		 * If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active. Otherwise, the cube becomes inactive.
		 * If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active. Otherwise, the cube remains inactive.
		 */
		if(currentpoint == 1) {
			return (countActive == 2 || countActive == 3) ? 1 : 0;			
		} else {
			return (countActive == 3) ? 1 : 0;	
		}
	}
}
