package mvc.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import gamePieces.Direction;
import gamePieces.GridPoint;
import gamePieces.NewFox;
import gamePieces.Rabbit;
import tree.FullPathNode;
import tree.MovementData;

/**
 * 
 * @author Craig Worthington, Tiantian Lin
 *
 */
public class Solver {
	
	private TilePlayBoard solve;
	
	public Solver(TilePlayBoard board) {
		solve = new TilePlayBoard(board);
	}
	
	/**
	 * Get list of all moves available to the player from the board's current state 
	 * @return The list of all moves from the current state available to the player
	 */
	private ArrayList<MovementData> getAllMovesFromCurrentState() {
		ArrayList<MovementData> possibleMoves = new ArrayList<MovementData>();
		
		// For each rabbit, test each jump direction to see if there is a valid move that can be made
		for (Rabbit rabbit : this.solve.getRabbits())  {
			for (Direction dir : Direction.values()) {
				if (this.solve.testJumpDirection(rabbit, dir)) { // If a valid move for a rabbit can be made in the given direction
					possibleMoves.add(new MovementData(rabbit, this.solve.getNearestJumpPoint(rabbit, dir))); // add it to the list of possible moves
				}
			}
		}
		
		// For each fox, it can only move in its row
		for (NewFox fox : this.solve.getFoxes()) {
			for (GridPoint newFoxLocation : fox.getValidMoveLocations()) { // For every valid fox movement location
				if (this.solve.testValidFoxMove(fox, newFoxLocation)) {
					possibleMoves.add(new MovementData(fox.getHead(), newFoxLocation));
				}
			}
		}
		
		return possibleMoves;
	}
	
	public ArrayList<MovementData> findSolution() {
		
		ArrayList<MovementData> solution = new ArrayList<MovementData>();
		Queue<FullPathNode> queue = new LinkedList<FullPathNode>();
		FullPathNode currNode;
		
		// Initialize the queue with one node for each possible move from the initial state
		for (MovementData move : this.getAllMovesFromCurrentState()) {
			FullPathNode newNode = new FullPathNode();
			newNode.addMove(new MovementData(move));
			queue.add(newNode);
		}
		
		while (true) {
			currNode = queue.poll();
			
			// execute all moves to set board in that state
			for (MovementData move : currNode.getMovesFromInit()) {
				this.solve.executeMove(move);
			}
			
			// If this move gets the board into a win state, exit
			if (this.solve.getWinState() == true) {
				solution = currNode.getMovesFromInit();
				break;
			}
			
			// Add new nodes into queue that are valid moves from this state
			for (MovementData move : this.getAllMovesFromCurrentState()) {
				FullPathNode node = new FullPathNode(currNode);
				node.addMove(move);
				queue.add(node);
			}
				
			// Roll back the board to its initial state
			for (int i = currNode.getMovesFromInit().size() - 1; i >= 0; --i) {
				this.solve.executeMove(currNode.getMovesFromInit().get(i).getInverseMove());
			}
		}
		
		
		return solution;
	}
	
	public boolean getWinState() {
		return solve.getWinState();
	}

}


