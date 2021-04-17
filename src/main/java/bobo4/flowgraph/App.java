package bobo4.flowgraph;

import java.util.*;

import javax.swing.JFrame;

import org.jgrapht.Graph;

import com.mxgraph.model.mxICell;

import bobo4.flowgraph.simulation.GraphSimulation;

public class App {
	
	public static void main(String[] args) throws InterruptedException {
		GraphData graphData = new GraphData();
		JFrame frame = new JFrame();
		GraphSimulation graphSimulation = new GraphSimulation(graphData);
		
		frame.getContentPane().add(graphSimulation);
		frame.setTitle("Flow Graph Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
