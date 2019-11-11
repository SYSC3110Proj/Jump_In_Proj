package gamePieces;

public class Tile {
	private final GridPoint location;
	private boolean isHole;
	private Token token;
	
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
	 * @param token the token to set
	 */
	public void setToken(Token token) {
		this.token = token;
		
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
