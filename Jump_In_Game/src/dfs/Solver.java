package dfs;

import gamePieces.Direction;
import gamePieces.GridPoint;
import gamePieces.PieceType;
import mvc.controller.TilePlayBoard;

public class Solver {
	private boolean[][] v1, v2, v3; //show where rabbits have visited
	private TilePlayBoard board;
	private Node head;
	private Node cur;
	private Direction[] direcArray = new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
	
	
	public Solver(TilePlayBoard board) {
		this.board = board;
		
		head = new Node(this.board, null, new GridPoint(0,0), null, null);
		cur = head;
		
		v1 = new boolean[5][5];
		v2 = new boolean[5][5];
		v3 = new boolean[5][5];
		
		
		for(int r=1; r<5; r++) {
			for(int c=0; c<5; c++) {
				if(board.getBoard().getTileAt(r, c).getToken() != null) {
					if(board.getBoard().getTileAt(r, c).getToken().getPieceType().equals(PieceType.MUSHROOM) || 
							board.getBoard().getTileAt(r, c).getToken().getPieceType().equals(PieceType.RABBIT)){
						v1[r][c] = true;
						v2[r][c] = true;
						v3[r][c] = true;
					}
					else {
						v1[r][c] = false;
						v2[r][c] = false;
						v3[r][c] = false;
					}
				}
					
			}
		}
		
		tranverse(head);
	}
	
	private void tranverse(Node node) {
		if(node.getBoard().isWin()) return;
				
		for(int i=0; i<board.getRabbitNum(); i++) {
			
			int rabbitR = node.getBoard().getRabbit(i).getRow();
			int rabbitC = node.getBoard().getRabbit(i).getCol();
			
			int foxNum = node.getBoard().getFoxNum();
			
			TilePlayBoard curBoard = node.getBoard();
			
			for(Direction direc: direcArray) {
			//check if rabbit can jump to north and whether it has already visited that location
				if(direc.equals(Direction.NORTH)) {
					if(curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.NORTH) != null &&
							!v1[curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.NORTH).getRow()]
									[curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.NORTH).getCol()]) {
						
						System.out.println("N");
					
						int row = curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.NORTH).getRow();
						int col = curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.NORTH).getCol();
					
