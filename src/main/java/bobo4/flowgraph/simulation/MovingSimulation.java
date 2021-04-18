package bobo4.flowgraph.simulation;

import bobo4.flowgraph.GraphData;
import bobo4.flowgraph.simulation.graphSimulation.GraphSimulation;
import bobo4.flowgraph.simulation.movingScene.GUIMoving;

public class MovingSimulation implements Simulation {

	public MovingSimulation() {
		
	}
	
	@Override
	public void simulate(GraphData graphData) {
		GraphSimulation graphSimulation = new GraphSimulation(graphData);
		
		GUIMoving guiMoving = new GUIMoving(graphSimulation);
		guiMoving.setVisible(true);
	}
	
}
