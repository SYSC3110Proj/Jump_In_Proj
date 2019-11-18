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
	private Stack<Record> MoveRecord;
	private ArrayList<Choice> choiceList;
	private int record;
	private int num;
	private ArrayList<boolean[][]> visited;

	public DFS(TilePlayBoard board) {
		this.temp = new TilePlayBoard();
		solvent = new Stack<Choice>();
		MoveRecord = new Stack<Record>();
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
	
	private GridPoint getNearestLocForR(int rabbit, int direction) {
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
			if(num == 50) break;
			
			if(record >= temp.getRabbitNum()*4) { //if all cases have been tried and none success
				//MoveRecord.pop();
				Record undoInfo = MoveRecord.pop();
				temp.undo();
				
				//if its a fox move
				while(!MoveRecord.isEmpty() && undoInfo.getPieceNum() >= temp.getRabbitNum()) {
					temp.undo();
					undoInfo = MoveRecord.pop();
					//undoInfo = MoveRecord.peek();
					//System.out.println("undo fox");
				}
				
				int name = undoInfo.getPieceNum();
				int row = undoInfo.getNextLoc().getRow();
				int col = undoInfo.getNextLoc().getCol();
				
				visited.get(name)[row][col] = false;
				
				Choice c = this.solvent.pop();
				record = (c.getName())*4 + c.getDirection()+1;
				continue;
				//System.out.println("record" + record + ", undo");
			}
			//System.out.println(choiceList.get(record).getName() + ":" + choiceList.get(record).getDirection());
			
			int rabbit = this.choiceList.get(record).getName();
			int direction = this.choiceList.get(record).getDirection();
			boolean moveFox = false;
			
			GridPoint rabbitPoint = temp.getNearestJumpPoint(temp.getRabbit(rabbit), Direction.values()[direction]);
			if(rabbitPoint!= null) {//if rabbit can jump
				//if that point is not visited, which means rabbit is not repeat its behavior
				//and don't want to try to move fox to help
				if(!visited.get(rabbit)[rabbitPoint.getRow()][rabbitPoint.getCol()]) {
					//try if rabbit can jump through two squares
					for(int i=0; i<temp.getFoxNum(); i++) {
						if(temp.testValidFoxMove(temp.getFox(i), rabbitPoint)) {
							GridPoint before = temp.getFox(i).getHead().getLocation();
							temp.moveFox(temp.getFox(i), rabbitPoint);
							GridPoint after = temp.getFox(i).getHead().getLocation();
							if(!before.equals(after)) {
								this.MoveRecord.add(new Record(i+temp.getRabbitNum(), before, after));
								moveFox = true;
							}
						}
					}
					if(moveFox) {
						if(temp.getNearestJumpPoint(temp.getRabbit(rabbit), Direction.values()[direction]) != null) {
							rabbitPoint = temp.getNearestJumpPoint(temp.getRabbit(rabbit), Direction.values()[direction]);
							//System.out.println("move fox");
						}
						else {
							MoveRecord.pop();
							temp.undo();
						}
					}
					//move rabbit and record
					solvent.add(choiceList.get(record));
					GridPoint before = temp.getRabbit(rabbit).getLocation();
					temp.moveRabbit(temp.getRabbit(rabbit), rabbitPoint);
					this.MoveRecord.add(new Record(rabbit, before, temp.getRabbit(rabbit).getLocation()));
					record = 0;
					visited.get(rabbit)[rabbitPoint.getRow()][rabbitPoint.getCol()] = true;
					num++;
					//System.out.println("move rabbit" + rabbit + "to" + Direction.values()[direction]);
				}
				//that location is visited
				else {
					//System.out.println("cannot move(cz visited), try next");
					record++;
				}
			}
			else {//rabbit cannot jump to that location
				GridPoint point = getNearestLocForR(rabbit, direction);
				GridPoint foxHead = null;
				//fox can move to help
				if(point != null) {
					for(int i=0; i<temp.getFoxNum(); i++) {
						if(temp.testValidFoxMove(temp.getFox(i), point)) {
							foxHead = temp.getFox(i).getHead().getLocation();
							temp.moveFox(temp.getFox(i), point);
							if(!foxHead.equals(temp.getFox(i).getHead().getLocation())) {
								this.MoveRecord.add(new Record(i+temp.getRabbitNum(), foxHead, temp.getFox(i).getHead().getLocation()));
								moveFox = true;
								//System.out.println("move fox" + i + "to" + point.getRow() + "," + point.getCol());
							}
						}
					}
					if(moveFox) {
						rabbitPoint = temp.getNearestJumpPoint(temp.getRabbit(rabbit), Direction.values()[direction]);
						//can jump to there now
						if(rabbitPoint != null && !visited.get(rabbit)[rabbitPoint.getRow()][rabbitPoint.getCol()]) {
							solvent.add(choiceList.get(record));
							GridPoint before = temp.getRabbit(rabbit).getLocation();
							temp.moveRabbit(temp.getRabbit(rabbit), rabbitPoint);
							this.MoveRecord.add(new Record(rabbit, before, temp.getRabbit(rabbit).getLocation()));
							record = 0;
							visited.get(rabbit)[rabbitPoint.getRow()][rabbitPoint.getCol()] = true;
							num++;
							//System.out.println("move rabbit" + rabbit + "to" + Direction.values()[direction]);
						}
						//still cannot jump to that location
						else {//undo fox move
							temp.undo();
							this.MoveRecord.pop();
						}
						//System.out.println("cannot move(case2), try next");
						record++;
					}
					else {
						//System.out.println("no point, try next");
						record++;
					}
				}
				//fox cannot move to that place
				else {
					//System.out.println("no point, try next");
					record++;
				}
			}
		
		}
	}
	

	public void getSolvent() {
		if(!temp.isWin()) System.out.println("there is no solvent for this puzzle");
		else {
			Stack<Record> record = new Stack<Record>();
			while(!this.MoveRecord.isEmpty()) {
				record.add(this.MoveRecord.pop());
			}
			while(!record.isEmpty()) {
				Record r = record.pop();
				if(r.getPieceNum() < temp.getRabbitNum()) {
					System.out.println("move rabbit" + (r.getPieceNum()+1) + " to " + r.getNextLoc().getRow() + "," + r.getNextLoc().getCol());
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		TilePlayBoard game = new TilePlayBoard();
		DFS solver = new DFS(game);
		solver.getSolvent();
	}
	
}