						v1[row][col] = true;
						curBoard.moveRabbit(curBoard.getRabbit(i), new GridPoint(row,col));
						Node next = new Node(curBoard, "rabbit"+i , Direction.NORTH, cur, null);
						cur.setNext(next);
						cur = next;
						tranverse(next);
					}
				
					else if(foxNum != 0 && rabbitR != 0 &&
							curBoard.getFoxAtLocation(new GridPoint(rabbitR-1, rabbitC))!= null) {
						for(int j=0; j<2; j++) {
							if(curBoard.testValidFoxMove(curBoard.getFox(j), new GridPoint(rabbitR-1, rabbitC))) {
								curBoard.moveFox(curBoard.getFox(j), new GridPoint(rabbitR-1, rabbitC));
								Node next = new Node(curBoard, "fox"+j, new GridPoint(rabbitR-1, rabbitC), cur, null);
								cur.setNext(next);
								cur = next;
								tranverse(next);
							}
						}
					}
					
					else continue;
				}

			
			/*else {
				Node temp = node.getPrevNode();
				temp.removeNextNode();
				tranverse(temp);
			}*/
			
			//south check
				else if(direc.equals(Direction.SOUTH)) {
					if(curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.SOUTH) != null &&
							!v1[curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.SOUTH).getRow()]
									[curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.SOUTH).getCol()]) {
					
						System.out.println("S");
					
						int row = curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.SOUTH).getRow();
						int col = curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.SOUTH).getCol();
					
						v1[row][col] = true;
						curBoard.moveRabbit(curBoard.getRabbit(i), new GridPoint(row,col));
						Node next = new Node(curBoard, "rabbit"+i, Direction.SOUTH, cur, null);
						cur.setNext(next);
						tranverse(next);
					}
					else if(foxNum != 0 && rabbitR != 0 &&
							curBoard.getFoxAtLocation(new GridPoint(rabbitR+1, rabbitC))!= null) {
						for(int j=0; j<2; j++) {
							if(curBoard.testValidFoxMove(curBoard.getFox(j), new GridPoint(rabbitR+1, rabbitC))) {
								curBoard.moveFox(curBoard.getFox(j), new GridPoint(rabbitR+1, rabbitC));
								Node next = new Node(curBoard, "fox"+j, new GridPoint(rabbitR+1, rabbitC), cur, null);
								cur.setNext(next);
								cur = next;
								tranverse(next);
							}
						}
					}
					else continue;
				}
				
				/*else {
					Node temp = node.getPrevNode();
					temp.removeNextNode();
					tranverse(temp);
				}*/
				
			
			//east check
				else if(direc.equals(Direction.EAST)) {
					if(curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.EAST) != null &&
							!v1[curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.EAST).getRow()]
									[curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.EAST).getCol()]) {
					
						System.out.println("E");
					
						int row = curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.EAST).getRow();
						int col = curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.EAST).getCol();

						v1[row][col] = true;
						curBoard.moveRabbit(curBoard.getRabbit(i), new GridPoint(row,col));
						Node next = new Node(curBoard, "rabbit"+i, Direction.EAST, cur, null);
						cur.setNext(next);
						tranverse(next);
					}
					else if(foxNum != 0 && rabbitR != 0 &&
							curBoard.getFoxAtLocation(new GridPoint(rabbitR, rabbitC+1))!= null) {
						for(int j=0; j<2; j++) {
							if(curBoard.testValidFoxMove(curBoard.getFox(j), new GridPoint(rabbitR, rabbitC+1))) {
								curBoard.moveFox(curBoard.getFox(j), new GridPoint(rabbitR, rabbitC+1));
								Node next = new Node(curBoard, "fox"+j, new GridPoint(rabbitR, rabbitC+1), cur, null);
								cur.setNext(next);
								cur = next;
							tranverse(next);
							}
						}
					}
					else continue;
				}
				
				/*else {
					Node temp = node.getPrevNode();
					temp.removeNextNode();
					tranverse(temp);
				}*/
				
			//west check
				else if(direc.equals(Direction.WEST)) {
					if(node.getBoard().getNearestJumpPoint(node.getBoard().getRabbit(i), Direction.WEST) != null &&
							!v1[curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.WEST).getRow()]
									[curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.WEST).getCol()]) {
						
						System.out.println("W");
						
						int row = curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.WEST).getRow();
						int col = curBoard.getNearestJumpPoint(curBoard.getRabbit(i), Direction.WEST).getCol();
						
						v1[row][col] = true;
						curBoard.moveRabbit(curBoard.getRabbit(i), new GridPoint(row,col));
						Node next = new Node(curBoard, "rabbit"+i, Direction.WEST, cur, null);
						cur.setNext(next);
						tranverse(next);
					}
					else if(foxNum != 0 && rabbitR != 0 &&
							curBoard.getFoxAtLocation(new GridPoint(rabbitR, rabbitC-1))!= null) {
						for(int j=0; j<2; j++) {
							if(curBoard.testValidFoxMove(curBoard.getFox(j), new GridPoint(rabbitR, rabbitC-1))) {
								curBoard.moveFox(curBoard.getFox(j), new GridPoint(rabbitR, rabbitC-1));
								Node next = new Node(curBoard, "fox"+j, new GridPoint(rabbitR, rabbitC-1), cur, null);
								cur.setNext(next);
								cur = next;
								tranverse(next);
							}
						}
					}
					else continue;
					
				}
			}	
			
			if(node.hasPrev()) {
				Node temp = node.getPrevNode();
				temp.removeNextNode();
				tranverse(temp);
			}
			else {
				node.removeNextNode();
				return;
			}
		}	
	}
	
	public String getSolvent() {
		cur = head;
		String str = "";
		
		while(cur.hasNext()) {
			cur = cur.getNextNode();
			str += cur.toString();
		}
		
		return str;
	}
	
	public static void main(String[] args) {
		TilePlayBoard board = new TilePlayBoard();
		Solver s = new Solver(board);
		System.out.println(s.getSolvent());
	}
	
	
}
