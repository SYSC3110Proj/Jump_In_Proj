package gamePieces;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PointPlayBoard {
	
	private PointSquare board[][];	// Format: board[row][col]
	private ArrayList<Rabbit> rabbits;
	private ArrayList<NewFox> foxes;
	
	private static final List<GridPoint> VALID_FOX_LOCATIONS = Collections.unmodifiableList(
			new ArrayList<GridPoint>() {{
				add(new GridPoint(0,1));
				add(new GridPoint(0,3));
				add(new GridPoint(1,0));
				add(new GridPoint(1,1));
				add(new GridPoint(1,2));
				add(new GridPoint(1,3));
				add(new GridPoint(1,4));
				add(new GridPoint(2,1));
				add(new GridPoint(2,3));
				add(new GridPoint(3,0));
				add(new GridPoint(3,1));
				add(new GridPoint(3,2));
				add(new GridPoint(3,3));
				add(new GridPoint(3,4));
				add(new GridPoint(4,1));
				add(new GridPoint(4,3));
			}}) ;


	/**
	 * Constructor for PlayBoard class
	 */
	public PointPlayBoard() {
		board = new PointSquare[5][5];
		
		this.rabbits = new ArrayList<Rabbit>(3);
		this.foxes = new ArrayList<NewFox>(2);
		
		for (int row = 0; row < 5; row++) { // row
			for (int col = 0; col < 5; col++) { // column
				board[row][col] = new PointSquare(new GridPoint(row, col), PieceType.EMPTY); // (row,column)
			}
		}
		
		
		// set the locations of the Hole
		board[0][0].setPieceType(PieceType.HOLE);
		board[0][4].setPieceType(PieceType.HOLE);
		board[2][2].setPieceType(PieceType.HOLE);
		board[4][0].setPieceType(PieceType.HOLE);
		board[4][4].setPieceType(PieceType.HOLE);
		
		this.setRabbit(1, 1, 4);
		this.setRabbit(2, 2, 4);
		this.setRabbit(3, 4, 1);
		
		setFox(new GridPoint(0, 1), Direction.NORTH);
		setFox(new GridPoint(3, 0), Direction.WEST);
		
		
		board[1][3].setPieceType(PieceType.MUSHROOM);
		board[4][2].setPieceType(PieceType.MUSHROOM);
		
	}
	
	/**
	 * Get the square at point pt
	 * @param pt The GridPoint to retrieve the square object from
	 * @return The PointSquare object at GridPoint pt
	 */
	public PointSquare getSquareAt(GridPoint pt) {
		return this.board[pt.getRow()][pt.getCol()];
	}


	/**
	 * Test for win condition by checking if all rabbits are in holes
	 * @return True if all rabbits are in holes, false otherwise
	 */
	public boolean isWin() {
		return rabbits.get(0).atHole() && rabbits.get(1).atHole() && rabbits.get(2).atHole();
	}

	/**
	 * Retrieve a fox object at a specified location
	 * @param point the point to retrieve the fox at
	 * @return the fox object with a piece at that location
	 */
	public NewFox getFoxAtLocation(GridPoint point) {
		for(int i = 0; i < foxes.size(); ++i) {
			if ((foxes.get(i).getHead().getLocation().equals(point)) || (foxes.get(i).getTail().getLocation().equals(point))) {
				return foxes.get(i);
			}
		}
		return null;
	}

	/**
	 * Retrieve a rabbit
	 * @param i The index of the rabbit to retrieve
	 * @return A Square object that represents the rabbit at index i
	 */
	public Rabbit getRabbit(String str){
		if (str.equals("rabbit1")) {
			return rabbits.get(0);
		} else if (str.equals("rabbit2")) {
			return rabbits.get(1);
		} else if (str.equals("rabbit3")) {
			return rabbits.get(2);
		}
		return null;
	}
	
	/**
	 * Retrieve a rabbit
	 * @param i The index of the rabbit to retrieve
	 * @return A Square object that represents the rabbit at index i
	 */
	public Rabbit getRabbit(RabbitColour colour){
		if (colour.equals(RabbitColour.BROWN)) {
			return rabbits.get(0);
		} else if (colour.equals(RabbitColour.BROWN)) {
			return rabbits.get(1);
		} else if (colour.equals(RabbitColour.GREY)) {
			return rabbits.get(2);
		} else {
			return null;
		}
	}
	
	/**
	 * Set the location of a rabbit on the board
	 * @param row The row of the rabbit location (aka y coordinate)
	 * @param col The column of the rabbit location (aka x coordinate)
	 * @return True if the rabbit was successfully created
	 */
	public void setRabbit(int i, int row, int col) {	
		if (i == 1) {
			rabbits.add(new Rabbit(row, col, "rabbit"+i));
			board[row][col] = rabbits.get(0);
		} else if(i == 2) {
			rabbits.add(new Rabbit(row, col, "rabbit"+i));
			board[row][col] = rabbits.get(1);
		} else if(i == 3) {
			rabbits.add(new Rabbit(row, col,"rabbit"+i));
			board[row][col] = rabbits.get(2);
		} 
	}

	/**
	 * Set the fox helper
	 * @param x
	 * @param direction
	 * @return
	 */
	private Fox[] setFoxHelper(int x, Direction direction) {
		int empty = 0;
		Fox[] temp = new Fox[2];

		if (direction.equals(Direction.HORIZONTAL)) {
			for (int j = 0; j < 5; j++) { // find if there are two empty board connected to put a fox in
				if (empty < 1) {
					if (!board[x][j].isOccupied()) {
						empty++;
					} else {
						empty = 0;
					}
				} else if (!board[x][j].isOccupied()){
					temp[0] = new Fox(x, j, Direction.HORIZONTAL);
					temp[1] = new Fox(x, j - 1, Direction.HORIZONTAL);
					board[x][j] = temp[0];
					board[x][j-1] = temp[1];
					return temp;
				}
			}
		} else if (direction.equals(Direction.VERTICAL)) {
			for (int i = 0; i < 5; i++) {
				if (empty < 1){
					if (!board[i][x].isOccupied()) {
						empty++;
					} else {
						empty = 0;
					}
				} else if (!this.board[i][x].isOccupied()) {
					temp[0] = new Fox(i, x, Direction.VERTICAL);
					temp[1] = new Fox(i - 1, x, Direction.VERTICAL);
					board[i][x] = temp[0];
					board[i-1][x] = temp[1];
					return temp;
				}
			}
		}
		throw new IllegalArgumentException("cannot set fox there");
	}

	

	
	public void setFox(GridPoint foxHead, Direction direction) {
		System.out.println(VALID_FOX_LOCATIONS.contains(foxHead));
		if (VALID_FOX_LOCATIONS.contains(foxHead)) {
			foxes.add(new NewFox(foxHead, direction));
			System.out.println(foxes);
		} else {
			System.err.println("Error in here!");
			throw new IllegalArgumentException("Fox Head is not in a valid location");
		}
		
		
	}

	/**
	 * Move a square to a new location
	 * @param s The square to move
	 * @param newRow The x coordinate of the new location
	 * @param newCol The y coordinate of the new location
	 */
	private void move(PointSquare s, GridPoint newLocation) {
		int currRow = s.getRow();
		int currCol = s.getColumn();
		
		if (!(newLocation.getRow() == currRow && newLocation.getCol() == currCol) && (newLocation.getRow() > -1) && (newLocation.getRow() < 5) && (newLocation.getCol() > -1) && (newLocation.getCol() < 5)) {
			board[newLocation.getRow()][newLocation.getCol()] = s;
			board[currRow][currCol] = new PointSquare(currRow, currCol);
			if (s.atHole()) {
				board[currRow][currCol].setName("Hole");
			}
			s.move(newLocation);
		}
	}
	
	/**
	 * Move a square to a new location
	 * @param rabbit the rabbit to be moved
	 * @param newLoc the new point for the rabbit to move to
	 */
	public void moveRabbit(Rabbit rabbit, GridPoint newLoc) {
		Rectangle rect = new Rectangle(0, 0, 5, 5);
		
		int currRow = rabbit.getRow();
		int currCol = rabbit.getColumn();
		
		System.out.println("currRow = " + currRow + ", currCol = " + currCol);
		System.out.println("Point (x,y) = (" + newLoc.x + "," + newLoc.y + ")");
		
		
		if (rect.contains(newLoc) && !(newLoc.getRow() == currRow && newLoc.getCol() == currCol)) {
			board[newLoc.getRow()][newLoc.getCol()] = rabbit;
			board[currRow][currCol] = new PointSquare(currRow, currCol);
			
			if (rabbit.atHole()) {
				board[currRow][currCol].setName("Hole");
			}
			
			rabbit.move(newLoc);	// set the internal location of the square
		}
	}
	
	/**
	 * Test if a rabbit can jump in a certain direction
	 * @param rabbit The rabbit to be moved
	 * @param direction The direction to test the jump
	 * @return True if the rabbit is able to jump in that direction
	 */
	public boolean testJumpDirection(Rabbit rabbit, Direction direction) {
		if (rabbit == null || direction == null) {
			return false;
		}

		// get rabbit's location
		int row = rabbit.getRow();
		int col = rabbit.getColumn();

		if (direction.equals(Direction.NORTH)) {
			if (row > 0 && this.board[row-1][col].isOccupied()) {	// check if rabbit can move upwards, and if the space north of the rabbit is occupied
				for (int i = 0; i <= row; i++) {
					if (board[row-i][col].isOccupied() == false) {
						return true;
					}
				}
			}
		} else if (direction.equals(Direction.SOUTH)) {
			if (row < 4 && this.board[row+1][col].isOccupied()) {
				for (int i = 0; i < 5-row; i++) {
					if (board[row+i][col].isOccupied() == false) {
						return true;
					}
				}
			}
		} else if (direction.equals(Direction.EAST)) {
			if (col < 4 && board[row][col+1].isOccupied()) {
				for (int j = 0; j < 5-col; j++) {
					if (board[row][col+j].isOccupied() == false) {
						return true;
					}
				}
			}
		} else if (direction.equals(Direction.WEST)) {
			if (col > 0 && board[row][col-1].isOccupied()) {
				for (int j = 0; j <= col; j++) {
					if (board[row][col-j].isOccupied() == false) {
						return true;
					}
				}
			}
		}
		return false;
		
	}
	
	/**
	 * Return the nearest point that the rabbit can jump to in a certain direction
	 * @param rabbit the rabbit to be moved
	 * @param direction the direction in which the rabbit will move
	 * @return Point where the rabbit can jump to
	 */
	public GridPoint getNearestJumpPoint(Rabbit rabbit, Direction direction) {
		// get rabbit's location
		int row = rabbit.getRow();
		int col = rabbit.getColumn();

		if (direction.equals(Direction.NORTH)) {
			if (row > 0 && this.board[row-1][col].isOccupied()) {
				for (int i = 0; i <= row; i++) {
					if (board[row-i][col].isOccupied() == false) {
						return new GridPoint(row-i, col);
					}
				}
			}
		} else if (direction.equals(Direction.SOUTH)) {
			if (row < 4 && this.board[row+1][col].isOccupied()) {
				for (int i = 0; i < 5-row; i++) {
					if (board[row+i][col].isOccupied() == false) {
						return new GridPoint(row+i, col);
					}
				}
			}
		} else if (direction.equals(Direction.EAST)) {
			if (col < 4 && board[row][col+1].isOccupied()) {
				for (int j = 0; j < 5-col; j++) {
					if (board[row][col+j].isOccupied() == false) {
						return new GridPoint(row, col+j);
					}
				}
			}
		} else if (direction.equals(Direction.WEST)) {
			if (col > 0 && board[row][col-1].isOccupied()) {
				for (int j = 0; j <= col; j++) {
					if (board[row][col-j].isOccupied() == false) {
						return new GridPoint(row, col-j);
					}
				}
			}
		}
		return null;
	}
	
	private Direction getMovementDirection(GridPoint oldLoc, GridPoint newLoc) {
		if (oldLoc.getRow() == newLoc.getRow() && oldLoc.getCol() == newLoc.getCol()) {
			throw new IllegalArgumentException("Old location is same as new location!");
		} else if (oldLoc.getRow() == newLoc.getRow()) {
			if (oldLoc.getCol() < newLoc.getCol()) {
				return Direction.EAST;
			} else {
				return Direction.WEST;
			}
		} else if (oldLoc.getCol() == newLoc.getCol()) {
			if (oldLoc.getRow() < newLoc.getRow()) {
				return Direction.NORTH;
			} else {
				return Direction.SOUTH;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Move a rabbit to a new location
	 * @param rabbit The rabbit to move
	 * @param newLoc the location for the rabbit to move to
	 * @return True if the move was successful
	 */
	public void moveRabbitTo(Rabbit rabbit, GridPoint newLoc) {
		
		// get the direction the rabbit is jumping in
		Direction jumpDirection = this.getMovementDirection(new GridPoint(rabbit.getRow(), rabbit.getColumn()), newLoc);
		
		
		// TODO: implement direction finding code here
		
		if (this.getNearestJumpPoint(rabbit, jumpDirection).equals(newLoc)) {
			// TODO: implement rabbit movement code here
			if (board[newLoc.getRow()][newLoc.getCol()].isOccupied()) {
				throw new IllegalArgumentException("This location is already occupied!");
			}
		} else {
			throw new IllegalArgumentException("Invalid jump location!");
		}
	}
	
	/**
	 * Test if a fox can move to a given location
	 * @param fox the fox to test if 
	 * @param newLocation
	 * @return true if the specified fox can move to the new location
	 */
	// TODO: write this function
	public boolean testValidFoxMove(NewFox fox, GridPoint newLocation) {
		return false;
	}
	
	/**
	 * Move a fox to a new location
	 * @param fox the fox to be moved
	 * @param newLocation the new location on the board for the head of the fox to be moved to
	 */
	// TODO: implement this function
	public void moveFox(NewFox fox, GridPoint newLocation) {
		if (testValidFoxMove(fox, newLocation) == true) {
			// move the fox
		} else {
			throw new IllegalArgumentException("Illegal fox move");
		}
	}
	
	public String[][] getBoardName(){
		String[][] name = new String[5][5];
		
		for(int r=0; r<5; r++) {
			for(int c=0; c<5; c++) {
				name[r][c] = board[r][c].toString();
			}
		}
		
		return name;
	}
}
