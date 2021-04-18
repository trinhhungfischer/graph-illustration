package bobo4.flowgraph.simulation.movingScene;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import bobo4.flowgraph.NodeGraph;

public class GUIMovingInfo extends JPanel{
	private int stepCount;
	private JLabel totalLenPassed;
	public GUIMovingInfo() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		totalLenPassed = new JLabel();
		totalLenPassed.setText("TOTAL LENGTH PASSES = 0");
		add(totalLenPassed);
		
		setVisible(true);
		totalLenPassed.setVisible(true);
	}
	
	public void addNewStep(String nodeId, int len) {
		totalLenPassed.setText("TOTAL LENGTH PASSES = " + String.valueOf(len));
		
		JLabel label = new JLabel();
		label.setText("step " + (++stepCount) + ": " + nodeId);
		label.setVisible(true);
		add(label);
	}
	
	public void reset() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		stepCount = 0;
		
		totalLenPassed = new JLabel();
		totalLenPassed.setText("TOTAL LENGTH PASSES = 0");
		add(totalLenPassed);
		
		setVisible(true);
		totalLenPassed.setVisible(true);
	}
}
