import java.util.ArrayList;

public class GameBoard {
	
	public static short ERROR = -1;
	
	public static short UP = 0;
	public static short RIGHT = 1;
	public static short DOWN = 2;
	public static short LEFT = 3;
	
	public static short EMPTY = 0;
	public static short FULL = 1;
	public static short MOVED = 2;
	public static short WALL = 3;
	
	private int width;
	private int height;
	private short[][] board;
	
	// This is a list of the points that move each step
	ArrayList<int[]> movements;
	
	// Constructors
	public GameBoard() {
		this(50, 20);
	}
	
	public GameBoard(int w, int h) {
		this(w, h, 0.5);
	}

	public GameBoard(int w, int h, double fill) {
		this.height = h;
		this.width = w;
		
		board = new short[height][width];
		for (int i=0; i < height; i++) {
			for (int j=0; j < width; j++) {
				if (Math.random() < fill) {
					board[i][j] = FULL;
				} else {
					board[i][j] = EMPTY;
				}
			}
		}
		
		movements = new ArrayList<int[]>();
	}
	
	// addPoint is run to set an initial mobile point,
	// given its coordinates from the top left corner
	// that point must already be "FULL"
	public int addPoint(int x, int y) {
		if (board[y][x] == FULL) {
			board[y][x] = MOVED;
			movements.add(
					new int[] {
							x,
							y
					}
			);
			return 0; //success
		}
		return 1;
	}
	
	public void step() {
		// TODO write method
		sonar(0, 0, UP);
		System.out.println("I did a step.");
	}
 
	// sonar() is used by the step method.
	// A MOVED spot can look in one of four directions
	// and see what is there. Is it the edge of the gameboard? (WALL)
	// Otherwise, it is either, EMPTY, FULL, or MOVED.
	// If the coordinate or direction given is invalid, returns ERROR.
	
	private int sonar(int x, int y, short direction) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			// ERROR if the coordinate is invalid
			return ERROR;
		}
		if (direction == UP) {
			if (y == 0) {
				return WALL;
			} else {
				return board[y-1][x];
			}
		} else if (direction == RIGHT) {
			if (x == width - 1) {
				return WALL;
			} else {
				return board[y][x+1];
			}
		} else if (direction == DOWN) {
			if (y == height - 1) {
				return WALL;
			} else {
				return board[y+1][x];
			}
		} else if (direction == LEFT) {
			if (x == 0) {
				return WALL;
			} else {
				return board[y][x-1];
			}
		}
		// ERROR is returned if direction was invalid
		return ERROR;
	}
	
	public String toString() {
		StringBuilder retVal = new StringBuilder();
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (board[i][j] == EMPTY) {
					retVal.append('.');
				} else if (board[i][j] == FULL) {
					retVal.append('O');
				} else if (board[i][j] == MOVED) {
					retVal.append('+');
				}
				if (j < width) {
					retVal.append(' ');
				}
			}
			
			if (i < height) {
				retVal.append("\n");
			}
		}
		
		return retVal.toString();
		
	}
}
