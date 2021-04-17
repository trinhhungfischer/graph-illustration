package bobo4.flowgraph;

public class Edge {
	private NodeGraph node1;
	private NodeGraph node2;
	private int length;

	public Edge(NodeGraph node1, NodeGraph node2, int lenght) {
		this.node1 = node1;
		this.node2 = node2;
		this.length = length;
	}
}
