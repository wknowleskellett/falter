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
				System.out.println(
						"Usage: ./FalterGUI.java [x y [fill] | ?]\n" +
						"x y [fill]\n" +
						"\tx and y are the width and height of your\n" +
						"\tgameboard, while fill is an optional double\n" +
						"\tranging between 0 and 1 (inclusive) denoting\n" +
						"\tthe fraction of the board which is a dot."
						// TODO find a better name than dot
						);
				
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
			
			// This series of if, else if's controls the command input system.
			// first is the first word in the command, where line is the
			// scanner through the entire command.
			//
			// I'm scanning the string instead of System.in, so that hasNext
			// does not prompt the user for more input.
			
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
						step = 0;
						fail++;
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
						System.out.println(
								"Usage:\n" + 
								"step [steps]\n" +
								"steps is an optional argument, the amount of steps to run.\n" +
								"Defaults to 1.");
					} else if (second.equals("?")) {
						System.out.println(
								"Usage:\n" + 
								"? [command name]\n" +
								"command name is an optional argument.\n" +
								"Just try and tell me you don't understand this command. Try it.");
					} else {
						fail++;
					}
				}
			} else {
				fail++;
			}
			if (fail > 0) {
				System.out.println("Invalid command. Run '?' for help.");
			}
			System.out.print("\n");
			line.close();
		}
		in.close();
		
	}
}
