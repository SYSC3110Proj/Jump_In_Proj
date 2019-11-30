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

/**
 * The GridButton class includes the location of the grid button, methods to set whether the button is a hole and what text is
 * displayed in the button.
 */

public class GridButton extends JToggleButton implements PropertyChangeListener {
	
	private boolean isHole;
	private GridPoint gridLocation;	// location in the grid of the button
	
	// TODO: add comments, add constructor with just point
	public GridButton(int row, int col) {
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
		
		//this.setText("" + row + "," + col);
		
		if (this.isHole) {
			this.setBackground(new Color(139,69,19));
		}
	}
	
	@Override
	public void setText(String str) {
		super.setText(str);
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Token newToken = (Token) evt.getNewValue();
		
		if (newToken != null && newToken.getName() != null) {
			this.setText(newToken.getName());
		}
		else if(newToken != null && newToken.getName() == null){
			this.setText(newToken.getPieceType().toString());
		}
		else {
			this.setText("");
		}
	}	
}
