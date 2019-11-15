package dfs;

import java.util.Stack;

import gamePieces.Direction;
import gamePieces.GridPoint;
import gamePieces.Rabbit;
import mvc.controller.Record;
import mvc.controller.TilePlayBoard;

public class DFS {
	
	private DFSNode head;
	private DFSNode cur;
	private TilePlayBoard board;
	private TilePlayBoard temp;
	private int level;
	
	public DFS(TilePlayBoard board) {
		this.board = board;
		this.temp = new TilePlayBoard(board);
		this.level = 30;
		head = new DFSNode(-1, -1, null);
		buildTree(head);
		cur = head.getNext();
		DFSSearch();
		
	}
	
	private void buildTree(DFSNode node) {
		if(level == 0) return;
		level --;
		for(int i=0; i<board.getRabbitNum(); i++) {
			for(int j=0; j<4; j++) {
				node.addNext(new DFSNode(i,j,cur));
			}
		}
		for(DFSNode n: node.getNextList()) {
			buildTree(n);
		}
	}
	
	private GridPoint getHelpPoint(Rabbit r, Direction direc) {
		int row = r.getRow();
		int col = r.getCol();
		
		if(direc.equals(Direction.NORTH)) {
			return new GridPoint(row-1, col);
		}
		else if(direc.equals(Direction.SOUTH)) {
			return new GridPoint(row+1, col);
		}
		else if(direc.equals(Direction.EAST)) {
			return new GridPoint(row, col+1);
		}
		else if(direc.equals(Direction.WEST)) {
			return new GridPoint(row, col-1);
		}
		return null;
	}
	
	private void DFSSearch() {
		
		if(temp.isWin())return;
		
		if(cur.equals(head) && head.hasNext()) cur = head.getNext();
		
		if(!cur.hasNext()) {
			if(!cur.hasPrev())return;
			cur = cur.getPrev();
			cur.removeNext();
			temp.undo();
			DFSSearch();
			getSolvent();
		}
		
		else {
			int piece = cur.getName();
			int direc = cur.getDirec();
			
			GridPoint point = temp.getNearestJumpPoint(temp.getRabbit(piece), Direction.values()[direc]); 
			if(point != null) {
				temp.moveRabbit(temp.getRabbit(piece), point);
				cur = cur.getNext();
			}
			else{
				boolean canJump = false;
				for(int i=0; i<temp.getFoxNum(); i++) {
					if(temp.testValidFoxMove(temp.getFox(i), getHelpPoint(temp.getRabbit(piece), Direction.values()[direc]))) {
						temp.moveFox(temp.getFox(i), getHelpPoint(temp.getRabbit(piece), Direction.values()[direc]));
						point = temp.getNearestJumpPoint(temp.getRabbit(piece), Direction.values()[direc]); 
						temp.moveRabbit(temp.getRabbit(piece), point);
						canJump  = true;
						break;
					}
				}
				if(!canJump) {
					cur = cur.getPrev();
					cur.removeNext();
				}
				cur = cur.getNext();
			}
			
			DFSSearch();
		
		}
	}
	
	public void getSolvent() {
		Stack<Record> stack = temp.getList();
		while(!stack.isEmpty()) {
			Record r = stack.pop();
			System.out.println(r.getPiece().getName() + ":" + r.getNextLoc().getRow() + "," + r.getNextLoc().getCol());
		}
	}
	
	public static void main(String[] args) {
		TilePlayBoard game = new TilePlayBoard();
		DFS solver = new DFS(game);
	}
	

}
