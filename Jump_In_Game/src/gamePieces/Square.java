package gamePieces;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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
	private PropertyChangeSupport support;

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
		support = new PropertyChangeSupport(this);
	}
	
	/**
	 * Constructor for Square object
	 * @param row The row of the square
	 * @param col The column of the square
	 */
	public Square(int row, int col, PieceType pieceType) {
		//board is set to 5x5
		if ((row < 0) || (row > 4)) {
			throw new IllegalArgumentException("Invalid row given!");
		} else if ((col < 0) || (col > 4)) {
			throw new IllegalArgumentException("Invalid column given!");
		}

		this.column = col;
		this.row = row;
		this.pieceType = pieceType;
		name = null;
		support = new PropertyChangeSupport(this);
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
		support = new PropertyChangeSupport(this);
	}
	
	/**
	 * Add a new property change lister
	 * @param pcl The PropertyChangeListener to be added
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}
	
	/**
	 * Remove a property change listener
	 * @param pcl The PropertyChangeListener to be removed
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
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
		
		// Fire property changes when the square is moved
		support.firePropertyChange("row", row, this.row);
		support.firePropertyChange("col", col, this.column);
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
