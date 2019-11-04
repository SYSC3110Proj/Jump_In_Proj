package gamePieces;

public class Square {
	//position
	private int column;
	private int row;
	private String name;

	/**
	 * Constructor for Square object
	 * @param x The x coordinate of the square
	 * @param y The y coordinate of the square
	 */
	public Square(int x, int y) {
		//board is set to 5x5
		if ((x < 0) || (x > 4)) {
			throw new IllegalArgumentException("Invalid x coordinate");
		} else if ((y < 0) || (y > 4)) {
			throw new IllegalArgumentException("Invalid y coordinate");
		}

		column = y;
		row = x;
		name = null;
	}

	/**
	 * Constructor for square object, with name
	 * @param x The x coordinate of this square
	 * @param y The y coordinate of this square
	 * @param name The name of the object in this square
	 */
	public Square(int x, int y, String name) {
		this(x,y);
		this.name = name;
	}

	/**
	 * Get the row of this square
	 * @return the x location of this square
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Get the column of the location of this square
	 * @return The y location of this square
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
	 * @param x Horizontal coordinate of the new location
	 * @param y Vertical location of the new coordinate
	 */
	public void move(int x, int y) {
		if ((x < 0) || (x > 4)) {
			throw new IllegalArgumentException("Invalid x coordinate");
		} else if ((y < 0) || (y > 4)) {
			throw new IllegalArgumentException("Invalid y coordinate");
		}
		
		row = x;
		column = y;
	}

	/**
	 * Test if the square is occupied
	 * @return true if the space is occupied
	 */
	public boolean isOccupied() {
		//tell if the square is occupied
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
