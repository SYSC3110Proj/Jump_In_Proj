package tree;

import gamePieces.GridPoint;
import gamePieces.Token;

public class MovementData {
	
	private Token token;
	private GridPoint newLocation;
	
	/**
	 * Constructor for MovementData class
	 * @param token
	 * @param newLocation
	 */
	public MovementData(Token token, GridPoint newLocation) {
		this.token = new Token(token);
		this.newLocation = newLocation;
	}

	/**
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(Token token) {
		this.token = token;
	}

	/**
	 * @return the newLocation
	 */
	public GridPoint getNewLocation() {
		return newLocation;
	}

	/**
	 * @param newLocation the newLocation to set
	 */
	public void setNewLocation(GridPoint newLocation) {
		this.newLocation = newLocation;
	}
	
	

}
