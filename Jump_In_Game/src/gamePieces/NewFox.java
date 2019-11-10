package gamePieces;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewFox implements PropertyChangeListener {
	private PointSquare head;
	private PointSquare tail;
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
		
		
		this.head = new PointSquare(head, PieceType.FOX);
		this.tail = new PointSquare(tail, PieceType.FOX);
		this.orientation = orientation;
	}
	
	public NewFox(GridPoint head, Direction orientation) {
		
		if (VALID_FOX_LOCATIONS.contains(head) == false) {
			throw new IllegalArgumentException("Head is not at a valid location");
		}
		
		GridPoint tailLocation = null;

		if (orientation.equals(Direction.HORIZONTAL)) {
			if (VALID_FOX_LOCATIONS.contains(new GridPoint(head.getRow()-1, head.getCol()))) {
				tailLocation = new GridPoint(head.getRow()-1, head.getCol());
			} else if (VALID_FOX_LOCATIONS.contains(new GridPoint(head.getRow()+1, head.getCol()))) {
				tailLocation = new GridPoint(head.getRow()+1, head.getCol());
			} else {
				throw new IllegalArgumentException("No valid location for the tail piece in a horizontal orientation with head at " + head.toString());
			}
		} else if (orientation.equals(Direction.VERTICAL)) {
			if (VALID_FOX_LOCATIONS.contains(new GridPoint(head.getRow(), head.getCol()-1))) {
				tailLocation = new GridPoint(head.getRow(), head.getCol()-1);
			} else if (VALID_FOX_LOCATIONS.contains(new GridPoint(head.getRow(), head.getCol()+1))) {
				tailLocation = new GridPoint(head.getRow(), head.getCol()+1);
			} else {
				throw new IllegalArgumentException("No valid location for the tail piece in a vertical orientation with head at " + head.toString());
			}
		}
		
		
		this.head = new PointSquare(head, PieceType.FOX);
		this.tail = new PointSquare(tailLocation, PieceType.FOX);
		this.orientation = orientation;
	}
	
	public void moveHead(GridPoint newLoc) {
		if (this.orientation == Direction.VERTICAL) {
			if (head.getColumn() != newLoc.getCol()) {
				throw new IllegalArgumentException("Head must remain in the current column");
			}
		} else if (this.orientation == Direction.HORIZONTAL) {
			if (head.getRow() != newLoc.getRow()) {
				throw new IllegalArgumentException("Head must remain in the current row");
			}
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the head
	 */
	public PointSquare getHead() {
		return head;
	}

	/**
	 * @param head the head to set
	 */
	public void setHead(PointSquare head) {
		this.head = head;
	}

	/**
	 * @return the tail
	 */
	public PointSquare getTail() {
		return tail;
	}

	/**
	 * @param tail the tail to set
	 */
	public void setTail(PointSquare tail) {
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

	
	
	
	
}
