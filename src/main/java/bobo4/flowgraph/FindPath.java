package bobo4.flowgraph;

import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.*;
import java.util.*;

import javax.swing.JOptionPane;

public class FindPath {
	private ListenableGraph<String, FlowEdge> graph;
	List<GraphPath<String, FlowEdge>> listPath = new ArrayList<GraphPath<String,FlowEdge>>();
	
	public FindPath(ListenableGraph<String, FlowEdge> graph)
	{
		this.graph = graph;
		AllDirectedPaths<String, FlowEdge> allPath = new AllDirectedPaths<>(this.graph);
		List<GraphPath<String, FlowEdge>> list = allPath.getAllPaths("1", Integer.toString(this.graph.vertexSet().size()), true, null);
		for (int i = 0; i < list.size(); i++)
		{
			listPath.add(list.get(i));
		}
	
	}
	
	public ListenableGraph<String, FlowEdge> getGraph() {
		return graph;
	}

	public void setGraph(ListenableGraph<String, FlowEdge> graph) {
		this.graph = graph;
	}
	
	public void print()
	{
		String[] strPath = new String[listPath.size()];
		for (int i = 0; i < strPath.length; i++) strPath[i] = "";
		for (int i = 0; i < listPath.size(); i++)
		{
			strPath[i] += "1";
			for (FlowEdge edge : listPath.get(i).getEdgeList())
			{
				strPath[i] += " => " + edge.getTarget();
			}
		}
		String strPrint = String.join("\n", strPath);
		JOptionPane.showMessageDialog(null, strPrint, "All Path", JOptionPane.INFORMATION_MESSAGE);
	}
}
