package mvc.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import javax.swing.JToggleButton;

import javax.swing.JButton;
import javax.swing.JFrame;

import gamePieces.Direction;
import gamePieces.GridPoint;
import gamePieces.NewFox;
import gamePieces.PieceType;
import gamePieces.Rabbit;
import mvc.view.*;
import tree.FullPathNode;
import tree.MovementData;
import tree.Node;
import gamePieces.Tile;

/** 
 * The Controller class creates the Playboard and View objects within a frame that the player interacts with, 
 * it also contains some of the game logic.
 * @author Ruixuan Ni
 * @author Craig Worthington
 * 
 */

public class Controller {
	
	private TilePlayBoard game;
	private View view;
	boolean select;
	private String name;
	
	private Tile sourceTile, destTile;
	
	private GridPoint sourcePoint, destPoint;
	private GridButton sourceButton;
	
	public Controller() {
		this.game = new TilePlayBoard();
		this.view = new View();
		
		this.game.addPropertyChangeListener(this.view); // Have view observe game
		
		for (int row = 0; row < 5; ++row) {
			for (int col = 0; col < 5; ++col) {
				this.game.getBoard().getTileAt(row, col).addPropertyChangeListener(this.view.getButton()[row][col]); // Have each button observe their corresponding grid in the model
			}
		}
		
		this.select = false;
		
		this.view.initButton(game.getBoardName(), new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// If the player is currently in the selection phase
				if (!select) {
					name = ((GridButton) e.getSource()).getText();
					sourceTile = game.getBoard().getTileAt((((GridButton) e.getSource()).getGridLocation()));
					
					//System.out.println(game.getBoard().getTileAt(((GridButton) e.getSource()).getGridLocation()).toString());
					//System.out.println(game.getBoard().getTileAt(((GridButton) e.getSource()).getGridLocation()).toString());
					
					sourcePoint = ((GridButton) e.getSource()).getGridLocation();
					sourceButton = (GridButton) e.getSource();
					
					//System.out.println("sourceTile = " + sourceTile);
					//System.out.println("sourcePoint = " + sourcePoint);
					
					select = true;
				} else {	// If the player is in the movement phase
					if (name != null) {

						// Test if the player is trying to move a hole or mushroom
						if (sourceTile.getToken() != null && sourceTile.getToken().getPieceType() != PieceType.MUSHROOM) {
							
							destTile = game.getBoard().getTileAt(((GridButton) e.getSource()).getGridLocation());
							destPoint = ((GridButton) e.getSource()).getGridLocation();
							
							//System.out.println("destTile = " + destTile);
							//System.out.println("destPoint = " + destPoint);
							
							if (sourceTile.getToken().getPieceType() == PieceType.RABBIT) {
								moveRabbit();
							} else if (sourceTile.getToken().getPieceType() == PieceType.FOX) {
								moveFox();
							}
							
							// Toggle both buttons to show as off
							sourceButton.setSelected(false);
							((GridButton) e.getSource()).setSelected(false);
							
						}
					}
					select = false;
//					if (game.checkWinState()) {
//						view.popWin();	
//					}
				}
			}
		});
		
		view.initMenu(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("undo")) {
					game.undo();
				}
				else if(e.getActionCommand().equals("redo")) {
					game.redo();
				}
				
			}
		});
	}
	
	/**
	 * Logic for moving a rabbit
	 */
	private void moveRabbit() {
		Rabbit rabbit = (Rabbit) sourceTile.getToken(); // For additional clarity, convert sourceSquare to rabbit
		
		try {
			game.moveRabbit(rabbit, destPoint);
		} catch(IllegalArgumentException exception) {
			System.out.println("Something went wrong!");
			System.err.println(exception);
		}
	}
	
	
	/**
	 * Logic for moving the fox
	 */
	private void moveFox() {
		try {
			NewFox foxToMove = game.getFoxAtLocation(sourceTile.getLocation());
			
			if (sourceTile.getLocation().equals(foxToMove.getTail().getLocation())) { // Check if the user clicked the tail
				
				if ((NewFox.getFoxBorderLocations().contains(foxToMove.getTail().getLocation())) 
						&& (NewFox.getFoxBorderLocations().contains(destPoint))) {
					Direction movementDirection = foxToMove.getTail().getLocation().getDirectionTo(destPoint);
					
					if (movementDirection.equals(Direction.NORTH)) {
						destPoint = new GridPoint(destPoint.getRow()+1, destPoint.getCol());
					} else if (movementDirection.equals(Direction.SOUTH)) {
						destPoint = new GridPoint(destPoint.getRow()-1, destPoint.getCol());
					} else if (movementDirection.equals(Direction.EAST)) {
						destPoint = new GridPoint(destPoint.getRow(), destPoint.getCol()-1);
					} else if (movementDirection.equals(Direction.WEST)) {
						destPoint = new GridPoint(destPoint.getRow(), destPoint.getCol()+1);
					} else {
						throw new IllegalArgumentException("The direction calculated is not a valid direction! Fox can only move horizontally or vertically");
					}
				}
				
				// Convert the movement into a point that is equivalent for the head
				if (foxToMove.getOrientation() == Direction.NORTH) {
					destPoint = new GridPoint(destPoint.getRow()-1, destPoint.getCol());
				} else if (foxToMove.getOrientation() == Direction.SOUTH) {
					destPoint = new GridPoint(destPoint.getRow()+1, destPoint.getCol());
				} else if (foxToMove.getOrientation() == Direction.EAST) {
					destPoint = new GridPoint(destPoint.getRow(), destPoint.getCol()+1);
				} else if (foxToMove.getOrientation() == Direction.WEST) {
					destPoint = new GridPoint(destPoint.getRow(), destPoint.getCol()-1);
				}
			} else { // User clicked the head of the fox
				
				if ((NewFox.getFoxBorderLocations().contains(foxToMove.getHead().getLocation())) // Check if moving from one border location to another
						&& (NewFox.getFoxBorderLocations().contains(destPoint)))  {  // If the fox head is moving to another border location
					
					Direction movementDirection = foxToMove.getHead().getLocation().getDirectionTo(destPoint);
					
					if (movementDirection.equals(Direction.NORTH)) {
						destPoint = new GridPoint(destPoint.getRow()+1, destPoint.getCol());
					} else if (movementDirection.equals(Direction.SOUTH)) {
						destPoint = new GridPoint(destPoint.getRow()-1, destPoint.getCol());
					} else if (movementDirection.equals(Direction.EAST)) {
						destPoint = new GridPoint(destPoint.getRow(), destPoint.getCol()-1);
					} else if (movementDirection.equals(Direction.EAST)) {
						destPoint = new GridPoint(destPoint.getRow(), destPoint.getCol()+1);
					} else {
						throw new IllegalArgumentException("The direction calculated is not a valid direction! Fox can only move horizontally or vertically");
					}
				}
			}
			
			game.moveFox(foxToMove, destPoint);
			
		} catch (IllegalArgumentException error) {
			System.out.println("Something went wrong!");
			System.err.println(error);
		}
	}
	
	//row1, col1 of original position and row2, col2 of destination
	private Direction getDirection(GridPoint source, GridPoint dest) {
		// Check if new destination is in line with the source
		if (source.getRow() != dest.getRow() && source.getCol() != dest.getCol()) {
			return null;
		}
		
		// Check if the destination is the same point as the source
		if (source.equals(dest)) {
			return null;
		}
		
		// If source and dest are on same row
		if (source.getRow() == dest.getRow()) {
			if (source.getCol() > dest.getCol()) {
				return Direction.WEST;
			} else {
				return Direction.EAST;
			}
		} else if (source.getCol() == dest.getCol()) { // If source and dest are in same column
			if (source.getRow() > dest.getRow()) {
				return Direction.NORTH;
			} else {
				return Direction.SOUTH;
			}
		}
		return null;
	}
	
	/**
	 * Get list of all moves available to the player from the board's current state 
	 * @return The list of all moves from the current state available to the player
	 */
	public ArrayList<MovementData> getAllMovesFromCurrentState() {
		ArrayList<MovementData> possibleMoves = new ArrayList<MovementData>();
		
		// For each rabbit, test each jump direction to see if there is a valid move that can be made
		for (Rabbit rabbit : this.game.getRabbits())  {
			for (Direction dir : Direction.values()) {
				if (this.game.testJumpDirection(rabbit, dir)) { // If a valid move for a rabbit can be made in the given direction
					possibleMoves.add(new MovementData(rabbit, this.game.getNearestJumpPoint(rabbit, dir))); // add it to the list of possible moves
				}
			}
		}
		
		// For each fox, it can only move in its row
		for (NewFox fox : this.game.getFoxes()) {
			for (GridPoint newFoxLocation : fox.getValidMoveLocations()) { // For every valid fox movement location
				if (this.game.testValidFoxMove(fox, newFoxLocation)) {
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
				this.game.executeMove(move);
			}
			
			// If this move gets the board into a win state, exit
			if (this.game.getWinState() == true) {
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
				this.game.executeMove(currNode.getMovesFromInit().get(i).getInverseMove());
			}
		}
		
		
		return solution;
	}
	
	public static void main(String[] args) {
		Controller con = new Controller();
		
		JFrame frame=new JFrame("Jump-In");
        frame.setLayout(new BorderLayout());
        frame.setBounds(500, 500, 500, 500);
        frame.getContentPane().add(con.view, BorderLayout.CENTER);
        frame.getContentPane().add(con.view.getMenuBar(), BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        con.findSolution();
	}
	

}
