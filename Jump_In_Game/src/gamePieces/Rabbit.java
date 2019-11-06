package gamePieces;

/** 
 * The rabbit class is used to create rabbits of a given name with given coordinates.
 * @author Mika Argyle
 */

public class Rabbit extends Square {
	
	private RabbitColour colour;
	
	public Rabbit(int row, int col, String str) {
		super(row, col, str);
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
