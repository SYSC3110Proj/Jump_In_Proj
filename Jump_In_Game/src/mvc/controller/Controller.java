package mvc.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import javax.swing.JToggleButton;

import org.xml.sax.SAXException;

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
	private Solver solve;
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
				else if(e.getActionCommand().equals("solve")) {
					solve = new Solver(game);
					ArrayList<MovementData> solvent = solve.findSolution();
					String text = "";
					for(int i=0; i<solvent.size(); i++) {
						MovementData temp = solvent.get(i);
						text += "move" + temp.getToken().getName() + " to (" + 
						temp.getNewLocation().getRow() + "," + temp.getNewLocation().getCol() + ")\n";
					}
					view.getTextArea().setText(text);
				}
				else if(e.getActionCommand().equals("save")) {
					XMLHandler handler = new XMLHandler(game);
					try {
						handler.toXMLFile();
					} catch (SAXException e1) {
						e1.printStackTrace();
					}
				}
				else if(e.getActionCommand().equals("load")) {
					
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
	
	public static void main(String[] args) {
		Controller con = new Controller();
		
		JFrame frame=new JFrame("Jump-In");
        frame.setLayout(new BorderLayout());
        frame.setBounds(500, 500, 500, 600);
        frame.getContentPane().add(con.view, BorderLayout.CENTER);
        frame.getContentPane().add(con.view.getMenuBar(), BorderLayout.NORTH);
        frame.getContentPane().add(con.view.getTextArea(), BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	

}
