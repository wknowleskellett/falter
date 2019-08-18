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
		
		// process command line arguments
		
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
				System.out.println("Usage: ./FalterGUI.java [<x> <y> [fill] | ?]");
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
			System.out.println("Usage: ./FalterGUI.java [<x> <y> [fill] | ?]");
			System.exit(1);
		}
		
		// Based on arguments, initialize the GameBoard
		
		if (status == 0) {
			g = new GameBoard();
		} else if (status == 1) {
			g = new GameBoard(width, height);
		} else {
			g = new GameBoard(width, height, fill);
		}
		
		Scanner in = new Scanner(System.in);
		
		// main loop
		
		while(true) {
			
			if (!in.hasNextLine()) {
				// end the program. Nothing to do.
				break;
			}
			
			String command = in.nextLine();
			
			// scan over each word in the command entered
			Scanner line = new Scanner(command);
			
			// If the command was all whitespace (or was empty)
			if (!line.hasNext()) {
				// Show the gameboard, cause I'm lazy
				System.out.println(g);
				line.close();
				continue;
			}
			
			int fail = 0;
			
			// "first" is the first word in the command, where "line" is the
			// scanner through the entire command.
			String first = line.next().toLowerCase();
			
			// This series of "if, else if" controls the command input system.
			
			// I'm scanning the String command instead of System.in, so that querying hasNext()
			// does not prompt the user for more input.
			
			if (first.equals("exit")) {
				// give the user a way to exit and still close the resources
				line.close();
				break;
			} else if (first.equals("show")) {
				// show the gameboard
				System.out.println(g);
			} else if (first.equals("add")) {
				// add a mobile point to the GameBoard
				// x and y should both follow the add command on the same line
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
				// run either 1 step
				int step = 1;
				
				// or however many the user requests
				if (line.hasNext()) {
					if (line.hasNextInt()) {
						step = line.nextInt();
					} else {
						step = 0;
						fail++;
					}
				}
				
				// run that many steps
				for (int i=0; i < step; i++) {
					g.step();
				}
			} else if (first.equals("?")) {
				// help menu
				if (!line.hasNext()) {
					// generic help
					
					System.out.println(
							"The commands available are:\n" + 
							"\tshow\n" + 
							"\tadd <x> <y>\n" + 
							"\tstep [steps]\n" + 
							"\t? [command-name]");
				} else {
					// help for specific commands
					
					String second = line.next().toLowerCase();
					if (second.equals("show")) {
						System.out.println(
								"Usage:\n" + 
								"add\n" +
								"Displays the game board.");
					} else if (second.equals("add")) {
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
								"Just try to tell me you don't understand this command. Try it.");
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
