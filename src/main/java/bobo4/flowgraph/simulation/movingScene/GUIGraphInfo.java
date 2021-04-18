package bobo4.flowgraph.simulation.movingScene;

import javax.swing.JPanel;

import bobo4.flowgraph.simulation.graphSimulation.GraphSimulation;

public class GUIGraphInfo extends JPanel{
	GraphSimulation graphSimulation;
	
	public GUIGraphInfo(GraphSimulation graphSimulation) {
		add(graphSimulation);
	}
}
