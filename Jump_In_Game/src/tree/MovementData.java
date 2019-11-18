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
	
	@Override
	public String toString() {
		return "MovementData [token=" + token + ", newLocation=" + newLocation + "]";
	}

	public MovementData getInverseMove() {
		MovementData inverseMove = new MovementData(new Token(this.token), new GridPoint(this.newLocation));
		
		GridPoint newStart = inverseMove.getNewLocation(); // Save the new starting location
		GridPoint newEnd = inverseMove.getToken().getLocation(); // Save the new ending location
		
		inverseMove.getToken().setLocation(newStart); // Set the new starting location for the token as the old location of the move
		inverseMove.setNewLocation(newEnd); // Set the new end location as the original location of the token
		
		return inverseMove;
	}
	

}
