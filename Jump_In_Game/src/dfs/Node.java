package dfs;

import java.util.LinkedList;

import gamePieces.Direction;
import gamePieces.GridPoint;
import mvc.controller.TilePlayBoard;

public class Node{
	private TilePlayBoard board;
	private String name;
	private Direction direc;
	private GridPoint point;
	private Node prevNode;
	private Node nextNode;
	
	public Node(TilePlayBoard board, String name, Direction direc, Node prev, Node next) {
		this.board = board;
		this.name = name;
		this.direc = direc;
		this.prevNode = prev;
		this.nextNode = next;
	}
	
	public Node(TilePlayBoard board, String name, GridPoint point, Node prev, Node next) {
		this.board = board;
		this.name = name;
		this.prevNode = prev;
		this.nextNode = next;
		this.point = point;
	}
	
	public void setNext(Node node) {
		nextNode = node;
	}

	public Node getPrevNode() {
		return prevNode;
	}
	
	public TilePlayBoard getBoard() {
		return board;
	}
	
	public void removeNextNode() {
		this.nextNode = null;
	}
	
	public Node getNextNode() {
		return nextNode;
	}
	
	public boolean hasNext() {
		return nextNode != null;
	}
	
	public boolean hasPrev() {
		return prevNode != null;
	}
	
	public String toString() {
		if(name != null) {
			if(direc == null) {
				return "/n" + name + " to point " + point.getRow() + "," + point.getCol();
			}
			else if(direc.equals(Direction.NORTH)) {
				return "/n" + name + " ¡ü";
			}
			else if(direc.equals(Direction.SOUTH)) {
				return "/n" + name + " ¡ý";
			}
			else if(direc.equals(Direction.EAST)) {
				return "/n" + name + " ¡ú";
			}
			else if(direc.equals(Direction.WEST)) {
				return "/n" + name + " ¡û";
			}
		}
		return null;
	}

}
