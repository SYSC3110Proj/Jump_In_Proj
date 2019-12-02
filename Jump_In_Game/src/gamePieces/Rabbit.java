package gamePieces;

import java.io.Serializable;

/** 
 * The rabbit class is used to create rabbits of a given name with given coordinates.
 * @author Mika Argyle
 */

public class Rabbit extends Token  implements Serializable {
	
	private static final long serialVersionUID = 4304558624413697278L;
	
		
	/**
	 * Constructor for rabbit
	 * @param location the starting location of the rabbit
	 * @param str the name of the rabbit
	 */
	public Rabbit(GridPoint location, String str) {
		super(location, PieceType.RABBIT);
		super.setPieceType(PieceType.RABBIT);
		super.setName(str);
		
		
	}
	
	
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

	
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
    	if(o == null) return false;
    	if(!(o instanceof Rabbit))return false;
    	Rabbit r = (Rabbit) o;
    	return (r.getName().equals(this.getName())&& r.getLocation().equals(this.location));
	}
}
