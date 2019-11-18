package gamePieces;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 *
 /*
public class Token implements Serializable {
	/**
	 * The token class contains the location, name, and type of piece for each token, and the methods to get and set those variables.
	 */
	private static final long serialVersionUID = 7898018235259814734L;
	
	protected GridPoint location;
	private String name;
	private PieceType pieceType;
	private PropertyChangeSupport support;

	
	/**
	 * Constructor for Token
	 * @param location Point object that represents the location on the grid of the token
	 * @param pieceType the type of piece that this token is
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
	 * Copy constructor for Token
	 * @param token the token to create a deep copy of
	 */
	public Token(Token token) {
		this.location = new GridPoint(token.getLocation());
		this.name = token.getName();
		this.pieceType = token.pieceType;
		this.support = new PropertyChangeSupport(this);
	}
	
	/**
	 * Add a new property change listener
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
	 * Get the row of this token
	 * @return the row of this token (aka y coordinate)
	 */
	public int getRow() {
		return this.location.getRow();
	}

	/**
	 * Get the column of the location of this token
	 * @return The column of this token (aka x coordinate)
	 */
	public int getColumn() {
		return this.location.getCol();
	}
	
	/**
	 * Get the column of the location of this token
	 * @return The column of this token (aka x coordinate)
	 */
	public int getCol() {
		return this.location.getCol();
	}
	
	/**
	 * Get the location of this token
	 * @return The GridPoint that represents the location of this token
	 */
	public GridPoint getLocation() {
		return this.location;
	}
	
	public void setLocation(GridPoint newLocation) {
		this.location = newLocation;
	}

	/**
	 * Set the name of the object in the token
	 * @param name The name of the object for this token
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the name of the object in the token
	 * @return the name of the object in the token
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
		support.firePropertyChange("location", oldLocation, newLocation); // Fire property changes when the token is moved
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pieceType == null) ? 0 : pieceType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pieceType != other.pieceType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Token [location=" + location + ", name=" + name + ", pieceType=" + pieceType + "]";
	}

	
}
