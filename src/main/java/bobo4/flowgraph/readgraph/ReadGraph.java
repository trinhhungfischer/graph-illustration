package bobo4.flowgraph.readgraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultListenableGraph;

import bobo4.flowgraph.elements.FlowEdge;

public class ReadGraph {
	private static ListenableGraph<String, FlowEdge> graph = new DefaultListenableGraph<>(
			new DefaultDirectedGraph<String, FlowEdge>(FlowEdge.class));

	public ListenableGraph<String, FlowEdge> getGraph() {
		return graph;
	}

	public void setGraph(ListenableGraph<String, FlowEdge> graph) {
		this.graph = graph;
	}

	public ReadGraph() {
		try {
			boolean[] isInGraph = new boolean[300];
			// Need to improve that can get current file of App and read graph txt
			File graphFile = new File(".\\src\\main\\java\\bobo4\\flowgraph\\asset\\graph.txt");
			// Open file to read by scanner
			Scanner fileReader = new Scanner(graphFile);

			while (fileReader.hasNextLine()) {
				String data = fileReader.nextLine();
				String[] verticesList = data.strip().split(" ");

				for (int i = 0; i < verticesList.length; i++) {
					int c = Integer.parseInt(verticesList[i]);
					if (isInGraph[c] == false)
					{
						this.graph.addVertex(verticesList[i]);
						isInGraph[c] = true;
					}
					if (i >= 1) this.graph.addEdge(verticesList[0], verticesList[i]);
				}
			}

			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred");
			e.printStackTrace();
		}
	}
}