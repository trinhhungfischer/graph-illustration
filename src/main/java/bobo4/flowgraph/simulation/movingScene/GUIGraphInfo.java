package bobo4.flowgraph.simulation.movingScene;

import javax.swing.JPanel;

import bobo4.flowgraph.simulation.GraphSimulation;

public class GUIGraphInfo extends JPanel{
	GraphSimulation graphSimulation;
	
	public GUIGraphInfo() {
	}
	
	public void addGraphSimulation(GraphSimulation graphSimulation) {
		add(graphSimulation);
	}
}
