package dfs;

import java.util.ArrayList;
import java.util.Stack;

import gamePieces.Direction;
import gamePieces.GridPoint;
import mvc.controller.Record;
import mvc.controller.TilePlayBoard;

public class DFS {
	
	private TilePlayBoard temp;
	private Stack<Choice> solvent;
	private Stack<Record> rabbitMoveRecord;
	private ArrayList<Choice> choiceList;
	private int record;
	private int num;
	private ArrayList<boolean[][]> visited;

	
	public DFS(TilePlayBoard board) {
		this.temp = new TilePlayBoard(board);
		solvent = new Stack<Choice>();
		rabbitMoveRecord = new Stack<Record>();
		choiceList = new ArrayList<Choice>();
		
		for(int i=0; i<board.getRabbitNum(); i++) {
			for(int j=0; j<4; j++) {
				choiceList.add(new Choice(i,j));
			}
		}
		
		visited = new ArrayList<boolean[][]>();
		for(int i=0; i<temp.getRabbitNum(); i++) {
			boolean[][] v = new boolean[5][5];
			//set occupied place as visited
			for(int r=0; r<5; r++) {
				for(int c=0; c<5; c++) {
					if(temp.getBoard().getTileAt(r, c).isOccupied()) {
						v[r][c] = true;
					}
				}
			}
			//set location of fox as not visited
			for(int j=0; j<temp.getFoxNum(); j++) {
				int row1 = temp.getFox(j).getHead().getRow();
				int row2 = temp.getFox(j).getTail().getRow();
				int col1 = temp.getFox(j).getHead().getCol();
				int col2 = temp.getFox(j).getTail().getCol();
				
				v[row1][col1] = false;
				v[row2][col2] = false;
			}
			visited.add(v);
		}
		
		record = 0;
		num = 0;
		
		solve();
	
	}
	
	private GridPoint getEmptyLocForR(int rabbit, int direction) {
		int col = temp.getRabbit(rabbit).getCol();
		int row = temp.getRabbit(rabbit).getRow();
		Direction direc = Direction.values()[direction];
		
		if(direc.equals(Direction.NORTH)) {
			if(row<2)return null;
			return new GridPoint(row-1, col);
		}
		else if(direc.equals(Direction.SOUTH)) {
			if(row>2)return null;
			return new GridPoint(row+1, col);
		}
		else if(direc.equals(Direction.EAST)) {
			if(col>2)return null;
			return new GridPoint(row, col+1);
		}
		else if(direc.equals(Direction.WEST)) {
			if(col<2)return null;
			return new GridPoint(row, col-1);
		}
		return null;
	}
	

	public void solve() {
		
		while(!temp.isWin()) {
			if(num == 500) break;
			
			if(record >= temp.getRabbitNum()*4) { //if all cases have been tried and none success
				temp.undo();
				Record undoInfo = this.rabbitMoveRecord.pop();
				int name = undoInfo.getPieceNum();
				int row = undoInfo.getNextLoc().getRow();
				int col = undoInfo.getNextLoc().getCol();
				
				visited.get(name)[row][col] = false;
				
				Choice c = this.solvent.pop();
				record = (c.getName())*4 + c.getDirection();
				//System.out.println("record" + record + ", undo");
			}
			//System.out.println(choiceList.get(record).getName() + ":" + choiceList.get(record).getDirection());
			
			int rabbit = this.choiceList.get(record).getName();
			int direction = this.choiceList.get(record).getDirection();
			
			GridPoint rabbitPoint = temp.getNearestJumpPoint(temp.getRabbit(rabbit), Direction.values()[direction]);
			if(rabbitPoint!= null) {//if rabbit can jump
				//if that point is not visited, which means rabbit is not repeat its behavior
				if(!visited.get(rabbit)[rabbitPoint.getRow()][rabbitPoint.getCol()]) {
					solvent.add(choiceList.get(record));
					GridPoint before = temp.getRabbit(rabbit).getLocation();
					temp.moveRabbit(temp.getRabbit(rabbit), rabbitPoint);
					this.rabbitMoveRecord.add(new Record(rabbit, before, temp.getRabbit(rabbit).getLocation()));
					record = 0;
					visited.get(rabbit)[rabbitPoint.getRow()][rabbitPoint.getCol()] = true;
					num++;
					//System.out.println("move rabbit" + rabbit + "to" + Direction.values()[direction]);
				}
				//that location is visited
				else {
					//System.out.println("cannot move(case1), try next");
					record++;
				}
			}
			else {//rabbit cannot jump to that location
				GridPoint point = getEmptyLocForR(rabbit, direction);
				int foxN = -1;
				GridPoint foxHead = null;
				//fox can move to help
				if(point != null) {
					for(int i=0; i<temp.getFoxNum(); i++) {
						if(temp.testValidFoxMove(temp.getFox(i), point)) {
							foxN = i;
							foxHead = temp.getFox(i).getHead().getLocation();
							temp.moveFox(temp.getFox(i), point);
							//System.out.println("move fox" + i + "to" + point.getRow() + "," + point.getCol());
						}
					}
					rabbitPoint = temp.getNearestJumpPoint(temp.getRabbit(rabbit), Direction.values()[direction]);
					//can jump to there now
					if(rabbitPoint != null && !visited.get(rabbit)[rabbitPoint.getRow()][rabbitPoint.getCol()]) {
						solvent.add(choiceList.get(record));
						GridPoint before = temp.getRabbit(rabbit).getLocation();
						temp.moveRabbit(temp.getRabbit(rabbit), rabbitPoint);
						this.rabbitMoveRecord.add(new Record(rabbit, before, temp.getRabbit(rabbit).getLocation()));
						record = 0;
						visited.get(rabbit)[rabbitPoint.getRow()][rabbitPoint.getCol()] = true;
						num++;
						//System.out.println("move rabbit" + rabbit + "to" + Direction.values()[direction]);
					}
					//still cannot jump to that location
					else {//undo fox move
						if(foxN!=-1 && !temp.getFox(foxN).getHead().getLocation().equals(foxHead)) temp.undo();
						//System.out.println("cannot move(case2), try next");
						record++;
					}
				}
				//fox cannot move to that place
				else {
					//System.out.println("no point, try next, undo fox move");
					record++;
				}
			}
		
		}
	}
	

	public void getSolvent() {
		if(temp.isWin()) {
			Stack<Choice> temp = new Stack<Choice>();
			while(!solvent.isEmpty()) {
				temp.add(solvent.pop());
			}
			while(!temp.isEmpty()) {
				Choice r = temp.pop();
				System.out.println("move rabbit" + (r.getName()+1) + " to " + Direction.values()[r.getDirection()]);
			}
		}
		else {
			System.out.println("no solution for this puzzle");
		}
		
	}
	
	public static void main(String[] args) {
		TilePlayBoard game = new TilePlayBoard();
		DFS solver = new DFS(game);
		solver.getSolvent();
	}
	
}
