package gamePieces;

import java.awt.Point;

public class GridPoint extends Point {
	
	public GridPoint(int row, int col) {
		this.x = col;
		this.y = row;
	}
	
	public GridPoint(Point location) {
		this.setLocation(location);
	}

	public int getRow() {
		return this.y;
	}
	
	public void setRow(int newRow) {
		this.y = newRow;
	}
	
	public int getCol() {
		return this.x;
	}
	
	public void setCol(int newCol) {
		this.x = newCol;
	}
}
