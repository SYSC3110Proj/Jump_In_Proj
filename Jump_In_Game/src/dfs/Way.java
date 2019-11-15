package dfs;

public class Way {
	private int name;
	private int direction;
	
	public Way(int name, int direction) {
		this.name = name;
		this.direction = direction;
	}
	
	public int getName() {
		return name;
	}
	
	public int getDirection() {
		return direction;
	}
}
