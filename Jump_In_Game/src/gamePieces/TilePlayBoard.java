package gamePieces;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

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
		// Test all the conditions to ensure that rabbit can be moved!
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
	
	public boolean testNoObstructionsOnFoxPath(NewFox fox, ArrayList<Tile> path) {
		for (int i = 0; i < path.size(); ++i) {
			// Test if the tile is occupied by a token other than the given fox
			if (path.get(i).isOccupied() 
					&& (path.get(i).getToken() != fox.getHead() || path.get(i).getToken() != fox.getTail())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Get all the tiles along the movement path of a fox piece
	 * NOTE: if using head as both start and end point, path may not account for the end location of the tail!
	 * @param startPoint the starting point for the fox movement
	 * @param endPoint the ending point for the fox movement
	 * @return ArrayList with all the tiles along the path
	 */
	public ArrayList<Tile> getTilesAlongFoxPath(GridPoint startPoint, GridPoint endPoint) {
		ArrayList<Tile> tilesInPath = new ArrayList<Tile>();
		Direction direction = startPoint.getDirectionTo(endPoint);
		
		if (direction.equals(Direction.NORTH)) {
			for (int row = startPoint.getRow(); row >= endPoint.getRow(); --row) {
				tilesInPath.add(board.getTileAt(row, startPoint.getCol()));
			}
		} else if (direction.equals(Direction.SOUTH)) {
			for (int row = startPoint.getRow(); row <= endPoint.getRow(); ++row) {
				tilesInPath.add(board.getTileAt(row, startPoint.getCol()));
			}
		} else if (direction.equals(Direction.EAST)) {
			for (int col = startPoint.getCol(); col <= endPoint.getCol(); ++col) {
				tilesInPath.add(board.getTileAt(startPoint.getRow(), col));
			}
		} else if (direction.equals(Direction.WEST)) {
			for (int col = startPoint.getCol(); col >= endPoint.getCol(); --col) {
				tilesInPath.add(board.getTileAt(startPoint.getRow(), col));
			}
		}
		
		return tilesInPath;
	}
		
	
	/**
	 * Test if a fox can move to a given location
	 * @param fox the fox to test if 
	 * @param newHeadLocation
	 * @return true if the specified fox can move to the new location
	 */
	public boolean testValidFoxMove(NewFox fox, GridPoint newHeadLocation) {
		Direction movementDirection = fox.getHead().getLocation().getDirectionTo(newHeadLocation);
		
		if (movementDirection.equals(Direction.EAST) || movementDirection.equals(Direction.WEST)) {	// if fox is horizontal
			// test if head and tail share same row as the new location
			if (fox.getHead().getRow() == newHeadLocation.getRow() && fox.getTail().getRow() == newHeadLocation.getRow()) { 
				
				// Get the location that the tail of the fox would be moved to
				GridPoint newTailLocation = NewFox.getTheoreticalNewTailLocation(newHeadLocation, fox.getOrientation());
				
				// If the new theoretical head and tail locations of the fox are valid locations
				if (NewFox.getValidFoxLocations().contains(newTailLocation) && NewFox.getValidFoxLocations().contains(newHeadLocation)) {
					ArrayList<Tile> tilesInPath = new ArrayList<Tile>();
					
					// get the two points the farthest away from one another
					if (fox.getHead().getLocation().distance(newTailLocation) > fox.getHead().getLocation().distance(newHeadLocation)) {
						tilesInPath = this.getTilesAlongFoxPath(fox.getHead().getLocation(), newTailLocation);
					} else {
						tilesInPath = this.getTilesAlongFoxPath(fox.getHead().getLocation(), newHeadLocation);
					}
					
					return this.testNoObstructionsOnFoxPath(fox, tilesInPath);
				}
			}
		} else if (movementDirection.equals(Direction.NORTH) || movementDirection.equals(Direction.SOUTH)) {
			if (fox.getHead().getCol() == newHeadLocation.getCol() && fox.getTail().getCol() == newHeadLocation.getCol()) {
				GridPoint newTailLocation = NewFox.getTheoreticalNewTailLocation(newHeadLocation, fox.getOrientation());
				
				// If the new theoretical head and tail locations are valid locations
				if (NewFox.getValidFoxLocations().contains(newTailLocation) && NewFox.getValidFoxLocations().contains(newHeadLocation)) {
					ArrayList<Tile> tilesInPath = new ArrayList<Tile>();
					
					// get the two points farthest away from one another
					if (fox.getHead().getLocation().distance(newTailLocation) > fox.getHead().getLocation().distance(newHeadLocation)) {
						tilesInPath = this.getTilesAlongFoxPath(fox.getHead().getLocation(), newTailLocation);
					} else {
						tilesInPath = this.getTilesAlongFoxPath(fox.getHead().getLocation(), newHeadLocation);
					}
					
					return this.testNoObstructionsOnFoxPath(fox, tilesInPath);
				}
			}
		}
		return false;
	}
	
	/**
	 * Move a fox to a new location
	 * @param fox the fox to be moved
	 * @param newLocation the new location on the board for the head of the fox to be moved to
	 */
	// TODO: implement this function
	public void moveFox(NewFox fox, GridPoint newLocation) {
		// If the fox can move
		if (testValidFoxMove(fox, newLocation) == true) {
			// move the fox one token at a time to its new location
			this.moveToken(fox.getHead(), newLocation);
			this.moveToken(fox.getTail(), NewFox.getTheoreticalNewTailLocation(newLocation, fox.getOrientation()));
			
		} else {
			throw new IllegalArgumentException("Illegal fox move");
		}
	}
	
	public String[][] getBoardName(){
		String[][] name = new String[5][5];
		
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				name[row][col] = board.getTileAt(row, col).toString();
			}
		}
		return name;
	}
	
	public void printBoard() {
		String[][] boardString = this.getBoardName();
		
		for(int row = 0; row < 5; ++row) {
			for(int col = 0; col < 5; ++col) {
				System.out.print(board.getTileAt(row, col).toString() + ", ");
			}
			System.out.print("\n");
		}
	}
	
	public static void main(String[] args) {
		TilePlayBoard board = new TilePlayBoard();
		
		board.printBoard();
	}
}
