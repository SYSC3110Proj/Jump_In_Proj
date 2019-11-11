package gamePieces;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TilePlayBoard {

	private Board board;	// Format: board[row][col]
	private ArrayList<Rabbit> rabbits;
	private ArrayList<NewFox> foxes;
	private ArrayList<Token> mushrooms;
	
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
	public TilePlayBoard() {
		this.board = new Board();
		
		this.rabbits = new ArrayList<Rabbit>(3);
		this.foxes = new ArrayList<NewFox>(2);
		
		for (int row = 0; row < 5; row++) { // row
			for (int col = 0; col < 5; col++) { // column
				board.setTileAt(row, col, new Tile(new GridPoint(row, col), null, false));
			}
		}
		
		// set the locations of the Holes
		board.getTileAt(0, 0).setHole(true);
		board.getTileAt(0, 4).setHole(true);
		board.getTileAt(2, 2).setHole(true);
		board.getTileAt(4, 0).setHole(true);
		board.getTileAt(4, 4).setHole(true);
		
		// Add Mushrooms
		this.mushrooms = new ArrayList<Token>(2);
		this.mushrooms.add(new Token(new GridPoint(1, 3), PieceType.MUSHROOM));
		this.mushrooms.add(new Token(new GridPoint(4, 2), PieceType.MUSHROOM));
		board.getTileAt(this.mushrooms.get(0).getLocation()).setToken(this.mushrooms.get(0));
		board.getTileAt(this.mushrooms.get(1).getLocation()).setToken(this.mushrooms.get(1));
		
		// Add Rabbits
		this.rabbits.add(new Rabbit(new GridPoint(1,4), "rabbit1"));
		this.rabbits.add(new Rabbit(new GridPoint(2,4), "rabbit2"));
		this.rabbits.add(new Rabbit(new GridPoint(4,1), "rabbit3"));
		
		board.getTileAt(this.rabbits.get(0).getLocation()).setToken(this.rabbits.get(0));
		board.getTileAt(this.rabbits.get(1).getLocation()).setToken(this.rabbits.get(1));
		board.getTileAt(this.rabbits.get(2).getLocation()).setToken(this.rabbits.get(2));
		
		// Set Foxes
		this.setFox(new GridPoint(0, 1), Direction.NORTH);
		this.setFox(new GridPoint(3, 0), Direction.WEST);
	}
	
	/**
	 * Get the board
	 * @return the board
	 */
	public Board getBoard() {
		return this.board;
	}


	/**
	 * Test for win condition by checking if all rabbits are in holes
	 * @return True if all rabbits are in holes, false otherwise
	 */
	public boolean checkWinState() {
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

	

	
	public void setFox(GridPoint foxHead, Direction direction) {
		System.out.println(VALID_FOX_LOCATIONS.contains(foxHead));
		if (VALID_FOX_LOCATIONS.contains(foxHead)) {
			foxes.add(new NewFox(foxHead, direction));
			
			NewFox newlyAddedFox = this.foxes.get(this.foxes.size() - 1);
			
			// Add the fox tokens to the game board
			this.board.getTileAt(newlyAddedFox.getHead().getLocation()).setToken(newlyAddedFox.getHead());
			this.board.getTileAt(newlyAddedFox.getTail().getLocation()).setToken(newlyAddedFox.getTail());
		} else {
			System.err.println("Error in here!");
			throw new IllegalArgumentException("Fox Head is not in a valid location");
		}
	}

	/**
	 * Move a square to a new location
	 * @param token The token to move
	 * @param newLocation The coordinates of the new location
	 */
	private void moveToken(Token token, GridPoint newLocation) {
		
		// TODO: test if new location is within board
		
		// If token is moving
		if (token.getLocation().equals(newLocation) == false) {
			if (board.getTileAt(newLocation).getToken() != null) {
				throw new IllegalArgumentException("Tile at " + newLocation.toString() + " is occupied!");
			} else {
				GridPoint oldLocation = token.getLocation();
				// Move token to new location and set the token at the old location as null
				this.board.getTileAt(newLocation).setToken(token);
				this.board.getTileAt(oldLocation).setToken(null);
			}
			
		}
	}
	
	/**
	 * Move a rabbit token to a new location
	 * @param rabbit the rabbit token to be moved
	 * @param newLoc the new point for the rabbit to move to
	 */
	public void moveRabbit(Rabbit rabbit, GridPoint newLoc) {
		if (this.testJumpDirection(rabbit, rabbit.getLocation().getDirectionTo(newLoc)) 
				&& this.getNearestJumpPoint(rabbit, rabbit.getLocation().getDirectionTo(newLoc)).equals(newLoc) 
				&& (board.getTileAt(newLoc).isOccupied()) == false) {
			this.moveToken(rabbit, newLoc);
		} else {
			throw new IllegalArgumentException("Rabbit at " + rabbit.getLocation() + " cannot jump to " + newLoc);
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
			if (row > 0 && this.board.getTileAt(row-1, col).isOccupied()) {	// check if rabbit can move upwards, and if the space north of the rabbit is occupied
				for (int i = 0; i <= row; i++) {
					if (board.getTileAt(row-i, col).isOccupied() == false) {
						return true;
					}
				}
			}
		} else if (direction.equals(Direction.SOUTH)) {
			if (row < 4 && this.board.getTileAt(row+1, col).isOccupied()) {
				for (int i = 0; i < 5-row; i++) {
					if (board.getTileAt(row+i, col).isOccupied() == false) {
						return true;
					}
				}
			}
		} else if (direction.equals(Direction.EAST)) {
			if (col < 4 && board.getTileAt(row, col+1).isOccupied()) {
				for (int i = 0; i < 5-col; i++) {
					if (board.getTileAt(row, col+i).isOccupied() == false) {
						return true;
					}
				}
			}
		} else if (direction.equals(Direction.WEST)) {
			if (col > 0 && board.getTileAt(row, col-1).isOccupied()) {
				for (int i = 0; i <= col; i++) {
					if (board.getTileAt(row, col-i).isOccupied() == false) {
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
			if (row > 0 && this.board.getTileAt(row-1, col).isOccupied()) {	// check if rabbit can move upwards, and if the space north of the rabbit is occupied
				for (int i = 0; i <= row; i++) {
					if (board.getTileAt(row-i, col).isOccupied() == false) {
						return new GridPoint(row-i, col);
					}
				}
			}
		} else if (direction.equals(Direction.SOUTH)) {
			if (row < 4 && this.board.getTileAt(row+1, col).isOccupied()) {
				for (int i = 0; i < 5-row; i++) {
					if (board.getTileAt(row+i, col).isOccupied() == false) {
						return new GridPoint(row+i, col);
					}
				}
			}
		} else if (direction.equals(Direction.EAST)) {
			if (col < 4 && board.getTileAt(row, col+1).isOccupied()) {
				for (int j = 0; j < 5-col; j++) {
					if (board.getTileAt(row, col+j).isOccupied() == false) {
						return new GridPoint(row, col+j);
					}
				}
			}
		} else if (direction.equals(Direction.WEST)) {
			if (col > 0 && board.getTileAt(row, col-1).isOccupied()) {
				for (int j = 0; j <= col; j++) {
					if (board.getTileAt(row, col-j).isOccupied() == false) {
						return new GridPoint(row, col-j);
					}
				}
			}
		} else {
			throw new IllegalArgumentException("No possible points for rabbit in direction " + direction);
		}
		return null;
	}
	
	/**
	 * Move a rabbit to a new location
	 * @param rabbit The rabbit to move
	 * @param newLoc the location for the rabbit to move to
	 * @return True if the move was successful
	 */
	public void moveRabbitTo(Rabbit rabbit, GridPoint newLoc) {
		
		// get the direction the rabbit is jumping in
		Direction jumpDirection = new GridPoint(rabbit.getRow(), rabbit.getColumn()).getDirectionTo(newLoc);
		
		
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
	
	
	/**
	 * Retrieve a rabbit
	 * @param i The index of the rabbit to retrieve
	 * @return A Square object that represents the rabbit at index i
	 */
//	public Rabbit getRabbit(String str){
//		if (str.equals("rabbit1")) {
//			return rabbits.get(0);
//		} else if (str.equals("rabbit2")) {
//			return rabbits.get(1);
//		} else if (str.equals("rabbit3")) {
//			return rabbits.get(2);
//		}
//		return null;
//	}
	
	/**
	 * Retrieve a rabbit
	 * @param i The index of the rabbit to retrieve
	 * @return A Square object that represents the rabbit at index i
	 */
//	public Rabbit getRabbit(RabbitColour colour){
//		if (colour.equals(RabbitColour.BROWN)) {
//			return rabbits.get(0);
//		} else if (colour.equals(RabbitColour.BROWN)) {
//			return rabbits.get(1);
//		} else if (colour.equals(RabbitColour.GREY)) {
//			return rabbits.get(2);
//		} else {
//			return null;
//		}
//	}
}
