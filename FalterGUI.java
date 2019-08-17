//	William Knowles-Kellett
//	8/14/2019



import java.util.Scanner;

public class FalterGUI {
	
	private static GameBoard g; 
	
	public static void main(String[] args) {
		int status = 0;
		int width = -1;
		int height = -1;
		double fill = -1;
		
		if (args.length == 1) {
			if (args[0].equals("?")) {
				// TODO help screen
				System.exit(0);
			} else {
				System.out.println("Usage: ./FalterGUI.java [x y [fill] | ?]");
				System.exit(1);
			}
		} else if (args.length == 2 || args.length == 3) {
			status = 1;
			width = Integer.parseInt(args[0]);
			height = Integer.parseInt(args[1]);
		} else if (args.length == 3) {
			status = 2;
			fill = Double.parseDouble(args[2]);
		} else if (args.length > 3){
			System.out.println("Usage: ./FalterGUI.java [x y [fill] | ?]");
			System.exit(1);
		}
		
		if (status == 0) {
			g = new GameBoard();
		} else if (status == 1) {
			g = new GameBoard(width, height);
		} else {
			g = new GameBoard(width, height, fill);
		}
		
		Scanner in = new Scanner(System.in);
		
		while(true) {
			System.out.println(g);
			
			if (!in.hasNextLine()) {
				break;
			}
			String order = in.nextLine();
			
			Scanner line = new Scanner(order);
			
			if (!line.hasNext()) {
				line.close();
				continue;
			}
			int fail = 0;
			String first = line.next().toLowerCase();
			if (first.equals("exit")) {
				line.close();
				break;
			} else if (first.equals("add")) {
				int x, y;
				if (line.hasNextInt()) {
					x = line.nextInt();
					if (line.hasNextInt()) {
						y = line.nextInt();
						if (g.addPoint(x, y) == 0) {
							System.out.println("Successfully added (" + x + ", " + y + ")");
						} else {
							System.out.println("Failed to add (" + x + ", " + y + ")");
						}
					} else {
						fail++;
					}
				} else {
					fail++;
				}
			} else if (first.equals("step")) {
				int step = 1;
				if (line.hasNext()) {
					if (line.hasNextInt()) {
						step = line.nextInt();
					} else {
						// TODO error message explaining that step has an optional argument
						// that accepts an integer amount of steps.
						System.out.println("");
					}
				}
				for (int i=0; i < step; i++) {
					g.step();
				}
			} else if (first.equals("?")) {
				if (!line.hasNext()) {
					System.out.println(
							"The commands available are:\n" + 
							"\tadd <x> <y>\n" + 
							"\tstep [steps]\n" + 
							"\t? [command-name]");
				} else {
					String second = line.next().toLowerCase();
					if (second.equals("add")) {
						System.out.println(
								"Usage:\n" + 
								"add <x> <y>\n" +
								"<x> and <y> are the coordinates from the top left\n" +
								"corner of the board at which to add a point.");
					} else if (second.equals("step")) {
						// TODO update for step
						System.out.println(
								"Usage:\n" + 
								"add <x> <y>\n" +
								"<x> and <y> are the coordinates from the top left\n" +
								"corner of the board at which to add a point.");
					} else if (second.equals("?")) {
						// TODO update for help
						System.out.println(
								"Usage:\n" + 
								"add <x> <y>\n" +
								"<x> and <y> are the coordinates from the top left\n" +
								"corner of the board at which to add a point.");
					}
				}
			} else {
				fail++;
			}
			if (fail > 0) {
				System.out.println("Invalid command.");
			}
			System.out.print("\n");
			line.close();
		}
		in.close();
		
	}
}
