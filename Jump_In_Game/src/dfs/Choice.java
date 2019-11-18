package dfs;

public class Choice {
	private int name;
	private int direction;
	//private boolean moveFox
	
	public Choice(int name, int direction) {
		this.name = name;
		this.direction = direction;
		//this.moveFox = moveFox;
	}
	
	public int getName() {
		return this.name;
	}
	
	public int getDirection() {
		return this.direction;
	}
	
	/*public boolean getFoxMove() {
		return this.moveFox;
	}*/
}
