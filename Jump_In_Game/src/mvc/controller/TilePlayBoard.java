package mvc.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

import gamePieces.*;
import tree.MovementData;

public class TilePlayBoard  implements Serializable{

	private Board board;
	private ArrayList<Rabbit> rabbits;
	private ArrayList<NewFox> foxes;
	private ArrayList<Token> mushrooms;
	private boolean winState;	// Indicator to whether or not the game has been won
	private PropertyChangeSupport support;
	private Stack<Record> before;
	private Stack<Record> after;


	/**
	 * Constructor for PlayBoard class
	 */
	public TilePlayBoard() {
		this.board = new Board();
		
		this.before = new Stack<Record>();
		this.after = new Stack<Record>();
		
		this.rabbits = new ArrayList<Rabbit>();
		this.foxes = new ArrayList<NewFox>();
		this.mushrooms = new ArrayList<Token>();
		this.winState = false;
		
		this.support = new PropertyChangeSupport(this);
		
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
	}
	
	public TilePlayBoard(TilePlayBoard playBoard) {
		this();
		
		// Add Mushrooms
		for(int i=0; i<playBoard.getMushrooms().size(); i++) {
			this.setMushroom(playBoard.getMushrooms().get(i).getRow(), playBoard.getMushrooms().get(i).getCol());
		}
				
		// Add Rabbits
		for(int i=1; i<=playBoard.getRabbitNum(); i++) {
			this.rabbits.add(new Rabbit(new GridPoint(playBoard.getRabbit(i-1).getRow(), playBoard.getRabbit(i-1).getCol()), "rabbit"+i));
			board.getTileAt(this.rabbits.get(i-1).getLocation()).setToken(this.rabbits.get(i-1));
		}
				
		//add foxes
		for(int i=0; i<playBoard.getFoxNum(); i++) {
			this.setFox(new GridPoint(playBoard.getFox(i).getHead().getRow(), playBoard.getFox(i).getHead().getCol()), playBoard.getFox(i).getOrientation());
		}	
	}
	
	public void setMushroom(int row, int col) {
		
		Token mushroom = new Token(new GridPoint(row, col), PieceType.MUSHROOM);
		int num=this.mushrooms.size();
		mushroom.setName("mushroom");
		this.mushrooms.add(mushroom);
		board.getTileAt(this.mushrooms.get(num).getLocation()).setToken(this.mushrooms.get(num));
		
	}
	
	public ArrayList<Token> getMushrooms() {
		return this.mushrooms;
	}
	
	/**
	 * @param rabbits the rabbits to set
	 */
	public void setRabbit(int row, int col) {
		int num=this.rabbits.size()+1;
		this.rabbits.add(new Rabbit(new GridPoint(row, col), "rabbit"+ num));
		board.getTileAt(this.rabbits.get(num-1).getLocation()).setToken(this.rabbits.get(num-1));
		
	}
	/**
	 * @return the rabbits
	 */
	public ArrayList<Rabbit> getRabbits() {
		return rabbits;
	}

	/**
	 * @param rabbits the rabbits to set
	 */
	public void setRabbits(ArrayList<Rabbit> rabbits) {
		this.rabbits = rabbits;
	}

	/**
	 * @return the foxes
	 */
	public ArrayList<NewFox> getFoxes() {
		return foxes;
	}

	/**
	 * @param foxes the foxes to set
	 */
	public void setFoxes(ArrayList<NewFox> foxes) {
		this.foxes = foxes;
	}

	/**
	 * Add a PropertyChangeListener to observe this class
	 * @param pcl the PropertyChangeListener to observe this class
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}
	
	/**
	 * Remove a PropertyChangeListener
	 * @param pcl the PropertyChangeListener already observing this class
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}

	/**
	 * Get the board
	 * @return the board
	 */
	public Board getBoard() {
		return this.board;
	}
	
	public Rabbit getRabbit(int i) {
		return rabbits.get(i);
	}
	
	public NewFox getFox(int i) {
		return foxes.get(i);
	}


	/**
	 * Test for win condition by checking if all rabbits are in holes
	 */
	private void checkWinState() {
		boolean prevWinState = this.winState;
		boolean nowWinState = true;
		
		for(Rabbit r: rabbits) {
			nowWinState &= r.atHole();
		}
		
		this.winState = nowWinState;
		support.firePropertyChange("winState", prevWinState, this.winState); // send the propertyChange
	}

