package mvc.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.border.Border;

public class GridButton extends JToggleButton {
	
	private boolean isHole;
	private Point gridLocation;	// location in the grid of the button
	
	
	public GridButton(int row, int col) {
		this.gridLocation = new Point(col, row);
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
		super.setText("" + gridLocation.x + "," + gridLocation.y + ": " + str);
	}
	
	

	public boolean isHole() {
		return isHole;
	}


	public void setHole(boolean isHole) {
		this.isHole = isHole;
	}


	public Point getGridLocation() {
		return gridLocation;
	}


	public void setGridLocation(Point location) {
		this.gridLocation = location;
	}
	
	
	
	
}
