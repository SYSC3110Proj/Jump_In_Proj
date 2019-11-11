package gamePieces;

public class Board {
	private Tile[][] grid;
	
	public Board() {
		this.grid = new Tile[5][5];
	}
	
	public Tile getTileAt(GridPoint location) {
		return this.grid[location.getRow()][location.getCol()];
	}
	
	public Tile getTileAt(int row, int col) {
		return this.grid[row][col];
	}
	
	public void setTileAt(GridPoint location, Tile newTile) {
		this.grid[location.getRow()][location.getCol()] = newTile;
	}
	
	public void setTileAt(int row, int col, Tile newTile) {
		this.grid[row][col] = newTile;
	}
}
