package bobo4.flowgraph.simulation.movingScene;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIMenu extends JPanel{
	private JButton buttonLego;
	private JButton buttonReset;
	private JTextField startPointText;
	private JTextField endPointText;
	public GUIMenu() {
		initStartPoint();
		initEndPoint();
		initButtonLego();
		initButtonReset();
	}
	
	private void initButtonLego() {
		buttonLego = new JButton("LET'S GO");
		buttonLego.setEnabled(true);
		buttonLego.setBounds(100,100,95,30);  
		buttonLego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				buttonLego.setEnabled(false);
				startPointText.setEnabled(false);
				endPointText.setEnabled(false);
	        }  
		});
		
		add(buttonLego);
	}
	
	private void initButtonReset() {
		buttonReset = new JButton("RESET");
		buttonReset.setEnabled(true);
		buttonReset.setBounds(50,100,95,30);  
		buttonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				buttonLego.setEnabled(true);
				startPointText.setEnabled(true);
				endPointText.setEnabled(true);
	        }  
		});
		
		add(buttonReset);
	}
	
	private void initStartPoint() {
		JLabel label = new JLabel("Start Point:");
		add(label);
		startPointText = new JTextField(9);
		add(startPointText);
	}
	
	private void initEndPoint() {
		JLabel label = new JLabel("End Point:");
		add(label);
		endPointText = new JTextField(9);
		add(endPointText);
	}
}