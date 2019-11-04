package mvc;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

public class GridButton extends JButton {
	private boolean isHole;
	
	public GridButton(int row, int col) {
		this.setPreferredSize(new Dimension(100, 100));
		
		// Check if this gridbutton is supposed to be a hole
		if (row == 0 || row == 4) {
			if (col == 0 || col == 4) {
				this.setBackground(new Color(139,69,19));
			}
		} else if (row == 2 && col == 2) {
			this.setBackground(new Color(139,69,19));
		}
	}
}
