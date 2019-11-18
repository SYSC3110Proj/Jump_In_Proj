package gamePieces;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NewFox implements PropertyChangeListener {
	
	private Token head;
	private Token tail;
	private Direction orientation;
	
	private static final int MIN_ROW = 0;
	private static final int MAX_ROW = 4;
	private static final int MIN_COL = 0;
	private static final int MAX_COL = 4;
	
	
	// List of all valid GridPoints that a fox can be placed in
	public static final List<GridPoint> VALID_FOX_LOCATIONS = Collections.unmodifiableList(
			new ArrayList<GridPoint>() {
				private static final long serialVersionUID = 4511043816701717338L;
			{
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
	
	public static final List<GridPoint> FOX_BORDER_LOCATIONS = Collections.unmodifiableList(
			new ArrayList<GridPoint>() {
				private static final long serialVersionUID = -1912311069171602663L;
			{
				add(new GridPoint(0,1));
				add(new GridPoint(0,3));
				add(new GridPoint(1,0));
				add(new GridPoint(1,4));
				add(new GridPoint(3,0));
				add(new GridPoint(3,4));
				add(new GridPoint(4,1));
				add(new GridPoint(4,3));
			}}) ;
	
	public NewFox(GridPoint head, GridPoint tail, String name) {
		if (!VALID_FOX_LOCATIONS.contains(head)) {
			throw new IllegalArgumentException("Head is not at a valid location");
		}
		
		if (!VALID_FOX_LOCATIONS.contains(tail)) {
			throw new IllegalArgumentException("tail is not at a valid location");
		}
		
		if ((int) head.distance(tail) != 1) {
			throw new IllegalArgumentException("Head and tail must be next to each other");
		}
		
		this.head = new Token(head, PieceType.FOX);
		this.head.setName(name);
		this.tail = new Token(tail, PieceType.FOX);
		this.tail.setName(name);
	}
	
	
	public NewFox(GridPoint head, Direction orientation, String name) {
		
		if (!VALID_FOX_LOCATIONS.contains(head)) {
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
		
		if (!VALID_FOX_LOCATIONS.contains(tailLocation)) {
			throw new IllegalArgumentException("Tail cannot be located at " + tailLocation.toString());
		}
		
		this.head = new Token(head, PieceType.FOX);
		this.head.setName(name);
		this.tail = new Token(tailLocation, PieceType.FOX);
		this.tail.setName(name);
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
	
	
	/**
	 * @return the validFoxLocations
	 */
	public static List<GridPoint> getValidFoxLocations() {
		return VALID_FOX_LOCATIONS;
	}

	/**
	 * @return the foxBorderLocations
	 */
	public static List<GridPoint> getFoxBorderLocations() {
		return FOX_BORDER_LOCATIONS;
	}

	@Override
	public String toString() {
		return "NewFox [head=" + head + ", tail=" + tail + ", orientation=" + orientation + "]";
	}
}
