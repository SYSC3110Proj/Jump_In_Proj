package gamePieces;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewFox implements PropertyChangeListener {
	
	private Token head;
	private Token tail;
	private Direction orientation;
	
	// List of all valid GridPoints that a fox can be placed in
	public static final List<GridPoint> VALID_FOX_LOCATIONS = Collections.unmodifiableList(
			new ArrayList<GridPoint>() {{
				add(new GridPoint(0,1));
				add(new GridPoint(0,3));
				add(new GridPoint(1,0));
				add(new GridPoint(1,1));
				add(new GridPoint(1,2));
				add(new GridPoint(1,3));
				add(new GridPoint(1,4));
				add(new GridPoint(2,1));
				add(new GridPoint(2,3));
				add(new GridPoint(3,0));
				add(new GridPoint(3,1));
				add(new GridPoint(3,2));
				add(new GridPoint(3,3));
				add(new GridPoint(3,4));
				add(new GridPoint(4,1));
				add(new GridPoint(4,3));
			}}) ;
	
	public NewFox(GridPoint head, GridPoint tail) {
		
		if (VALID_FOX_LOCATIONS.contains(head) == false) {
			throw new IllegalArgumentException("Head is not at a valid location");
		}
		
		if (VALID_FOX_LOCATIONS.contains(tail) == false) {
			throw new IllegalArgumentException("tail is not at a valid location");
		}
		
		if ((int) head.distance(tail) != 1) {
			throw new IllegalArgumentException("Head and tail must be next to each other");
		}
		
		if (orientation == Direction.VERTICAL) {
			if (head.x != tail.x) {
				throw new IllegalArgumentException("Head and tail must be in the same column");
			}
		} else if (orientation == Direction.HORIZONTAL) {
			if (head.y != tail.y) {
				throw new IllegalArgumentException("Head and tail must be in same row");
			}
		}
		
		this.head = new Token(head, PieceType.FOX);
		this.tail = new Token(tail, PieceType.FOX);
	}
	
	public NewFox(GridPoint head, Direction orientation) {
		
		if (VALID_FOX_LOCATIONS.contains(head) == false) {
			throw new IllegalArgumentException("Head is not at a valid location");
		}
		
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
		
//		if (orientation.equals(Direction.HORIZONTAL)) {
//			if (VALID_FOX_LOCATIONS.contains(new GridPoint(head.getRow()-1, head.getCol()))) {
//				tailLocation = new GridPoint(head.getRow()-1, head.getCol());
//			} else if (VALID_FOX_LOCATIONS.contains(new GridPoint(head.getRow()+1, head.getCol()))) {
//				tailLocation = new GridPoint(head.getRow()+1, head.getCol());
//			} else {
//				throw new IllegalArgumentException("No valid location for the tail piece in a horizontal orientation with head at " + head.toString());
//			}
//		} else if (orientation.equals(Direction.VERTICAL)) {
//			if (VALID_FOX_LOCATIONS.contains(new GridPoint(head.getRow(), head.getCol()-1))) {
//				tailLocation = new GridPoint(head.getRow(), head.getCol()-1);
//			} else if (VALID_FOX_LOCATIONS.contains(new GridPoint(head.getRow(), head.getCol()+1))) {
//				tailLocation = new GridPoint(head.getRow(), head.getCol()+1);
//			} else {
//				throw new IllegalArgumentException("No valid location for the tail piece in a vertical orientation with head at " + head.toString());
//			}
//		}
		
		if (VALID_FOX_LOCATIONS.contains(tailLocation) == false) {
			throw new IllegalArgumentException("Tail cannot be located at " + tailLocation.toString());
		}
		
		this.head = new Token(head, PieceType.FOX);
		this.head.setName("foxHead");
		this.tail = new Token(tailLocation, PieceType.FOX);
		this.tail.setName("foxTail");
		this.orientation = orientation;
	}
	
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
		
//		else if (this.orientation == Direction.VERTICAL) {
//			if (head.getColumn() != newLoc.getCol()) {
//				throw new IllegalArgumentException("Head must remain in the current column");
//			}
//			// TODO: add check to see if head and tail will be within board
//			
//			int verticalDisplacement = newLoc.getRow() - this.head.getRow();
//			
//			this.head.move(newLoc);
//			this.tail.move(new GridPoint(this.tail.getRow() + verticalDisplacement, this.tail.getColumn()));
//			
//		} else if (this.orientation == Direction.HORIZONTAL) {
//			if (head.getRow() != newLoc.getRow()) {
//				throw new IllegalArgumentException("Head must remain in the current row");
//			}
//			
//			// TODO: add check to see if head and tail will be within board
//			
//			int horizontalDisplacement = newLoc.getRow() - this.head.getRow();
//			
//			this.head.move(newLoc);
//			this.tail.move(new GridPoint(this.tail.getRow(), this.tail.getColumn() + horizontalDisplacement));
//		}
		
		
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

	
	
	
	
}
