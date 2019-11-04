package gamePieces;

public class Fox extends Square {
	
	private Direction direction;
	
	/**
	 * Constructor for fox object
	 * @param x The x coordinate of the fox
	 * @param y The y coordinate of the fox
	 * @param direction The direction that the fox is facing
	 * @param name The name of the fox
	 */
	public Fox(int x, int y, Direction direction, String name) {
		super(x,y, name);
		this.direction = direction;
	}
	
	/**
	 * Constructor for fox object
	 * @param x The x coordinate of the fox
	 * @param y The y coordinate of the fox
	 * @param direction The direction that the fox is facing
	 */
	public Fox(int x, int y, Direction direction) {
		super(x,y);
		this.direction = direction;
	}
	
	/**
	 * Get the direction that the fox is facing
	 * @return The direction that the fox is facing
	 */
	public Direction getDirection() {
		return direction;
	}
	
}
