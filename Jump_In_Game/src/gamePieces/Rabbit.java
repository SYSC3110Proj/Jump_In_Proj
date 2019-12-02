package gamePieces;

import java.io.Serializable;

/** 
 * The rabbit class is used to create rabbits of a given name with given coordinates.
 * @author Mika Argyle
 */

public class Rabbit extends Token  implements Serializable {
	
	private static final long serialVersionUID = 4304558624413697278L;
	private RabbitColour colour;
		
	/**
	 * Constructor for rabbit
	 * @param location the starting location of the rabbit
	 * @param str the name of the rabbit
	 */
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
		super.setName(str);
	}
	
	/**
	 * Copy constructor for Rabbit
	 * @param rabbit the rabbit to make a deep copy of
	 */
	public Rabbit(Rabbit rabbit) {
		super((Token) rabbit);
		this.colour = rabbit.colour;
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
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
    	if(o == null) return false;
    	if(!(o instanceof Rabbit))return false;
    	Rabbit r = (Rabbit) o;
    	return (r.getName().equals(this.getName())&& r.getLocation().equals(this.location));
	}
}
