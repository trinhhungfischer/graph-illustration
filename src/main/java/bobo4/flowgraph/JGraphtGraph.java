package bobo4.flowgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

public class JGraphtGraph {
	private DirectedWeightedPseudograph<Integer, DefaultWeightedEdge> graph;
	
	public JGraphtGraph(Graph graph) {
		this.graph = new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class);
		
		HashMap<Integer, NodeGraph> nodeList = graph.getNodeList();
		for(Map.Entry<Integer, NodeGraph> entry : nodeList.entrySet()) {
			NodeGraph node = entry.getValue();
			
			int nodeId = node.getId();
			ArrayList<NodeGraph> nextNodeList = node.getNextNodeList();
			ArrayList<Integer> lengthEdgeList = node.getLengthEdgeList();
			
			this.graph.addVertex(nodeId);
			
			for(int i = 0; i < nextNodeList.size(); i++) {
				DefaultWeightedEdge edge1 = this.graph.addEdge(nodeId, nextNodeList.get(i).getId());
				this.graph.setEdgeWeight(edge1, lengthEdgeList.get(i));
			}
		}
	}
	
	public GraphPath<Integer, DefaultWeightedEdge> getShortestPath(int nodeId1, int nodeId2) {
		DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(this.graph);
	    GraphPath<Integer, DefaultWeightedEdge> shortestPath = dijkstraShortestPath.getPath(nodeId1, nodeId2);
	    return shortestPath;
	}
}
