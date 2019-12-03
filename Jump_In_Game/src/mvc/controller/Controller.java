package mvc.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.xml.sax.SAXException;

import gamePieces.Direction;
import gamePieces.GridPoint;
import gamePieces.NewFox;
import gamePieces.PieceType;
import gamePieces.Rabbit;
import mvc.view.*;

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
	
	private Tile sourceTile;
	private GridPoint sourcePoint, destPoint;
	private GridButton sourceButton;
	
	public Controller() {
		this.view = new View();
		this.game = new TilePlayBoard();
		view.initMenu(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("undo")) {
					if(game != null) {
						game.undo();
						view.updateButton(game.getBoardName());
					}
				}
				else if(e.getActionCommand().equals("redo")) {
					if(game != null) {
						game.redo();
						view.updateButton(game.getBoardName());
					}
				}
				else if(e.getActionCommand().equals("solve")) {
					if(game!= null) {
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
				}
				else if(e.getActionCommand().equals("save")) {
					XMLHandler handler = new XMLHandler();
					try {
						handler.setBoard(game);
						handler.toXMLFile("saved_game");
					} catch (SAXException e1) {
						e1.printStackTrace();
					}
				}
				else if(e.getActionCommand().equals("load")) {
					XMLHandler handler = new XMLHandler();
					try {
						handler.importXMLFileByName("saved_game");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(game == null) {
						setGame(handler.getBoard());
						initButtons();
					}
					else {
						setGame(handler.getBoard());
						view.updateButton(game.getBoardName());
					}
				}
				else if(e.getActionCommand().equals("game1")) {
					XMLHandler handler = new XMLHandler();
					try {
						handler.importXMLFile(1);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if(game == null) {
						setGame(handler.getBoard());
						initButtons();
					}
					else {
						setGame(handler.getBoard());
						view.updateButton(game.getBoardName());
					}
				}
				else if(e.getActionCommand().equals("game2")) {
					XMLHandler handler = new XMLHandler();
					try {
						handler.importXMLFile(2);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if(game == null) {
						setGame(handler.getBoard());
						initButtons();
					}
					else {
						setGame(handler.getBoard());
						view.updateButton(game.getBoardName());
					}
				}
				else if(e.getActionCommand().equals("game3")) {
					XMLHandler handler = new XMLHandler();
					try {
						handler.importXMLFile(3);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if(game == null) {
						setGame(handler.getBoard());
						initButtons();
					}
					else {
						setGame(handler.getBoard());
						view.updateButton(game.getBoardName());
					}
				}
				else if(e.getActionCommand().equals("set")) {
					boolean init = (game==null);
					game = new TilePlayBoard();
					
					BuilderWindow bc = new BuilderWindow();
					JDialog dialog = new JDialog();
					dialog.add(bc);
					dialog.setBounds(300, 50, 550, 350);
					dialog.setVisible(true);
					bc.initConfirm(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
								if(!bc.getXr1().isEmpty()&&!bc.getYr1().isEmpty()) {
								   game.setRabbit(Integer.parseInt(bc.getXr1()), Integer.parseInt(bc.getYr1()));
							   }
							   if(!bc.getXr2().isEmpty()&&!bc.getYr2().isEmpty()) {
								   game.setRabbit(Integer.parseInt(bc.getXr2()), Integer.parseInt(bc.getYr2()));
							   }
							   if(!bc.getXr3().isEmpty()&&!bc.getYr3().isEmpty()) {
								   game.setRabbit(Integer.parseInt(bc.getXr3()), Integer.parseInt(bc.getYr3()));
							   }
							   if(!bc.getXm1().isEmpty()&&!bc.getYm1().isEmpty()) {
								   game.setMushroom(Integer.parseInt(bc.getXm1()), Integer.parseInt(bc.getYm1()));
							   }
							   if(!bc.getXm2().isEmpty()&&!bc.getYm2().isEmpty()) {
								   game.setMushroom(Integer.parseInt(bc.getXm2()), Integer.parseInt(bc.getYm2()));
							   }
							   if(!bc.getXm3().isEmpty()&&!bc.getYm3().isEmpty()) {
								   game.setMushroom(Integer.parseInt(bc.getXm3()), Integer.parseInt(bc.getYm3()));
							   }

							   if(!bc.getXf1().isEmpty()&&!bc.getYf1().isEmpty()&&bc.getF1Dir()!=null) {
								   GridPoint g = new GridPoint(Integer.parseInt(bc.getXf1()), Integer.parseInt(bc.getYf1()));
								   game.setFox(g,bc.getF1Dir());
							   }
							   if(!bc.getXf2().isEmpty()&&!bc.getYf2().isEmpty()&&bc.getF2Dir()!=null) {
								   GridPoint g = new GridPoint(Integer.parseInt(bc.getXf2()), Integer.parseInt(bc.getYf2()));
								   game.setFox(g,bc.getF2Dir());
							   }
							   
							   dialog.dispose();
						   } 
					   });
					
					if(init) initButtons();
					else view.updateButton(game.getBoardName());
				}
				else if(e.getActionCommand().equals("build game")) {
					BuilderWindow bc= new BuilderWindow();
					   JDialog dialog = new JDialog();
					   dialog.add(bc);
					   dialog.setBounds(300, 50, 550, 350);
					   dialog.setVisible(true);
					   bc.confirm(new ActionListener () {
						   public void actionPerformed(ActionEvent e) {
							   //System.out.print("test");
							   if(!bc.getXr1().isEmpty()&&!bc.getYr1().isEmpty()) {
								   game.setRabbit(Integer.parseInt(bc.getXr1()), Integer.parseInt(bc.getYr1()));
							   }
							   if(!bc.getXr2().isEmpty()&&!bc.getYr2().isEmpty()) {
								   game.setRabbit(Integer.parseInt(bc.getXr2()), Integer.parseInt(bc.getYr2()));
							   }
							   if(!bc.getXr3().isEmpty()&&!bc.getYr3().isEmpty()) {
								   game.setRabbit(Integer.parseInt(bc.getXr3()), Integer.parseInt(bc.getYr3()));
							   }
							   if(!bc.getXm1().isEmpty()&&!bc.getYm1().isEmpty()) {
								   game.setMushroom(Integer.parseInt(bc.getXm1()), Integer.parseInt(bc.getYm1()));
							   }
							   if(!bc.getXm2().isEmpty()&&!bc.getYm2().isEmpty()) {
								   game.setMushroom(Integer.parseInt(bc.getXm2()), Integer.parseInt(bc.getYm2()));
							   }
							   if(!bc.getXm3().isEmpty()&&!bc.getYm3().isEmpty()) {
								   game.setMushroom(Integer.parseInt(bc.getXm3()), Integer.parseInt(bc.getYm3()));
							   }
							   
							   if(!bc.getXf1().isEmpty()&&!bc.getYf1().isEmpty()&&bc.getF1Dir()!=null) {
								   GridPoint g = new GridPoint(Integer.parseInt(bc.getXf1()), Integer.parseInt(bc.getYf1()));
								   game.setFox(g,bc.getF1Dir());
							   }
							   if(!bc.getXf2().isEmpty()&&!bc.getYf2().isEmpty()&&bc.getF2Dir()!=null) {
								   GridPoint g = new GridPoint(Integer.parseInt(bc.getXf2()), Integer.parseInt(bc.getYf2()));
								   game.setFox(g,bc.getF2Dir());
							   }
							   initButtons();
						   } 
					   });
				}
				
			}
		});
	}
	
	private void setGame(TilePlayBoard newGame) {
		this.game = new TilePlayBoard(newGame);
	}
	
	private void initButtons() {
		
		this.select = false;
		
		for (int row = 0; row < 5; ++row) {
			for (int col = 0; col < 5; ++col) {
				this.game.getBoard().getTileAt(row, col).addPropertyChangeListener(this.view.getButton()[row][col]); // Have each button observe their corresponding grid in the model
			}
		}
		
		this.view.initButton(game.getBoardName(), new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// If the player is currently in the selection phase
				if (!select) {
					name = ((GridButton) e.getSource()).getText();
					sourcePoint = ((GridButton) e.getSource()).getGridLocation();
					sourceTile = game.getBoard().getTileAt(sourcePoint);
					sourceButton = (GridButton) e.getSource();
					select = true;
				} 
				else {	// If the player is in the movement phase
					if (name != null) {
						// Test if the player is trying to move a hole or mushroom
						if (sourceTile.getToken() != null && sourceTile.getToken().getPieceType() != PieceType.MUSHROOM) {
							destPoint = ((GridButton) e.getSource()).getGridLocation();
							
							if (sourceTile.getToken().getPieceType() == PieceType.RABBIT) {
								game.moveRabbit((Rabbit)sourceTile.getToken(), destPoint);
							} else if (sourceTile.getToken().getPieceType() == PieceType.FOX) {
								moveFox();
							}
							
							// Toggle both buttons to show as off
							sourceButton.setSelected(false);
							((GridButton) e.getSource()).setSelected(false);
							
						}
					}
					select = false;
				}
				view.updateButton(game.getBoardName());
				if(game.isWin()) view.popWin();
			}
		});
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
