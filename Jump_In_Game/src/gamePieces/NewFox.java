package gamePieces;

import java.awt.Point;

public class NewFox {
	private Square head;
	private Square tail;
	private Direction orientation;
	private int fixedCoordinate;
	
	public NewFox(Direction orientation, int fixedCoordinate, Point head, Point tail) {
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
		
		this.head = new Square(head.y, head.x, PieceType.FOX);
		this.tail = new Square(tail.y, tail.x, PieceType.FOX);
		this.orientation = orientation;
	}
	
	public void moveHead(Point newLoc) {
		if (this.orientation == Direction.VERTICAL) {
			if (head.getColumn() != newLoc.x) {
				throw new IllegalArgumentException("Head must remain in the current column");
			}
		} else if (this.orientation == Direction.HORIZONTAL) {
			if (head.getRow() != newLoc.y) {
				throw new IllegalArgumentException("Head must remain in the current row");
			}
		}
		
		if ()
	}

	/**
	 * @return the head
	 */
	public Square getHead() {
		return head;
	}

	/**
	 * @param head the head to set
	 */
	public void setHead(Square head) {
		this.head = head;
	}

	/**
	 * @return the tail
	 */
	public Square getTail() {
		return tail;
	}

	/**
	 * @param tail the tail to set
	 */
	public void setTail(Square tail) {
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
