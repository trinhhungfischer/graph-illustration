package bobo4.flowgraph.simulation.movingScene;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GUIMoving extends JFrame{
	GUIGraphInfo guiGraphInfo = new GUIGraphInfo();
	GUIMovingInfo guiMovingInfo = new GUIMovingInfo();
	GUIMenu guiMenu = new GUIMenu();

	JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel(), guiMenu);
	
	
	public GUIMoving() {
		add(splitPane);
	}
	
	public static void main(String[] args) {
		GUIMoving guiMoving = new GUIMoving();
		guiMoving.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		guiMoving.setSize(1024, 600);
		guiMoving.setVisible(true);
	}
}
