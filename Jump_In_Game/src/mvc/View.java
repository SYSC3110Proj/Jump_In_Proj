package mvc;

import java.awt.Dimension;
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
	private JButton[][] button;
	
	public View() {
		super();
		this.setLayout(new GridLayout(5,5));
		this.setBounds(300, 400, 500, 500);
		
		//use JButton to make a 5x5 board
		button = new JButton[5][5];
		
		for(int r=0; r<5; r++) {
			for(int c=0; c<5; c++) {
				button[r][c] = new JButton();
				button[r][c].setPreferredSize(new Dimension(100,100));
				this.add(button[r][c]);
			}
		}
		
		this.setVisible(true);
	}
	
	//when pieces are moved, update the board by changing names on it
	public void update(String[][] chess) {
		for(int r=0; r<5; r++) {
			for(int c=0; c<5; c++) {
				button[r][c].setText(chess[r][c]);
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
		for(int r=0; r<5; r++) {
			for(int c=0; c<5; c++) {
				button[r][c].setName(r + ","+ c);
				button[r][c].setText(chess[r][c]);
				button[r][c].addActionListener(listener);
			}
		}
	}
	
	
}
