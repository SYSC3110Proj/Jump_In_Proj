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
		this.level = 3;
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
		
		
		//getSolvent();		
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
				System.out.println("no other way to go, back");
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
						//System.out.println("move rabbit"+piece + "to" + Direction.values()[direc]);
						node.setNextNode();
						state++;
					}
					else {//can move rabbit but there is no next to go
						node = node.getPrev();
						node.setNextNode();
						temp.undoAndClear();
						//System.out.println("move rabbit"+piece + "back from" + Direction.values()[direc]);
					}
				}
				else {//unable to move rabbit
					node = node.getPrev();
					node.setNextNode();
					//System.out.println("cannot move rabbit"+piece + "to" + Direction.values()[direc]);
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
						if(node.hasOtherWayToGo()) {//can move fox and has next
							state++;
							node.setNextNode();
							//System.out.println("move fox"+ fox + "to" + direc);
							//cur = cur.getNext();
						}
						else {//can move fox but not has next
							node = node.getPrev();
							node.setNextNode();
							temp.undoAndClear();
							//System.out.println("move fox"+ fox + "back from" + direc);
						}
					}
					else {//cannot move fox
						node = node.getPrev();
						node.setNextNode();
						//System.out.println("can not move fox"+ fox + "to" + direc);
					}
				}
				else if(orientation.equals(Direction.EAST) || orientation.equals(Direction.WEST)) {
					int row = temp.getFox(fox).getHead().getRow();
					GridPoint point =  new GridPoint(row, direc);
					if(temp.testValidFoxMove(temp.getFox(fox), point)){
						temp.moveFox(temp.getFox(fox), point);
						if(node.hasOtherWayToGo()) {//can move fox and has next
							state++;
							node.setNextNode();
							//System.out.println("move fox"+ fox + "to" + direc);
							//cur = cur.getNext();
						}
						else {//can move fox but not has next
							node = node.getPrev();
							node.setNextNode();
							temp.undoAndClear();
							//System.out.println("move fox"+ fox + "to" + direc);
						}
					}
					else {//cannot move fox
						node = node.getPrev();
						node.setNextNode();
						//System.out.println("can not move fox"+ fox + "to" + direc);
					}
				}
			}
			search(node);
		}
		
	}
	
	
	public void getSolvent() {
		/*Stack<Record> stack = temp.getList();
		while(!stack.isEmpty()) {
			Record r = stack.pop();
			System.out.println(r.getPiece().getName() + ":" + r.getNextLoc().getRow() + "," + r.getNextLoc().getCol());
		}*/
		System.out.println(temp.isWin());
	}
	
	public static void main(String[] args) {
		TilePlayBoard game = new TilePlayBoard();
		DFS solver = new DFS(game);
		solver.getSolvent();
	}
	
}
