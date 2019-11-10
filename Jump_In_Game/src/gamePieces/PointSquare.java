package gamePieces;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PointSquare {
	//position
	private GridPoint location;
	private String name;
	private PieceType pieceType;
	private PropertyChangeSupport support;

	
	/**
	 * Constructor for PointSquare
	 * @param location Point object that represents the location on the grid of the square
	 */
	public PointSquare(Point location) {
		if ((location.x < 0) || (location.x > 4)) {
			throw new IllegalArgumentException("Invalid row given!");
		} else if ((location.y < 0) || (location.y > 4)) {
			throw new IllegalArgumentException("Invalid column given!");
		}
		
		this.location = new GridPoint(location);
		this.name = null;
		this.pieceType = PieceType.EMPTY;
		this.support = new PropertyChangeSupport(this);
	}
	
	/**
	 * Constructor for Square object
	 * @param row The row of the square
	 * @param col The column of the square
	 */
	public PointSquare(int row, int col) {
		this(new Point(col, row));
	}
	
	/**
	 * Constructor for Square object
	 * @param row The row of the square
	 * @param col The column of the square
	 * @param PieceType the type of piece this square represents
	 */
	public PointSquare(int row, int col, PieceType pieceType) {
		this(new Point(col, row));
		this.pieceType = pieceType;
	}
	
	/**
	 * Constructor for Square object
	 * @param location the location of the square
	 * @param PieceType the type of piece this square represents
	 */
	public PointSquare(Point location, PieceType pieceType) {
		this(location);
		this.pieceType = pieceType;
	}
	
	/**
	 * Constructor for square object, with name
	 * @param row The row of this square
	 * @param col The column of this square
	 * @param name The name of the object in this square
	 */
	public PointSquare(int row, int col, String name) {
		this(new Point(col, row));
		this.name = name;
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
		return this.location.getRow();
	}

	/**
	 * Get the column of the location of this square
	 * @return The column of this square (aka x coordinate)
	 */
	public int getColumn() {
		return this.location.getCol();
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
		if (this.location.getCol() == 0) {
			if (this.location.getRow() == 0 || this.location.getRow() == 4) {
				return true;
			}
		} else if (this.location.getCol() == 4) {
			if (this.location.getRow() == 0 || this.location.getRow() == 4) {
				return true;
			}
		} else if (this.location.getCol() == 2 && this.location.getRow() == 2) {
			return true;
		} 
		
		return false;
	}

	/**
	 * Move the piece to a new location
	 * @param newLocation the new coordinate location of the point
	 */
	public void move(GridPoint newLocation) {
		if ((newLocation.getRow() < 0) || (newLocation.getRow() > 4)) {
			throw new IllegalArgumentException("Invalid row given!");
		} else if ((newLocation.getCol() < 0) || (newLocation.getCol() > 4)) {
			throw new IllegalArgumentException("Invalid column given!");
		}
		
		GridPoint oldLocation = this.location;
		this.location.setLocation(newLocation);
		support.firePropertyChange("location", oldLocation, newLocation); // Fire property changes when the square is moved
	}

	/**
	 * Test if the square is occupied
	 * @return true if the space is occupied
	 */
	public boolean isOccupied() {
		if (this.pieceType != PieceType.EMPTY) {
			return true;
		} else {
			return false;
		}
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
