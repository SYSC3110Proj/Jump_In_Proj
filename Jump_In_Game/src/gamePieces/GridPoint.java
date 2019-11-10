package gamePieces;

import java.awt.Point;

public class GridPoint extends Point {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -762178550191657169L;

	/**
	 * Constructor for GridPoint
	 * @param row The row in the game board grid of the piece
	 * @param col The column in the game board grid of the piece
	 */
	public GridPoint(int row, int col) {
		this.x = col;
		this.y = row;
	}
	
	/**
	 * Constructor for GridPoint
	 * @param location A Point representing the new location
	 */
	public GridPoint(Point location) {
		this.setLocation(location);
	}
	
	/**
	 * Get the row of the grid point
	 * @return the row of the grid point
	 */
	public int getRow() {
		return this.y;
	}
	
	/**
	 * Set the new row of the grid point
	 * @param newRow the new row for the grid point
	 */
	public void setRow(int newRow) {
		this.y = newRow;
	}
	
	/**
	 * Get the column of the grid point
	 * @return the column of this grid point
	 */
	public int getCol() {
		return this.x;
	}
	
	/**
	 * Set the column of the grid point
	 * @param newCol the new column of the grid point
	 */
	public void setCol(int newCol) {
		this.x = newCol;
	}
	
	public String toString() {
		return ("row=" + this.getRow() + ", col=" + this.getCol());
	}
}
