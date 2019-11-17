package dfs;

import java.util.ArrayList;
import java.util.Stack;

import gamePieces.Direction;
import gamePieces.GridPoint;
import mvc.controller.Record;
import mvc.controller.TilePlayBoard;

public class DFS {
	
	private DFSNode head;
	private TilePlayBoard temp;
	private int level;
	private int state;
	
	public DFS(TilePlayBoard board) {
		this.temp = new TilePlayBoard(board);
		this.level = 6;
		this.state = 0;
		//this.undoNum = 0;
		
		ArrayList<Choice> choices = new ArrayList<Choice>();
		
		for(int i=0; i<board.getRabbitNum(); i++) {
			for(int j=0; j<4; j++) {
				choices.add(new Choice(i,j));
			}
		}
		for(int i=board.getRabbitNum(); i<board.getFoxNum()+board.getRabbitNum();i++) {
			for(int j=1; j<4; j++) {
				choices.add(new Choice(i,j));
			}
		}
		
		head = new DFSNode(-1, -1, null, choices);
		head.setNextNode();
		search(head);
		
		
		getSolvent();		
	}

	private void search(DFSNode node) {
		if(temp.isWin()) return;
		
		if(!node.hasOtherWayToGo() || state==level) {
			if(!node.hasPrev())return;
			else {
				node = node.getPrev();
				node.setNextNode();
				temp.undoAndClear();
				state--;
				search(node);
			}
		}
		
		else {
			node = node.getNext();
			int piece = node.getName();
			int direc = node.getDirec();
			
			if(piece < temp.getRabbitNum()) {
				GridPoint point = temp.getNearestJumpPoint(temp.getRabbit(piece), Direction.values()[direc]); 
				if(point != null) {//can move rabbit
					temp.moveRabbit(temp.getRabbit(piece), point);
					if(node.hasOtherWayToGo()) {
						node.setNextNode();
						state++;
					}
					else {//can move rabbit but there is no next to go
						node = node.getPrev();
						node.setNextNode();
						temp.undoAndClear();
					}
				}
				else {//unable to move rabbit
					node = node.getPrev();
					node.setNextNode();
				}
			}
			else {//move fox
				int fox = piece-temp.getRabbitNum();
				Direction orientation = temp.getFox(fox).getOrientation();
				
				if(orientation.equals(Direction.NORTH) || orientation.equals(Direction.SOUTH)) {
					int col = temp.getFox(fox).getHead().getCol();
					GridPoint point =  new GridPoint(direc, col);
					//if(temp.testValidFoxMove(temp.getFox(fox), point)){
					temp.moveFox(temp.getFox(fox), point);
					if(node.hasOtherWayToGo()) {//can move fox and has next
						state++;
						node.setNextNode();
							//cur = cur.getNext();
					}
					else {//can move fox but not has next
						node = node.getPrev();
						node.setNextNode();
						temp.undoAndClear();
					}
					//}
					/*else {//cannot move fox
						node = node.getPrev();
						node.setNextNode();
					}*/
				}
				else if(orientation.equals(Direction.EAST) || orientation.equals(Direction.WEST)) {
					int row = temp.getFox(fox).getHead().getRow();
					GridPoint point =  new GridPoint(row, direc);
					//if(temp.testValidFoxMove(temp.getFox(fox), point)){
					temp.moveFox(temp.getFox(fox), point);
					if(node.hasOtherWayToGo()) {//can move fox and has next
						state++;
						node.setNextNode();
							//cur = cur.getNext();
					}
					else {//can move fox but not has next
						node = node.getPrev();
						node.setNextNode();
						temp.undoAndClear();
					}
					/*}
					else {//cannot move fox
						node = node.getPrev();
						node.setNextNode();
					}*/
				}
			}
			search(node);
		}
		
	}
	
	
	/*private void DFSSearch() {
		
		if(temp.isWin())return;
		
		if(!cur.hasNext()) {
			if(!cur.hasPrev()) return;
			else {
				cur = cur.getPrev();
				cur.removeNext();
				System.out.println("return & undo");
				temp.undo();
				DFSSearch();
			}
		}
		
		else {
			cur = cur.getNext();
			
			int piece = cur.getName();
			int direc = cur.getDirec();
			
			if(piece < temp.getRabbitNum()) {
				GridPoint point = temp.getNearestJumpPoint(temp.getRabbit(piece), Direction.values()[direc]); 
				if(point != null) {//can move rabbit
					temp.moveRabbit(temp.getRabbit(piece), point);
					if(cur.hasNext()) {
						System.out.println("move rabbit" + piece + "," + Direction.values()[direc]);
						//cur = cur.getNext();
					}
					else {
						//System.out.println("undo rabbit");
						cur = cur.getPrev();
						cur.removeNext();
						temp.undo();
						System.out.println("no more next, undo and return to rabbit" + cur.getName() + ":" + Direction.values()[cur.getDirec()]);
						/*cur = cur.getPrev();
						cur.removeNext();
						if(cur.hasNext()) {
							System.out.println("undo rabbit" + piece);
							cur = cur.getNext();
							temp.undo();
						}
						else {
							while(cur.hasPrev()) {
								temp.undo();
								System.out.println("undo rabbit" + piece);
								cur = cur.getPrev();
								if(cur.hasNext()) break;
							}
							cur = cur.getNext();
						}
					}
				}
				else {//unable to move rabbit
					cur = cur.getPrev();
					cur.removeNext();
					System.out.println("cannot move rabbit" + piece + ",back");
					/*if(cur.hasNext()) {
						cur = cur.getNext();
						//temp.undo();
						//System.out.println("undo rabbit" + piece);
					}
					else {
						while(cur.hasPrev()) {
							temp.undo();
							System.out.println("undo rabbit" + piece);
							cur = cur.getPrev();
							if(cur.hasNext()) break;
						}
						System.out.println(piece + "," + direc);
						cur = cur.getNext();
					}
				}
				
			}
			else {//move fox
				int fox = piece-temp.getRabbitNum();
				Direction orientation = temp.getFox(fox).getOrientation();
				
				if(orientation.equals(Direction.NORTH) || orientation.equals(Direction.SOUTH)) {
					int col = temp.getFox(fox).getHead().getCol();
					GridPoint point =  new GridPoint(direc, col);
					if(temp.testValidFoxMove(temp.getFox(fox), point)){
						temp.moveFox(temp.getFox(fox), point);
						if(cur.hasNext()) {//can move fox and has next
							System.out.println("move fox" + piece + "," + point.toString());
							//cur = cur.getNext();
						}
						else {//can move fox but not has next
							cur = cur.getPrev();
							cur.removeNext();
							System.out.println("undo fox move");
							temp.undo();
							/*if(cur.hasNext()) {
								cur = cur.getNext();
								temp.undo();
								System.out.println("undo fox");
							}
							else {
								while(cur.hasPrev()) {
									temp.undo();
									cur = cur.getPrev();
									if(cur.hasNext()) break;
								}
								cur = cur.getNext();
							}
							
						}
					}
					else {//cannot move fox
						System.out.println("cannot move fox" + fox + "and back");
						cur = cur.getPrev();
						cur.removeNext();
						/*if(cur.hasNext()) {
							cur = cur.getNext();
							//temp.undo();
						}
						else {
							while(cur.hasPrev()) {
								temp.undo();
								cur = cur.getPrev();
								System.out.println("undo fox");
								if(cur.hasNext()) break;
							}
							cur = cur.getNext();
						}
					}
				}
				else if(orientation.equals(Direction.EAST) || orientation.equals(Direction.WEST)) {
					int row = temp.getFox(fox).getHead().getRow();
					GridPoint point =  new GridPoint(row, direc);
					if(temp.testValidFoxMove(temp.getFox(fox), point)){
						temp.moveFox(temp.getFox(fox), point);
						if(cur.hasNext()) {//can move fox and has next
							System.out.println("move fox" + piece +  "," + point.toString());
							//cur = cur.getNext();
						}
						else {//can move fox but not has next
							cur = cur.getPrev();
							cur.removeNext();
							System.out.println("undo fox move");
							temp.undo();
						}
					}
					else {//cannot move fox
						System.out.println("cannot move fox" + fox + "and back");
						cur = cur.getPrev();
						cur.removeNext();
						/*if(cur.hasNext()) {
							cur = cur.getNext();
							//temp.undo();
						}
						else {
							while(cur.hasPrev()) {
								temp.undo();
								cur = cur.getPrev();
								System.out.println("undo fox");
								if(cur.hasNext()) break;
							}
							cur = cur.getNext();
						}
					}
				}
			}
			DFSSearch();
		}
		
		
			
	}*/
	
	public void getSolvent() {
		Stack<Record> stack = temp.getList();
		while(!stack.isEmpty()) {
			Record r = stack.pop();
			System.out.println(r.getPiece().getName() + ":" + r.getNextLoc().getRow() + "," + r.getNextLoc().getCol());
		}
		System.out.println(temp.isWin());
	}
	
	public static void main(String[] args) {
		TilePlayBoard game = new TilePlayBoard();
		DFS solver = new DFS(game);
		solver.getSolvent();
	}
	

}
