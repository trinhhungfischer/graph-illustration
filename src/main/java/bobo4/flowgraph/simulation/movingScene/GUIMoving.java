package bobo4.flowgraph.simulation.movingScene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.jgrapht.graph.DefaultWeightedEdge;

import bobo4.flowgraph.GraphData;
import bobo4.flowgraph.JGraphtGraph;
import bobo4.flowgraph.NodeGraph;
import bobo4.flowgraph.simulation.graphSimulation.GraphSimulation;

public class GUIMoving extends JFrame{
	private GUIGraphInfo guiGraphInfo;
	private GUIMovingInfo guiMovingInfo;
	private GUIMenu guiMenu;
	private JPanel topPanel;
	private JSplitPane splitPane;
	private JGraphtGraph graph;
	private NodeGraph startPoint;
	private NodeGraph endPoint;
	private NodeGraph curPoint;
	private int totalLenPassed;
	private GraphData graphData;
	
	public GUIMoving(GraphSimulation graphSimulation) {
		graphData = graphSimulation.getGraphData();
		this.setSize(1024, 600);
		
		guiGraphInfo = new GUIGraphInfo(graphSimulation);
		guiMovingInfo = new GUIMovingInfo();
		guiMenu = new GUIMenu(this);
		
		JSplitPane topPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(guiGraphInfo), new JScrollPane(guiMovingInfo));
		topPanel.setDividerLocation(700);
		
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, guiMenu);
		splitPane.setDividerLocation(510);
		splitPane.setResizeWeight(0);
		
		add(splitPane);
	}
	
	public void startMove(String startPoint, String endPoint) {
		this.startPoint = graphData.getNodeById(startPoint);
		this.endPoint = graphData.getNodeById(endPoint);
		this.curPoint = this.startPoint;
		this.totalLenPassed = 0;
		
		this.guiMovingInfo.addNewStep(curPoint.getId(), this.totalLenPassed);
	}
	
	public void nextMove() {
		ArrayList<NodeGraph> nextNodeList = curPoint.getNextNodeList();
		ArrayList<Integer> lengthEdgeList = curPoint.getLengthEdgeList();
		
		this.curPoint = nextNodeList.get(0);
		this.totalLenPassed += lengthEdgeList.get(0);
		
		this.guiMovingInfo.addNewStep(curPoint.getId(), this.totalLenPassed);
	}
	
	public void reset() {
		guiMovingInfo.removeAll();
		guiMovingInfo.repaint();
		guiMovingInfo.reset();
	}
}
