package bobo4.flowgraph.simulation.movingScene;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import bobo4.flowgraph.JGraphtGraph;
import bobo4.flowgraph.simulation.GraphSimulation;

public class GUIMoving extends JFrame{
	GUIGraphInfo guiGraphInfo;
	GUIMovingInfo guiMovingInfo;
	GUIMenu guiMenu;
	JPanel topPanel;
	JSplitPane splitPane;
	JGraphtGraph graph;
	
	public GUIMoving() {
		this.setSize(1024, 600);
		
		guiGraphInfo = new GUIGraphInfo();
		guiMovingInfo = new GUIMovingInfo();
		guiMenu = new GUIMenu();
		
		JSplitPane topPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(guiGraphInfo), new JScrollPane(guiMovingInfo));
		topPanel.setDividerLocation(700);
		
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, guiMenu);
		splitPane.setDividerLocation(510);
		splitPane.setResizeWeight(0);
		
		add(splitPane);
	}
	
	public void addGraph(GraphSimulation graphSimulation) {
		guiGraphInfo.addGraphSimulation(graphSimulation);
	}
	
	public static void main(String[] args) {
		GUIMoving guiMoving = new GUIMoving();
		guiMoving.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		guiMoving.setVisible(true);
	}
}
