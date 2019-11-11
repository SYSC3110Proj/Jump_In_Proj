package gamePieces;

/** 
 * The rabbit class is used to create rabbits of a given name with given coordinates.
 * @author Mika Argyle
 */

public class Rabbit extends Token {
	
	private RabbitColour colour;
	
	public Rabbit(GridPoint location, String str) {
		super(location, PieceType.RABBIT);
		super.setPieceType(PieceType.RABBIT);
		
		// Set the rabbit's colour
		if (str.equals("rabbit1")) {
			this.colour = RabbitColour.BROWN;
		} else if (str.equals("rabbit2")) {
			this.colour = RabbitColour.WHITE;
		} else if (str.equals("rabbit3")) {
			this.colour = RabbitColour.GREY;
		} 
	}
	
	/**
	 * Test if the token is at on a hole tile
	 * @return True if the token is on a hole tile
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
	 * @return the colour
	 */
	public RabbitColour getColour() {
		return colour;
	}

	/**
	 * @param colour the colour to set
	 */
	public void setColour(RabbitColour colour) {
		this.colour = colour;
	}	
}
