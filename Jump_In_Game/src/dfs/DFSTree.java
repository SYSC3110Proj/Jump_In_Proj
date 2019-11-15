package dfs;

import java.util.ArrayList;

import gamePieces.Direction;
import gamePieces.GridPoint;
import gamePieces.PieceType;
import gamePieces.Rabbit;
import mvc.controller.TilePlayBoard;

public class DFSTree {
	
	private Node head;
	private ArrayList<boolean[][]> visited;	
	
	private boolean[] foxRepeat;
	
	public DFSTree(TilePlayBoard board) {
		
		visited = new ArrayList<boolean[][]>();
		for(int i=0; i<board.getRabbitNum(); i++) {
			visited.add(new boolean[5][5]);
		}
		
		for(int r=1; r<5; r++) {
			for(int c=0; c<5; c++) {
				if(board.getBoard().getTileAt(r, c).getToken() != null) {
					if(board.getBoard().getTileAt(r, c).getToken().getPieceType().equals(PieceType.MUSHROOM) || 
							board.getBoard().getTileAt(r, c).getToken().getPieceType().equals(PieceType.RABBIT)){
						for(int i=0; i<visited.size(); i++) {
							visited.get(i)[r][c] = true;
						}
					}
					else {
						for(int i=0; i<visited.size(); i++) {
							visited.get(i)[r][c] = false;
						}
					}
				}
					
			}
		}
		
		foxRepeat = new boolean[board.getFoxNum()];
		for(int i=0; i<board.getFoxNum(); i++) {
			foxRepeat[i] = false;
		}
		
		head = new Node(board, null, visited, null, null);
		
		search(head);
		
	}
	
	private void search(Node node) {
		
		if(node.getBoard().isWin()) {
			return;
		}
		
		if(!node.hasNext()) {
			if(!node.hasPrev())return;
			else {
				while(node.hasPrev()) {
					node = node.getPrevNode();
					node.removePossibility();
					if(node.hasNext()) {
						break;
					}
				}
				visited = node.getVisited();
				search(node);
			}
		}
		
		else {
			int piece = node.getNextWay().getName();
			int direction = node.getNextWay().getDirection();
			
			TilePlayBoard copy = node.getBoard().clone();
			//int foxNum = b.getFoxNum();
			
			if(piece < copy.getRabbitNum()) {
				GridPoint point = copy.getNearestJumpPoint(copy.getRabbit(piece), Direction.values()[direction]);
				if(point != null && !visited.get(piece)[point.getRow()][point.getCol()]) {
					visited.get(piece)[point.getRow()][point.getCol()] = true;
					for(int i=0; i<copy.getFoxNum(); i++) {
						foxRepeat[i] = false;
					}
					
					copy.moveRabbit(copy.getRabbit(piece), point);
					Node next = new Node(copy, "rabbit" + piece, visited, point, node);
					node.setNext(next);
					System.out.println("r" + piece + " " + point.getRow() + point.getCol());
					search(next);
				}
				else {
					node.removePossibility();
					search(node);
				}
			}
			
			else {
				int fox = piece-copy.getRabbitNum();
				Direction dir = copy.getFox(fox).getOrientation();
				
				if(foxRepeat[fox]) {
					Node prev = node.getPrevNode();
					prev.removePossibility();
					visited = prev.getVisited();
					search(prev);
				}
				else if(dir.equals(Direction.NORTH) || dir.equals(Direction.SOUTH)) {
					int position = copy.getFox(fox).getHead().getCol();
					GridPoint point = new GridPoint(direction, position);
					if(copy.getFoxAtLocation(point) != null && copy.getFoxAtLocation(point).equals(copy.getFox(fox))) {
						node.removePossibility();
						search(node);
					}
					else {
						copy.moveFox(copy.getFox(fox), point);
						foxRepeat[fox] = true;
						Node next = new Node(copy, "fox" + fox, visited, point, node);
						node.setNext(next);
						System.out.println("f" + fox + " " + point.getRow() + point.getCol());
						search(next);
					}
					
				}
				
				else if(dir.equals(Direction.EAST) || dir.equals(Direction.WEST)){
					int position = copy.getFox(fox).getHead().getRow();
					GridPoint point = new GridPoint(position, direction);
					if(copy.getFoxAtLocation(point) != null && copy.getFoxAtLocation(point).equals(copy.getFox(fox))) {
						node.removePossibility();
						search(node);
					}
					else {
						copy.moveFox(copy.getFox(fox), point);
						foxRepeat[fox] = true;
						Node next = new Node(copy, "fox" + fox, visited, point, node);
						node.setNext(next);
						System.out.println("f"+ fox + " " + point.getRow() + point.getCol());
						search(next);
					}
				}
				else {
					Node prev = node.getPrevNode();
					prev.removePossibility();
					if(prev.hasNext()) {
						visited = prev.getVisited();
						search(prev);
					}
					else {
						while(prev.hasPrev()) {
							prev = prev.getPrevNode();
							prev.removePossibility();
							if(prev.hasNext()) {
								break;
							}
						}
						visited = prev.getVisited();
						search(prev);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		TilePlayBoard board = new TilePlayBoard();
		DFSTree s = new DFSTree(board);
	}
}
