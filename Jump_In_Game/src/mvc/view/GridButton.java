package mvc.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.border.Border;

import gamePieces.GridPoint;
import gamePieces.Token;

public class GridButton extends JToggleButton implements PropertyChangeListener {
	
	private boolean isHole;
	private boolean waitingForSolver;
	private GridPoint gridLocation;	// location in the grid of the button
	
	// TODO: add comments, add constructor with just point
	public GridButton(int row, int col) {
		this.waitingForSolver = false;
		this.gridLocation = new GridPoint(row, col);
		this.setPreferredSize(new Dimension(100, 100));
		
		
		// Check if this gridbutton is supposed to be a hole
		if (row == 0 || row == 4) {
			if (col == 0 || col == 4) {
				this.isHole = true;
			}
		} else if (row == 2 && col == 2) {
			this.isHole = true;
		} else {
			this.isHole = false;
		}
		
		this.setText("" + row + "," + col);
		
		if (this.isHole) {
			this.setBackground(new Color(139,69,19));
		}
	}
	
	@Override
	public void setText(String str) {
		super.setText("" + gridLocation.y + "," + gridLocation.x + ": " + str);
	}
	
	

	public boolean isHole() {
		return isHole;
	}


	public void setHole(boolean isHole) {
		this.isHole = isHole;
	}


	public GridPoint getGridLocation() {
		return gridLocation;
	}


	public void setGridLocation(GridPoint location) {
		this.gridLocation = location;
	}

	/**
	 * @return the waitingForSolver
	 */
	public boolean isWaitingForSolver() {
		return waitingForSolver;
	}

	/**
	 * @param waitingForSolver set waitingForSolver flag
	 */
	public void setWaitingForSolver(boolean waitingForSolver) {
		this.waitingForSolver = waitingForSolver;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if (evt.getPropertyName().equalsIgnoreCase("isSolving")) { // PropertyChange Handler for enabling isSolving
			this.setWaitingForSolver((boolean) evt.getNewValue());
		} else if (evt.getPropertyName().equalsIgnoreCase("token") && this.waitingForSolver == false) { // PropertyChange handler for token change
			Token newToken = (Token) evt.getNewValue();
			if (newToken != null) {
				this.setText(newToken.getPieceType().toString());
			} else {
				this.setText("empty");
			}
		}
		
		
		
	}
	
	
	
	
}
