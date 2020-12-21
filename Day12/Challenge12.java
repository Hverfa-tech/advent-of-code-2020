package Day12;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Challenge12 {

	public static void main(String[] args) {
		List<String> input = new ArrayList<>();
		try {
			File myObj = new File("src/Day12/input12.txt");
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
		char currentDirection = 'E';
		int x=0, y=0;
		for(int i=0; i<input.size(); i++) {
			char direction = input.get(i).charAt(0);
			int units = Integer.parseInt(input.get(i).substring(1));
			switch(direction) {
				case 'N':
					y+=units;
					break;
				case 'S':
					y-=units;
					break;
				case 'E':
					x+=units;
					break;
				case 'W':
					x-=units;
					break;
				case 'L':
					currentDirection = calculateLeftDirection(currentDirection, units);
					break;
				case 'R':
					currentDirection = calculateRightDirection(currentDirection, units);
					break;
				case 'F':
					if(currentDirection == 'E') {
						x+=units;
					} else if(currentDirection == 'W') {
						x-=units;
					} if(currentDirection == 'N') {
						y+=units;
					} if(currentDirection == 'S') {
						y-=units;
					} 
					break;
			}
		}
		
		System.out.println(Math.abs(x)+Math.abs(y));

		// PART 2
		char currentDirectionX = 'E';
		char currentDirectionY = 'N';
		int sx=0, sy=0, wx=10, wy=1;
		for(int i=0; i<input.size(); i++) {
			char direction = input.get(i).charAt(0);
			int units = Integer.parseInt(input.get(i).substring(1));
			switch(direction) {
			case 'N':
				wy+=units;
				break;
			case 'S':
				wy-=units;
				break;
			case 'E':
				wx+=units;
				break;
			case 'W':
				wx-=units;
				break;
			case 'R':
			case 'L':
				int x2=(currentDirectionX == 'E' ? 1 : -1);
				int y2=(currentDirectionY == 'N' ? 1 : -1);
				String newDir = calculateDirection(direction, x2+","+y2, units);
				int newDirX = Integer.parseInt(newDir.split(",")[0]);
				int newDirY = Integer.parseInt(newDir.split(",")[1]);
				int tmpX = wx;
				wx = newDirX * (units != 180 ? wy : wx);
				wy = newDirY * (units != 180 ? tmpX : wy);
				break;
			case 'F':
				sx+=(wx*units);
				sy+=(wy*units);
				break;
			}
		}

		System.out.println(Math.abs(sx)+Math.abs(sy));
	}
	
	
	private static String calculateDirection(char way, String currDir, int degrees) {
		int x=Integer.parseInt(currDir.split(",")[0]);
		int y=Integer.parseInt(currDir.split(",")[1]);
		if(degrees == 180) {
			switch(currDir) {
				// NE
				case "1,1":
					x = -1;
					y = -1;
					break;
				// NW
				case "1,-1":
					x = -1;
					y = 1;
					break;
				// SE
				case "-1,1":
					x = 1;
					y = -1;
					break;
				// NW
				case "-1,-1":
					x = 1;
					y = 1;
					break;
			}			
		} else {
			if(way == 'R') {
				switch(currDir) {
					// NE
					case "1,1":
						x = degrees==90 ? 1 : -1;
						y = degrees==90 ? -1 : 1;
						break;
					// NW
					case "1,-1":
						x = degrees==90 ? 1 : -1;
						y = degrees==90 ? 1 : -1;
						break;
					// SE
					case "-1,1":
						x = degrees==90 ? -1 : 1;
						y = degrees==90 ? -1 : 1;
						break;
					// NW
					case "-1,-1":
						x = degrees==90 ? 1 : -1;
						y = degrees==90 ? -1 : 1;
						break;
				}
			} else {
				switch(currDir) {
					// NE
					case "1,1":
						x = degrees==90 ? -1 : 1;
						y = degrees==90 ? 1 : -1;
						break;
					// NW
					case "1,-1":
						x = degrees==90 ? -1 : 1;
						y = degrees==90 ? -1 : 1;
						break;
					// SE
					case "-1,1":
						x = degrees==90 ? 1 : -1;
						y = degrees==90 ? 1 : -1;
						break;
					// NW
					case "-1,-1":
						x = degrees==90 ? -1 : 1;
						y = degrees==90 ? 1 : -1;
						break;
				}
			}
		}

		return x+","+y;		
	}
	
	private static char calculateRightDirection(char currDir, int degrees) {
		char result = ' ';
		if(currDir == 'E') {
			if(degrees == 90) {
				result = 'S';
			} else if(degrees == 180) {
				result = 'W';
			} else if(degrees == 270) {
				result = 'N';
			}
		} else if(currDir == 'S') {
			if(degrees == 90) {
				result = 'W';
			} else if(degrees == 180) {
				result = 'N';
			} else if(degrees == 270) {
				result = 'E';
			}
		} else if(currDir == 'W') {
			if(degrees == 90) {
				result = 'N';
			} else if(degrees == 180) {
				result = 'E';
			} else if(degrees == 270) {
				result = 'S';
			}
		} else if(currDir == 'N') {
			if(degrees == 90) {
				result = 'E';
			} else if(degrees == 180) {
				result = 'S';
			} else if(degrees == 270) {
				result = 'W';
			}
		}

		return result;		
	}

	private static char calculateLeftDirection(char currDir, int degrees) {
		char result = ' ';
		if(currDir == 'E') {
			if(degrees == 90) {
				result = 'N'; 
			} else if(degrees == 180) {
				result = 'W'; 
			} else if(degrees == 270) {
				result = 'S'; 
			}
		} else if(currDir == 'S') {
			if(degrees == 90) {
				result = 'E'; 
			} else if(degrees == 180) {
				result = 'N'; 
			} else if(degrees == 270) {
				result = 'W'; 
			}
		} else if(currDir == 'W') {
			if(degrees == 90) {
				result = 'S'; 
			} else if(degrees == 180) {
				result = 'E'; 
			} else if(degrees == 270) {
				result = 'N'; 
			}
		} else if(currDir == 'N') {
			if(degrees == 90) {
				result = 'W'; 
			} else if(degrees == 180) {
				result = 'S'; 
			} else if(degrees == 270) {
				result = 'E'; 
			}
		}

		return result;
	}
}