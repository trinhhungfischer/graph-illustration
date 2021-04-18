package bobo4.flowgraph;

import java.util.*;

import javax.swing.JFrame;

import org.jgrapht.Graph;

import com.mxgraph.model.mxICell;

import bobo4.flowgraph.simulation.MovingSimulation;
import bobo4.flowgraph.simulation.Simulation;
import bobo4.flowgraph.simulation.graphSimulation.GraphSimulation;
import bobo4.flowgraph.simulation.movingScene.GUIMoving;

public class App {
	
	public static void main(String[] args) throws InterruptedException {
		GraphData graphData = new GraphData();
		
		Simulation simulation = new MovingSimulation();
		simulation.simulate(graphData);
	}
}
