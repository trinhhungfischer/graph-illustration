package bobo4.flowgraph;

import java.util.ArrayList;

public class NodeGraph {
	String id;
	private ArrayList<NodeGraph> nextNodeList;
	private ArrayList<Integer> lengthEdgeList;
	
	public NodeGraph(String id) {
		this.id = id;
		this.nextNodeList = new ArrayList<>();
		this.lengthEdgeList = new ArrayList<>();
	}
	
	public void addNextNode(NodeGraph node, int length) {
		this.nextNodeList.add(node);
		this.lengthEdgeList.add(length);
	}
	
	public ArrayList<NodeGraph> getNextNodeList(){
		return this.nextNodeList;
	}
	
	public ArrayList<Integer> getLengthEdgeList(){
		return this.lengthEdgeList;
	}
	
	public String getId() {
		return this.id;
	}
}
