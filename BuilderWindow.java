package mvc.view;

import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;

public class BuilderWindow extends JPanel {
	private JTextField r1x;
	private JTextField r1y;
	private JTextField r2x;
	private JTextField r2y;
	private JTextField r3x;
	private JTextField r3y;
	private JLabel label_1;
	private JTextField m1x;
	private JTextField m1y;
	private JLabel label_2;
	private JTextField m2x;
	private JTextField m2y;
	private JLabel lblMushrom;
	private JTextField m3x;
	private JTextField m3y;
	private JLabel lblFox;
	private JLabel lblFox_1;
	private JTextField f1x;
	private JTextField f1y;
	private JTextField f2x;
	private JTextField f2y;
	private JRadioButton rdbtnNorth;
	private JRadioButton rdbtnSouth;
	private JRadioButton rdbtnEast;
	private JRadioButton rdbtnWest;
	private JRadioButton rdbtnNorth_1;
	private JRadioButton rdbtnSouth_1;
	private JRadioButton rdbtnEast_1;
	private JRadioButton rdbtnWest_1;
	private JButton btnConfirm;
	private String xr1,yr1,xr2,yr2,xr3,yr3,xm1,ym1,xm2,ym2,xm3,ym3,xf1,yf1,xf2,yf2;
	private JButton btnConfirm_1;
	/**
	 * Create the panel.
	 */
	public BuilderWindow() {
		setLayout(null);
		
		JLabel lblRabbit = new JLabel("Rabbit 1:");
		lblRabbit.setBounds(6, 60, 61, 16);
		add(lblRabbit);
		
		r1x = new JTextField();
		r1x.setBounds(79, 55, 61, 21);
		add(r1x);
		r1x.setColumns(10);
		
		
		
		r1y = new JTextField();
		r1y.setBounds(152, 55, 61, 26);
		add(r1y);
		r1y.setColumns(10);
		
	
		
		JLabel lblX = new JLabel("x");
		lblX.setBounds(79, 37, 61, 16);
		add(lblX);
		
		JLabel lblY = new JLabel("y");
		lblY.setBounds(152, 37, 61, 16);
		add(lblY);
		
		JLabel lblRabbit_1 = new JLabel("Rabbit 2:");
		lblRabbit_1.setBounds(6, 88, 61, 16);
		add(lblRabbit_1);
		
		r2x = new JTextField();
		r2x.setColumns(10);
		r2x.setBounds(79, 83, 61, 26);
		add(r2x);
		
		r2y = new JTextField();
		r2y.setColumns(10);
		r2y.setBounds(152, 83, 61, 26);
		add(r2y);
		
		JLabel label = new JLabel("Rabbit 3:");
		label.setBounds(6, 116, 61, 16);
		add(label);
		
		r3x = new JTextField();
		r3x.setColumns(10);
		r3x.setBounds(79, 111, 61, 26);
		add(r3x);
		
		r3y = new JTextField();
		r3y.setColumns(10);
		r3y.setBounds(152, 111, 61, 26);
		add(r3y);
		
		label_1 = new JLabel("Mushrom 1:");
		label_1.setBounds(0, 144, 75, 16);
		add(label_1);
		
		m1x = new JTextField();
		m1x.setColumns(10);
		m1x.setBounds(79, 139, 61, 26);
		add(m1x);
		
		m1y = new JTextField();
		m1y.setColumns(10);
		m1y.setBounds(152, 139, 61, 26);
		add(m1y);
		
		label_2 = new JLabel("Mushrom 2:");
		label_2.setBounds(0, 172, 75, 16);
		add(label_2);
		
		m2x = new JTextField();
		m2x.setColumns(10);
		m2x.setBounds(79, 167, 61, 26);
		add(m2x);
		
		m2y = new JTextField();
		m2y.setColumns(10);
		m2y.setBounds(152, 167, 61, 26);
		add(m2y);
		
		lblMushrom = new JLabel("Mushrom 3:");
		lblMushrom.setBounds(0, 200, 75, 16);
		add(lblMushrom);
		
		m3x = new JTextField();
		m3x.setColumns(10);
		m3x.setBounds(79, 195, 61, 26);
		add(m3x);
		
		m3y = new JTextField();
		m3y.setColumns(10);
		m3y.setBounds(152, 195, 61, 26);
		add(m3y);
		
		lblFox = new JLabel("Fox1:");
		lblFox.setBounds(6, 245, 61, 16);
		add(lblFox);
		
		lblFox_1 = new JLabel("Fox2:");
		lblFox_1.setBounds(6, 273, 61, 16);
		add(lblFox_1);
		
		f1x = new JTextField();
		f1x.setColumns(10);
		f1x.setBounds(79, 233, 61, 26);
		add(f1x);
		
		f1y = new JTextField();
		f1y.setColumns(10);
		f1y.setBounds(152, 233, 61, 26);
		add(f1y);
		
		f2x = new JTextField();
		f2x.setColumns(10);
		f2x.setBounds(79, 268, 61, 26);
		add(f2x);
		
		f2y = new JTextField();
		f2y.setColumns(10);
		f2y.setBounds(152, 268, 61, 26);
		add(f2y);
		
		rdbtnNorth = new JRadioButton("north");
		rdbtnNorth.setBounds(214, 234, 70, 21);
		add(rdbtnNorth);
		
		rdbtnSouth = new JRadioButton("south");
		rdbtnSouth.setBounds(292, 234, 70, 21);
		add(rdbtnSouth);
		
		rdbtnEast = new JRadioButton("east");
		rdbtnEast.setBounds(374, 235, 70, 21);
		add(rdbtnEast);
		
		rdbtnWest = new JRadioButton("west");
		rdbtnWest.setBounds(437, 235, 70, 21);
		add(rdbtnWest);
		
		ButtonGroup gp1 = new ButtonGroup();
		gp1.add(rdbtnEast);
		gp1.add(rdbtnSouth);
		gp1.add(rdbtnWest);
		gp1.add(rdbtnNorth);
		
		rdbtnNorth_1 = new JRadioButton("north");
		rdbtnNorth_1.setBounds(214, 270, 70, 21);
		add(rdbtnNorth_1);
		
		rdbtnSouth_1 = new JRadioButton("south");
		rdbtnSouth_1.setBounds(292, 269, 70, 21);
		add(rdbtnSouth_1);
		
		rdbtnEast_1 = new JRadioButton("east");
		rdbtnEast_1.setBounds(374, 269, 70, 21);
		add(rdbtnEast_1);
		
		rdbtnWest_1 = new JRadioButton("west");
		rdbtnWest_1.setBounds(447, 269, 70, 21);
		add(rdbtnWest_1);
		
		ButtonGroup gp2 = new ButtonGroup();
		gp2.add(rdbtnEast_1);
		gp2.add(rdbtnNorth_1);
		gp2.add(rdbtnSouth_1);
		gp2.add(rdbtnSouth_1);
		gp2.add(rdbtnWest_1);
		
		btnConfirm_1 = new JButton("Confirm");
		btnConfirm_1.setBounds(327, 103, 117, 29);
		add(btnConfirm_1);
		
		

		
		
	}
	public void confirm(ActionListener l) {
		btnConfirm_1.addActionListener(l);
	}
	/**
	 * @return the xr1
	 */
	public String getXr1() {
		return r1x.getText();
	}
	/**
	 * @return the yr1
	 */
	public String getYr1() {
		return r1y.getText();
	}
	/**
	 * @return the xr2
	 */
	public String getXr2() {
		return xr2;
	}
	/**
	 * @return the yr2
	 */
	public String getYr2() {
		return yr2;
	}
	/**
	 * @return the xr3
	 */
	public String getXr3() {
		return xr3;
	}
	/**
	 * @return the yr3
	 */
	public String getYr3() {
		return yr3;
	}
	/**
	 * @return the xm1
	 */
	public String getXm1() {
		return xm1;
	}
	/**
	 * @return the ym1
	 */
	public String getYm1() {
		return ym1;
	}
	/**
	 * @return the xm2
	 */
	public String getXm2() {
		return xm2;
	}
	/**
	 * @return the ym2
	 */
	public String getYm2() {
		return ym2;
	}
	/**
	 * @return the xm3
	 */
	public String getXm3() {
		return xm3;
	}
	/**
	 * @return the ym3
	 */
	public String getYm3() {
		return ym3;
	}
	/**
	 * @return the xf1
	 */
	public String getXf1() {
		return xf1;
	}
	/**
	 * @return the yf1
	 */
	public String getYf1() {
		return yf1;
	}
	/**
	 * @return the xf2
	 */
	public String getXf2() {
		return xf2;
	}
	/**
	 * @return the yf2
	 */
	public String getYf2() {
		return yf2;
	}
	
	
}
