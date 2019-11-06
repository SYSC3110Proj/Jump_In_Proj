package gamePieces;

/** 
 * The fox class is used to keep track of the coordinates and get the direction of a fox. Foxes are constructed with co-ords, direction,
 * and optionally a name.
 * @author Mika Argyle
 */

public class Fox extends Square {
	
	private Direction direction;
	
	/**
	 * Constructor for fox object
	 * @param row The row of the fox (aka y coordinate)
	 * @param col The column of the fox (aka x coordinate)
	 * @param direction The direction that the fox is facing
	 * @param name The name of the fox
	 */
	public Fox(int row, int col, Direction direction, String name) {
		super(row, col, name);
		super.setPieceType(PieceType.FOX);
		this.direction = direction;
	}
	
	/**
	 * Constructor for fox object
	 * @param row The row of the fox (aka y coordinate)
	 * @param col The column of the fox (aka x coordinate)
	 * @param direction The direction that the fox is facing
	 */
	public Fox(int row, int col, Direction direction) {
		super(row, col);
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
