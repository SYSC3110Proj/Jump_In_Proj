package mvc.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

/** 
 * The view class creates the menu and user interface, with buttons and action listeners, so that the user can interact with
 * and play the game.
 * @author Zewen Chen
 * @author Craig Worthington
 * 
 */

public class View extends JPanel implements PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridButton[][] button;
	private JMenuBar menuBar;
	private JMenu edit, file, startWith;
	private JMenuItem undo, redo, save, load, getSolution;
	private JMenuItem game1, game2, game3,buildgame;
	
	private JTextArea textArea;
	
	public View() {
		super();
		this.setLayout(new GridLayout(5,5));
		this.setBounds(300, 400, 500, 500);
		edit = new JMenu("game");
		file = new JMenu("file");
		startWith = new JMenu("start with");
		menuBar = new JMenuBar();
		buildgame = new JMenuItem("build game");
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(startWith);
		menuBar.add(buildgame);
		undo = new JMenuItem("undo");
		redo = new JMenuItem("redo");
		getSolution = new JMenuItem("get solution");
		getSolution.setActionCommand("solve");
		save = new JMenuItem("save");
		load = new JMenuItem("load");
		game1 = new JMenuItem("game1");
		game2 = new JMenuItem("game2");
		game3 = new JMenuItem("game3");
		
		edit.add(undo);
		edit.add(redo);
		edit.add(getSolution);
		
		file.add(save);
		file.add(load);
		
		startWith.add(game1);
		startWith.add(game2);
		startWith.add(game3);
		
		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(500, 100));
		
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
	
	public void initMenu(ActionListener listener) {
		undo.addActionListener(listener);
		redo.addActionListener(listener);
		getSolution.addActionListener(listener);
		save.addActionListener(listener);
		load.addActionListener(listener);
		game1.addActionListener(listener);
		game2.addActionListener(listener);
		game3.addActionListener(listener);
		buildgame.addActionListener(listener);
		
	}
	
	public JMenuBar getMenuBar() {
		return menuBar;
	}
	
	/**
	 * @return the button
	 */
	public GridButton[][] getButton() {
		return button;
	}
	
	//when all rabbits are in the hole, pop a dialog of greeting
	//and quit the game when press quit
	public void popWin() {
		JDialog dialog = new JDialog();
		JTextField txet1= new JTextField();
		dialog.setTitle("Win!");
		dialog.setBounds(600, 500, 300, 100);
		dialog.setModal(true);
		dialog.setLayout(new FlowLayout());
		
		JLabel win = new JLabel("please entern coordinate");
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
	
   public void popBuilderControl(){
	   
   }

	
	//write name on buttons
	public void initButton(String[][] chess, ActionListener listener) {
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				button[row][col].setName(row + ","+ col);
				if(button[row][col].isHole()&&chess[row][col].isEmpty()) {
					button[row][col].setText("Hole");
				}
				else if(!chess[row][col].equals("")) {
					button[row][col].setText(chess[row][col]);
				}
				else {
					button[row][col].setText("");
				}
				button[row][col].addActionListener(listener);
			}
		}
	}
	public void updateButton(String[][] chess) {
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				button[row][col].setName(row + ","+ col);
				if(button[row][col].isHole()&&chess[row][col].isEmpty()) {
					button[row][col].setText("Hole");
				}
				else if(!chess[row][col].equals("")) {
					button[row][col].setText(chess[row][col]);
				}
				else {
					button[row][col].setText("");
				}
			}
		}
	}
	
	public JTextArea getTextArea() {
		return this.textArea;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("winState") &&  (boolean) evt.getNewValue() == true) {
			popWin();
		}
		
	}
}

