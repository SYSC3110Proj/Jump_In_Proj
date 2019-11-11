package gamePieces;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Token {
	protected GridPoint location;
	private String name;
	private PieceType pieceType;
	private PropertyChangeSupport support;

	
	/**
	 * Constructor for PointSquare
	 * @param location Point object that represents the location on the grid of the square
	 */
	public Token(Point location, PieceType pieceType) {
		if ((location.x < 0) || (location.x > 4)) {
			throw new IllegalArgumentException("Invalid row given!");
		} else if ((location.y < 0) || (location.y > 4)) {
			throw new IllegalArgumentException("Invalid column given!");
		}
		
		this.location = new GridPoint(location);
		this.name = null;
		this.pieceType = pieceType;
		this.support = new PropertyChangeSupport(this);
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
	 * Get the column of the location of this square
	 * @return The column of this square (aka x coordinate)
	 */
	public int getCol() {
		return this.location.getCol();
	}
	
	/**
	 * Get the location of this square
	 * @return The GridPoint that represents the location of this square
	 */
	public GridPoint getLocation() {
		return this.location;
	}
	
	public void setLocation(GridPoint newLocation) {
		this.location = newLocation;
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

	@Override
	public String toString() {
		return "Token [location=" + location + ", name=" + name + ", pieceType=" + pieceType + ", support=" + support
				+ "]";
	}

	
}
