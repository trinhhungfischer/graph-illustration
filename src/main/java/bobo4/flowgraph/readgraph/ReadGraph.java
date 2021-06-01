package bobo4.flowgraph.readgraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultListenableGraph;

import bobo4.flowgraph.elements.FlowEdge;

public class ReadGraph {
	private static ListenableGraph<String, FlowEdge> graph = new DefaultListenableGraph<>(
			new DefaultDirectedGraph<String, FlowEdge>(FlowEdge.class));

	public static ListenableGraph<String, FlowEdge> getGraph() {
		return graph;
	}

	public static void setGraph(ListenableGraph<String, FlowEdge> graph) {
		ReadGraph.graph = graph;
	}

	public ReadGraph(String DirectoryPath) {
		try {
			ReadGraph.graph = new DefaultListenableGraph<>(new DefaultDirectedGraph<String, FlowEdge>(FlowEdge.class));

			boolean[] isInGraph = new boolean[2000];
			// Need to improve that can get current file of App and read graph txt
			File graphFile = new File(DirectoryPath);
			// Open file to read by scanner
			Scanner fileReader = new Scanner(graphFile);

			while (fileReader.hasNextLine()) {
				String data = fileReader.nextLine();
				String[] verticesList = data.strip().split(" ");

				for (int i = 0; i < verticesList.length; i++) {
					int c = Integer.parseInt(verticesList[i]);
					if (isInGraph[c] == false) {
						ReadGraph.graph.addVertex(verticesList[i]);
						isInGraph[c] = true;
					}
					if (i >= 1)
						ReadGraph.graph.addEdge(verticesList[0], verticesList[i]);
				}
			}

			fileReader.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found or wrong file type", "Warning",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Acess denied", "Warning", JOptionPane.ERROR_MESSAGE);
		}
	}
}