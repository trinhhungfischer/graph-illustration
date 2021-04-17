package bobo4.flowgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

public class JGraphtGraph {
	private DirectedWeightedPseudograph<String, DefaultWeightedEdge> graph;
	
	public JGraphtGraph(GraphData graph) {
		this.graph = new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class);
		
		HashMap<String, NodeGraph> nodeList = graph.getNodeList();
		for(Map.Entry<String, NodeGraph> entry : nodeList.entrySet()) {
			NodeGraph node = entry.getValue();
			String nodeId = node.getId();
			
			this.graph.addVertex(nodeId);
		}
		
		for(Map.Entry<String, NodeGraph> entry : nodeList.entrySet()) {
			NodeGraph node = entry.getValue();
			
			String nodeId = node.getId();
			ArrayList<NodeGraph> nextNodeList = node.getNextNodeList();
			ArrayList<Integer> lengthEdgeList = node.getLengthEdgeList();
			
			for(int i = 0; i < nextNodeList.size(); i++) {
				DefaultWeightedEdge edge1 = this.graph.addEdge(nodeId, nextNodeList.get(i).getId());
				this.graph.setEdgeWeight(edge1, lengthEdgeList.get(i));
			}
		}
		
		System.out.println("JGraphtGraph " + this.graph.getVertexSupplier());
	}
	
	public GraphPath<Integer, DefaultWeightedEdge> getShortestPath(int nodeId1, int nodeId2) {
		DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(this.graph);
	    GraphPath<Integer, DefaultWeightedEdge> shortestPath = dijkstraShortestPath.getPath(nodeId1, nodeId2);
	    return shortestPath;
	}
	
	public DirectedWeightedPseudograph<String, DefaultWeightedEdge> getGraph(){
		return this.graph;
	}
}
