package bobo4.flowgraph;

import java.util.*;

import javax.swing.JFrame;

import org.jgrapht.Graph;

import com.mxgraph.model.mxICell;

import bobo4.flowgraph.simulation.GraphSimulation;
import bobo4.flowgraph.simulation.movingScene.GUIMoving;

public class App {
	
	public static void main(String[] args) throws InterruptedException {
		GraphData graphData = new GraphData();
		GraphSimulation graphSimulation = new GraphSimulation(graphData);
		
		GUIMoving guiMoving = new GUIMoving();
		guiMoving.addGraph(graphSimulation);
		guiMoving.setVisible(true);
	}
}
