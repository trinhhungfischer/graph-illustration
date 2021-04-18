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
	private JButton buttonNext;
	private JTextField startPointText;
	private JTextField endPointText;
	private GUIMoving guiMoving;
	public GUIMenu(GUIMoving guiMoving) {
		this.guiMoving = guiMoving;
		initStartPoint();
		initEndPoint();
		initButtonNext();
		initButtonLego();
		initButtonReset();
	}
	
	private void initButtonLego() {
		ActionListener action =  new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				buttonLego.setEnabled(false);
				startPointText.setEnabled(false);
				endPointText.setEnabled(false);
				buttonNext.setEnabled(true);
				startMove();
	        }  
		};
		
		buttonLego = new JButton("LET'S GO");
		buttonLego.setEnabled(true);
		buttonLego.setBounds(100,100,95,30);  
		buttonLego.addActionListener(action);
		
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
				buttonNext.setEnabled(false);
				reset();
	        }  
		});
		
		add(buttonReset);
	}
	
	private void initButtonNext() {
		ActionListener action =  new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				nextMove();
	        }  
		};
		
		buttonNext = new JButton("NEXT");
		buttonNext.setEnabled(false);
		buttonNext.setBounds(50,100,95,30);  
		buttonNext.addActionListener(action);
		
		add(buttonNext);
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
	
	private void startMove() {
		guiMoving.startMove(startPointText.getText(), endPointText.getText());
	}
	
	private void nextMove() {
		guiMoving.nextMove();
	}
	
	private void reset() {
		guiMoving.reset();
	}
}