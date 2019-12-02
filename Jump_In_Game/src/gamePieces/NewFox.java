package gamePieces;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The NewFox class has a location for the head and the tail, and the direction the fox is facing. It also contains methods
 * for constructing instances of itself, and methods to determine where it is, where it can be, and to actually move it.
 */

public class NewFox  implements PropertyChangeListener,Serializable  {
	
	private Token head;
	private Token tail;
	private Direction orientation;
	
	private static final int MIN_ROW = 0;
	private static final int MAX_ROW = 4;
	private static final int MIN_COL = 0;
	private static final int MAX_COL = 4;
	
	/**Creates a NewFox object with a head location, a direction (North, East, South, West) and a name.
	*/
	public NewFox(GridPoint head, Direction orientation, String name) {
		
		
		GridPoint tailLocation = null;
		
		if (orientation.equals(Direction.NORTH)) {
			tailLocation = new GridPoint(head.getRow()+1, head.getCol());
		} else if (orientation.equals(Direction.SOUTH)) {
			tailLocation = new GridPoint(head.getRow()-1, head.getCol());
		} else if (orientation.equals(Direction.EAST)) {
			tailLocation = new GridPoint(head.getRow(), head.getCol()-1);
		} else if (orientation.equals(Direction.WEST)) {
			tailLocation = new GridPoint(head.getRow(), head.getCol()+1);
		}
		
		this.head = new Token(head, PieceType.FOX);
		this.head.setName(name);
		this.tail = new Token(tailLocation, PieceType.FOX);
		this.tail.setName(name);
		this.orientation = orientation;
	}
	
	/**Moves the head to the specified location, if valid, and positions the tail accordingly based on this new location and current orientation.
	 * @param  newloc  The new GridPoint location of the head.
	 */
	public void moveHead(GridPoint newLoc) {
		// TODO: add checks
		if (this.orientation == Direction.NORTH) {
			this.head.move(newLoc);
			this.tail.move(new GridPoint(newLoc.getRow()+1, newLoc.getCol()));
		} else if (this.orientation == Direction.SOUTH) {
			this.head.move(newLoc);
			this.tail.move(new GridPoint(newLoc.getRow()-1, newLoc.getCol()));
		} else if (this.orientation == Direction.EAST) {
			this.head.move(newLoc);
			this.tail.move(new GridPoint(newLoc.getRow(), newLoc.getCol()-1));
		} else if (this.orientation == Direction.WEST) {
			this.head.move(newLoc);
			this.tail.move(new GridPoint(newLoc.getRow(), newLoc.getCol()+1));
		} 
	}
	
	/**
	 * Get the list of all valid moves that can be made for the head of this fox
	 * @return ArrayList<GridPoint> of all valid moves for the fox head
	 */
	public ArrayList<GridPoint> getValidMoveLocations() {
		ArrayList<GridPoint> validMoveLocations = new ArrayList<GridPoint>();
		
		if (this.orientation == Direction.NORTH) {
			for (int i = MIN_ROW; i <= MAX_ROW - 1; ++i) {
				validMoveLocations.add(new GridPoint(i, this.head.getCol()));
			}
		} else if (this.orientation == Direction.SOUTH) {
			for (int i = MIN_ROW + 1; i <= MAX_ROW; ++i) {
				validMoveLocations.add(new GridPoint(i, this.head.getCol()));
			}
		} else if (this.orientation == Direction.EAST) {
			for (int i = MIN_COL; i <= MAX_COL - 1; ++i) {
				validMoveLocations.add(new GridPoint(i, this.head.getCol()));
			}
		} else if (this.orientation == Direction.WEST) {
			for (int i = MIN_COL + 1; i <= MAX_COL; ++i) {
				validMoveLocations.add(new GridPoint(i, this.head.getCol()));
			}
		}
		
		// Remove the current head location from the list
		validMoveLocations.remove(this.head.getLocation());
		
		return validMoveLocations;
	}
	
	/**
	 * Check if the given location is a valid location for the head of this fox
	 * @param newHeadLocation the new location for the head of the fox
	 * @return true if it is a valid location
	 */
	public boolean isValidMoveLocation(GridPoint newHeadLocation) {
		if (this.orientation == Direction.NORTH) {
			if (newHeadLocation.getCol() == this.getHead().getCol()) {
				if (newHeadLocation.getRow() >= MIN_ROW && newHeadLocation.getRow() <= MAX_ROW - 1) {
					return true;
				}
			}
		} else if (this.orientation == Direction.SOUTH) {
			if (newHeadLocation.getCol() == this.getHead().getCol()) {
				if (newHeadLocation.getRow() >= MIN_ROW + 1 && newHeadLocation.getRow() <= MAX_ROW) {
					return true;
				}
			}
		} else if (this.orientation == Direction.EAST) {
			if (newHeadLocation.getRow() == this.getHead().getRow()) {
				if (newHeadLocation.getCol() >= MIN_COL + 1 && newHeadLocation.getCol() <= MAX_COL) {
					return true;
				}
			}
		} else if (this.orientation == Direction.EAST) {
			if (newHeadLocation.getRow() == this.getHead().getRow()) {
				if (newHeadLocation.getCol() >= MIN_COL && newHeadLocation.getCol() <= MAX_COL - 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Get the location of this fox's tail if the head was moved to the location specified in the parameter
	 * @param newTheoreticalHeadLocation the new theoretical head location to test
	 * @param foxOrientation the current orientation of the fox
	 * @return the new theoretical tail location
	 */
	public static final GridPoint getTheoreticalNewTailLocation(GridPoint newTheoreticalHeadLocation, Direction foxOrientation) {
		if (foxOrientation.equals(Direction.EAST)) {
			return new GridPoint(newTheoreticalHeadLocation.getRow(), newTheoreticalHeadLocation.getCol()-1);
		} else if (foxOrientation.equals(Direction.WEST)) {
			return new GridPoint(newTheoreticalHeadLocation.getRow(), newTheoreticalHeadLocation.getCol()+1);
		} else if (foxOrientation.equals(Direction.NORTH)) {
			return new GridPoint(newTheoreticalHeadLocation.getRow()+1, newTheoreticalHeadLocation.getCol());
		} else if (foxOrientation.equals(Direction.SOUTH)) {
			return new GridPoint(newTheoreticalHeadLocation.getRow()-1, newTheoreticalHeadLocation.getCol());
		}
		return null;
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the head
	 */
	public Token getHead() {
		return head;
	}

	/**
	 * @param head the head to set
	 */
	public void setHead(Token head) {
		this.head = head;
	}

	/**
	 * @return the tail
	 */
	public Token getTail() {
		return tail;
	}

	/**
	 * @param tail the tail to set
	 */
	public void setTail(Token tail) {
		this.tail = tail;
	}

	/**
	 * @return the orientation
	 */
	public Direction getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation the orientation to set
	 */
	public void setOrientation(Direction orientation) {
		this.orientation = orientation;
	}
	

	@Override
	public String toString() {
		return "NewFox [head=" + head + ", tail=" + tail + ", orientation=" + orientation + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
    	if(o == null) return false;
    	if(!(o instanceof NewFox))return false;
    	NewFox r = (NewFox) o;
    	
    	return (r.getHead().getLocation().equals(this.getHead().getLocation())
    			&& r.getHead().getPieceType().equals(this.getHead().getPieceType())
    			&& r.getHead().getName().equals(this.getHead().getName())
    			&& r.getOrientation().equals(this.getOrientation()));
	}
	
}
