package mvc;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class View extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridButton[][] button;
	
	public View() {
		super();
		this.setLayout(new GridLayout(5,5));
		this.setBounds(300, 400, 500, 500);
		
		//use JButton to make a 5x5 board
		button = new GridButton[5][5];
		
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				button[row][col] = new GridButton(row, col);
				this.add(button[row][col]);
			}
		}
		
		this.setVisible(true);
	}
	
	//when pieces are moved, update the board by changing names on it
	public void update(String[][] chess) {
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				button[row][col].setText(chess[row][col]);
			}
		}
	}
	
	//when all rabbits are in the hole, pop a dialog of greeting
	//and quit the game when press quit
	public void popWin() {
		JDialog dialog = new JDialog();
		dialog.setTitle("Win!");
		dialog.setBounds(600, 500, 300, 100);
		dialog.setModal(true);
		dialog.setLayout(new FlowLayout());
		
		JLabel win = new JLabel("Congratulations, you have win the game.");
		JButton quit = new JButton("quit");
		
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		dialog.add(win);
		dialog.add(quit);
		
		dialog.setVisible(true);
		
	}
	
	//write name on buttons
	public void initButton(String[][] chess, ActionListener listener) {
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				button[row][col].setName(row + ","+ col);
				button[row][col].setText(chess[row][col]);
				button[row][col].addActionListener(listener);
			}
		}
	}
	
	
}
