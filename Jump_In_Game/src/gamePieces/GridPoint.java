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
	
	/**
	 * Get the cardinal direction from this point to a given point (only in a straight line!)
	 * @param newLocation the location to get the direction to
	 * @return the direction in which newLocation is located, or throws an error if not in a straight line
	 */
	public Direction getDirectionTo(GridPoint newLocation) {
		if (this.getRow() == newLocation.getRow()) {
			if (this.getCol() < newLocation.getCol()) {
				return Direction.EAST;
			} else if (this.getCol() > newLocation.getCol()) {
				return Direction.WEST;
			} else {
				return null;
			}
		} else if (this.getCol() == newLocation.getCol()) {
			if (this.getRow() < newLocation.getRow()) {
				return Direction.SOUTH;
			} else if (this.getRow() > newLocation.getRow()) {
				return Direction.NORTH;
			} else {
				return null;
			}
		} else {
			//throw new IllegalArgumentException("newLocation is not in a directly north, south, east or west of this point");
			return null;
		}
	}
	
	public String toString() {
		return ("row=" + this.getRow() + ", col=" + this.getCol());
	}
}
