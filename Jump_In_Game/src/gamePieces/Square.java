package gamePieces;

/** 
 * The square object is used to seperate the board into squares, it has it's own coordinates and is either an empty square, or contains
 * an object such as a rabbit, fox, or hole.
 * @author Zewen Chen
 */

public class Square {
	//position
	private int column;
	private int row;
	private String name;
	private PieceType pieceType;

	/**
	 * Constructor for Square object
	 * @param row The row of the square
	 * @param col The column of the square
	 */
	public Square(int row, int col) {
		//board is set to 5x5
		if ((row < 0) || (row > 4)) {
			throw new IllegalArgumentException("Invalid row given!");
		} else if ((col < 0) || (col > 4)) {
			throw new IllegalArgumentException("Invalid column given!");
		}

		this.column = col;
		this.row = row;
		name = null;
	}

	/**
	 * @return the pieceType
	 */
	public PieceType getPieceType() {
		return pieceType;
	}

	/**
	 * @param pieceType the pieceType to set
	 */
	public void setPieceType(PieceType pieceType) {
		this.pieceType = pieceType;
	}

	/**
	 * Constructor for square object, with name
	 * @param row The row of this square
	 * @param col The column of this square
	 * @param name The name of the object in this square
	 */
	public Square(int row, int col, String name) {
		this(row,col);
		this.name = name;
	}

	/**
	 * Get the row of this square
	 * @return the row of this square (aka y coordinate)
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Get the column of the location of this square
	 * @return The column of this square (aka x coordinate)
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Set the name of the object in the square
	 * @param name The name of the object for this square
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the name of the object in the square
	 * @return the name of the object in the square
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Test if the square is at a hole location
	 * @return True if the square is at a hole
	 */
	public boolean atHole() {
		if ((row == 0) && ((column == 0 || column == 4))) {
			return true;
		} else if ((row == 4) && ((column == 0 || column == 4))) {
			return true;
		} else if ((row == 2) && (column == 2)) {
			return true;
		}
		return false;
	}

	/**
	 * Move the piece to a new location
	 * @param row Horizontal coordinate of the new location
	 * @param col Vertical location of the new coordinate
	 */
	public void move(int row, int col) {
		if ((row < 0) || (row > 4)) {
			throw new IllegalArgumentException("Invalid row given!");
		} else if ((col < 0) || (col > 4)) {
			throw new IllegalArgumentException("Invalid column given!");
		}
		
		this.row = row;
		this.column = col;
	}

	/**
	 * Test if the square is occupied
	 * @return true if the space is occupied
	 */
	// TODO: Change this to use enums instead of names
	public boolean isOccupied() {
		return (name != null && (!name.equals("Hole")));
	}

	/**
	 * toString function for this square
	 */
	public String toString() {
		if(name == null) {
			return null;
		}
		return name;
	}

}
