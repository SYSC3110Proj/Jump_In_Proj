package tree;

import java.util.ArrayList;

public class FullPathNode {
	private ArrayList<MovementData> movesFromInit;
	
	public FullPathNode() {
		this.movesFromInit = new ArrayList<MovementData>();
	}
	
	public FullPathNode(ArrayList<MovementData> previousMoves) {
		this.movesFromInit = new ArrayList<MovementData>();
		
		for (MovementData move : previousMoves) {
			this.movesFromInit.add(new MovementData(move));
		}
	}
	
	// Copy constructor for full path node
	public FullPathNode(FullPathNode node) {
		this.movesFromInit = new ArrayList<MovementData>();
		
		for (MovementData move : node.getMovesFromInit()) {
			this.movesFromInit.add(new MovementData(move));
		}
	}

	/**
	 * @return the movesFromInit
	 */
	public ArrayList<MovementData> getMovesFromInit() {
		return movesFromInit;
	}

	/**
	 * @param movesFromInit the movesFromInit to set
	 */
	public void setMovesFromInit(ArrayList<MovementData> movesFromInit) {
		this.movesFromInit = movesFromInit;
	}
	
	/**
	 * Get the last move in this node from the initial state
	 * @return
	 */
	public MovementData getLastMove() {
		return this.movesFromInit.get(this.movesFromInit.size() - 1);
	}
	
	public void addMove(MovementData move) {
		this.movesFromInit.add(move);
	}
	
}
