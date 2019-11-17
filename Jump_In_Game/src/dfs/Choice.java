package dfs;

public class Choice {
	private int name;
	private int direction;
	
	public Choice(int name, int direction) {
		this.name = name;
		this.direction = direction;
	}
	
	public int getName() {
		return this.name;
	}
	
	public int getDirection() {
		return this.direction;
	}
}
