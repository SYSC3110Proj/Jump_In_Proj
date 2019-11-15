package dfs;

import java.util.ArrayList;

import gamePieces.Direction;
import gamePieces.GridPoint;
import mvc.controller.TilePlayBoard;

public class Node{
	private TilePlayBoard board;
	private String name;
	private GridPoint point;
	private ArrayList<boolean[][]> visited;
	private Node prevNode;
	private Node nextNode;
	private ArrayList<Way> possibility;
	
	public Node(TilePlayBoard board, String name, ArrayList<boolean[][]> visited, GridPoint point,Node prev) {
		this.board = board;
		this.name = name;
		this.prevNode = prev;
		this.visited = (ArrayList<boolean[][]>)visited.clone();
		this.point = point;
		if(prevNode != null) prev.setNext(this);
		
		possibility = new ArrayList<Way>(board.getFoxNum()*3 + board.getRabbitNum()*4);
		for(int i=0; i<board.getRabbitNum(); i++) {
			for(int j=0; j<4; j++) {
				possibility.add(new Way(i, j));
			}
		}
		for(int i=board.getRabbitNum(); i<(board.getRabbitNum() + board.getFoxNum()); i++) {
			for(int j=0; j<3; j++) {
				possibility.add(new Way(i, j*2));
			}
		}
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
	
	public Way getNextWay() {
		return possibility.get(0);
	}
	
	public void removePossibility() {
		possibility.remove(0);
	}
	
	public Node getNextNode() {
		return nextNode;
	}
	
	public boolean[][] getVisited(int i){
		return visited.get(i);
	}
	
	public ArrayList<boolean[][]> getVisited(){
		return visited;
	}
	
	public boolean hasPrev() {
		return prevNode != null;
	}
	
	public boolean hasNext() {
		return !possibility.isEmpty();
	}
	
	public String toString() {
		if(name != null) return "/n" + name + " to point " + point.getRow() + "," + point.getCol();
		return null;
	}

}