	public boolean getWinState() {
		return this.winState;
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
		int num = foxes.size()+1;
		if (NewFox.getValidFoxLocations().contains(foxHead)) {
			foxes.add(new NewFox(foxHead, direction, "fox" + num ));
			NewFox newlyAddedFox = this.foxes.get(this.foxes.size() - 1);
			
			// Add the fox tokens to the game board
			this.board.getTileAt(newlyAddedFox.getHead().getLocation()).setToken(newlyAddedFox.getHead());
			this.board.getTileAt(newlyAddedFox.getTail().getLocation()).setToken(newlyAddedFox.getTail());
		} else {
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
		if (!token.getLocation().equals(newLocation)) {
			if (board.getTileAt(newLocation).getToken() != null) {
				throw new IllegalArgumentException("Tile at " + newLocation.toString() + " is occupied!");
			} else {
				GridPoint oldLocation = token.getLocation();
				// Move token to new location and set the token at the old location as null
				
				before.add(new Record(token, oldLocation, newLocation));
				
				this.board.getTileAt(newLocation).setToken(token);
				this.board.getTileAt(oldLocation).setToken(null);
				this.checkWinState();
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
	 * Move a rabbit token to a new location without making any checks first (for solver)
	 * @param rabbit the rabbit token to be moved
	 * @param newLoc the new point for the rabbit to move to
	 */
	public void moveRabbitNoChecks(Rabbit rabbit, GridPoint newLoc) {
		this.moveToken(rabbit, newLoc);
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

		return this.getNearestJumpPoint(rabbit, direction) != null;
	}
	
	/**
	 * Return the nearest point that the rabbit can jump to in a certain direction
	 * @param rabbit the rabbit to be moved
	 * @param direction the direction in which the rabbit will move
	 * @return Point where the rabbit can jump to
	 */
	public GridPoint getNearestJumpPoint(Rabbit rabbit, Direction direction) {
		
		if (direction.equals(Direction.NORTH)) {
			if (rabbit.getRow() > 1 && this.board.getTileAt(rabbit.getRow()-1, rabbit.getCol()).isOccupied()) {	// check if rabbit can move upwards, and if the space north of the rabbit is occupied
				for (int row = rabbit.getRow()-1; row >= 0; row--) {
					Tile tile = board.getTileAt(row, rabbit.getCol());
					if (!tile.isOccupied()) {
						return tile.getLocation();
					}
				}
			}
		} else if (direction.equals(Direction.SOUTH)) {
			if (rabbit.getRow() < 3 && this.board.getTileAt(rabbit.getRow()+1, rabbit.getCol()).isOccupied()) {
				for (int row = rabbit.getRow()+1; row <= 4; row++) {
					Tile tile = board.getTileAt(row, rabbit.getCol());
					if (!tile.isOccupied()) {
						return tile.getLocation();
					}
				}
			}
		} else if (direction.equals(Direction.EAST)) {
			if (rabbit.getCol() < 3 && board.getTileAt(rabbit.getRow(), rabbit.getCol()+1).isOccupied()) {
				for (int j = rabbit.getCol()+1; j <= 4; j++) {
					Tile tile = board.getTileAt(rabbit.getRow(), j);
					if (!tile.isOccupied()) {
						return tile.getLocation();
					}
				}
			}
		} else if (direction.equals(Direction.WEST)) {
			if (rabbit.getCol() > 1 && board.getTileAt(rabbit.getRow(), rabbit.getCol()-1).isOccupied()) {
				for (int col = rabbit.getCol()-1; col >= 0; col--) {
					Tile tile = board.getTileAt(rabbit.getRow(), col);
					if (!tile.isOccupied()) {
						return tile.getLocation();
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
			if (path.get(i).isOccupied()) {
				if (path.get(i).getToken() == fox.getHead() || path.get(i).getToken() == fox.getTail()) {
					// keep going
				} else {
					return false;
				}
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
		
		if (movementDirection == null) {
			return false;
		}
		
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
	public void moveFox(NewFox fox, GridPoint newLocation) {
		// If the fox can move
		if (testValidFoxMove(fox, newLocation)) {
			// move the fox one token at a time to its new location
			
			// If the fox is moving backwards, move the tail first
			if (fox.getHead().getLocation().getDirectionTo(newLocation) != fox.getOrientation()) {
				this.moveToken(fox.getTail(), NewFox.getTheoreticalNewTailLocation(newLocation, fox.getOrientation()));
				this.moveToken(fox.getHead(), newLocation);
			} else { // else move the head first
				this.moveToken(fox.getHead(), newLocation);
				this.moveToken(fox.getTail(), NewFox.getTheoreticalNewTailLocation(newLocation, fox.getOrientation()));
			}
			
			
		} 
		/*else {
			throw new IllegalArgumentException("Illegal fox move");
		}*/
	}
	
	public void redo() {
		if(after.empty())return;
		
		Record record = after.pop();
		moveToken(record.getPiece(), record.getNextLoc());
		if(record.getPiece().getPieceType().equals(PieceType.FOX)) {
			record = after.pop();
			moveToken(record.getPiece(), record.getNextLoc());
		}
	}
	
	public void undo() {
		if(before.empty())return;
		
		Record record = before.pop();
		after.add(record);
		moveToken(record.getPiece(), record.getLastLoc());
		before.pop();
		
		if(record.getPiece().getPieceType().equals(PieceType.FOX)) {
			record = before.pop();
			after.add(record);
			moveToken(record.getPiece(), record.getLastLoc());
			before.pop();
		}
	}
	
	/**
	 * Move a fox to a new location without performing any checks (for solver)
	 * @param fox the fox to move
	 * @param newLocation the new location to move the fox to
	 */
	public void moveFoxNoChecks(NewFox fox, GridPoint newLocation) {
		// If the fox is moving backwards, move the tail first
		if (fox.getHead().getLocation().getDirectionTo(newLocation) != fox.getOrientation()) {
			this.moveToken(fox.getTail(), NewFox.getTheoreticalNewTailLocation(newLocation, fox.getOrientation()));
			this.moveToken(fox.getHead(), newLocation);
		} else { // else move the head first
			this.moveToken(fox.getHead(), newLocation);
			this.moveToken(fox.getTail(), NewFox.getTheoreticalNewTailLocation(newLocation, fox.getOrientation()));
		}
	}
	
	/**
	 * Execute a move based on the given MovementData object
	 * @param move
	 */
	public void executeMove(MovementData move) {
		if (move.getToken().getPieceType() == PieceType.RABBIT) {
			// TODO: add more conditions to prevent errors
			if (this.rabbits.contains(board.getTileAt(move.getToken().getLocation()).getToken())) {
				Rabbit rabbitToMove = this.rabbits.get(this.rabbits.indexOf(this.board.getTileAt(move.getToken().getLocation()).getToken()));
				this.moveRabbitNoChecks(rabbitToMove, move.getNewLocation());
			} else {
				System.err.println("Rabbit cannot be found!");
			}
		} else if (move.getToken().getPieceType() == PieceType.FOX) {
			NewFox foxToMove = this.getFoxAtLocation(move.getToken().getLocation());
			this.moveFoxNoChecks(foxToMove, move.getNewLocation());
		}
	}
	
	public String[][] getBoardName(){
		String[][] name = new String[5][5];
		
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				if (board.getTileAt(row, col).getToken() == null) {
					name[row][col] = "";
				} 
				else {
					name[row][col] = board.getTileAt(row, col).getToken().getName();
				}
			}
		}
		return name;
	}
	
	public boolean isWin() {
		return winState;
	}
	
	public int getRabbitNum() {
		return rabbits.size();
	}
	
	public int getFoxNum() {
		return foxes.size();
	}
	
	public Record getUndoInfo() {
		return after.peek();
	}
	public static void save(Serializable data, String file) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(Paths.get(file)));
			output.writeObject(data);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Object load(String file) {
		Object o=null;
		try {
			ObjectInputStream input = new ObjectInputStream(Files.newInputStream(Paths.get(file)));
			o=input.readObject();
			input.close();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return o;
		
	}
	public void resetBoard(Board b) {
		this.board=b;
	}
	
}
