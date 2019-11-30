package gamePieces;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Tile  implements Serializable {
	private final GridPoint location;
	private boolean isHole;
	private Token token;
	private PropertyChangeSupport support;
	
	/**
	 * Constructor for tile
	 * @param location the location on the game board of the tile
	 * @param isHole if this tile is a hole or not
	 * @param token the token that occupies this space
	 */
	public Tile(GridPoint location, Token token, boolean isHole) {
		super();
		this.location = location;
		this.isHole = isHole;
		this.token = token;
		this.support = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}

	/**
	 * @return the location
	 */
	public GridPoint getLocation() {
		return location;
	}

	/**
	 * @return the isHole
	 */
	public boolean isHole() {
		return isHole;
	}

	/**
	 * @param isHole the isHole to set
	 */
	public void setHole(boolean isHole) {
		
		this.isHole = isHole;
		
	}

	/**
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * @param newToken the token to set
	 */
	public void setToken(Token newToken) {
		support.firePropertyChange("token", this.token, newToken);
		this.token = newToken;
		
		if (this.token != null) {
			this.token.setLocation(this.location);
		}
	}

	@Override
	public String toString() {
		return "Tile [location=" + location + ", isHole=" + isHole + ", token=" + token + "]";
	}
	
	/**
	 * Test if the tile is occupied with a token
	 * @return true if the tile contains a token
	 */
	public boolean isOccupied() {
		if (this.token != null) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
