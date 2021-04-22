package bobo4.flowgraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultListenableGraph;

public class ReadGraph {
	// This is a Graph Adapter to connect between JGrgaphT and JFrame use print
	// graph
	private JGraphXAdapter<String, FlowEdge> jgxAdapter;

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
			// Need to improve that can get current file of App and read graph txt
			File graphFile = new File(".\\src\\main\\java\\bobo4\\flowgraph\\graph.txt");
			// Open file to read by scanner
			Scanner fileReader = new Scanner(graphFile);

			while (fileReader.hasNextLine()) {
				String data = fileReader.nextLine();
				String[] verticesList = data.strip().split(" ");

				this.graph.addVertex(verticesList[0]);

				for (int i = 1; i < verticesList.length; i++) {
					this.graph.addVertex(verticesList[i]);
					this.graph.addEdge(verticesList[0], verticesList[i]);
				}
			}

			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred");
			e.printStackTrace();
		}
	}
}