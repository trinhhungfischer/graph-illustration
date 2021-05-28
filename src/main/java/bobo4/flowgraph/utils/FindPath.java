package bobo4.flowgraph.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import org.jgrapht.GraphPath;
import org.jgrapht.ListenableGraph;
import org.jgrapht.alg.shortestpath.YenKShortestPath;

import bobo4.flowgraph.elements.FlowEdge;
import bobo4.flowgraph.elements.Graph;
import bobo4.flowgraph.exception.WrongVertexException;
import bobo4.flowgraph.readgraph.ReadGraph;

public class FindPath {
	private ListenableGraph<String, FlowEdge> graph;
	private static List<GraphPath<String, FlowEdge>> listPath = new ArrayList<GraphPath<String, FlowEdge>>();

	public static List<GraphPath<String, FlowEdge>> getListPath() {
		return listPath;
	}

	public static void setListPath(List<GraphPath<String, FlowEdge>> listPath) {
		FindPath.listPath = listPath;
	}

	public FindPath(String startNode, String endNode) {
		this.graph = ReadGraph.getGraph();
		listPath.clear();
		YenKShortestPath<String, FlowEdge> somePath = new YenKShortestPath<>(this.graph);
		if (endNode.equals("")) {
			String lastNode = Integer.toString(graph.vertexSet().size());
			FindPath.listPath = somePath.getPaths(startNode, lastNode, 10);
		} else {
			FindPath.listPath = somePath.getPaths(startNode, endNode, 10);
		}
	}

	public ListenableGraph<String, FlowEdge> getGraph() {
		return graph;
	}

	public void setGraph(ListenableGraph<String, FlowEdge> graph) {
		this.graph = graph;
	}

	public void print() {
		String[] strPath = new String[listPath.size()];

		if (listPath.size() == 0) {

		} else {
			for (int i = 0; i < strPath.length; i++)
				strPath[i] = "";
			Graph myGraph = new Graph();
			myGraph.init();
			for (int i = 0; i < listPath.size(); i++) {
				strPath[i] += "1";
				try {
					myGraph.paintNode("1", 0);
				} catch (WrongVertexException e) {
						e.printStackTrace();
				}
				for (FlowEdge edge : listPath.get(i).getEdgeList()) {
					strPath[i] += " => " + edge.getTarget();
				}
				myGraph.paintPath(listPath.get(i));
				myGraph.saveImage(false);
				myGraph.repaintGraph();
			}
			String strPrint = String.join("\n", strPath);
			JOptionPane.showMessageDialog(null, strPrint, "All Path", JOptionPane.INFORMATION_MESSAGE);

		}
	}

	public static GraphPath<String, FlowEdge> getRandomPath() {
		int indexPath = new Random().nextInt(listPath.size());
		return listPath.get(indexPath);
	}
}
