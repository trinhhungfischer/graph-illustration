package bobo4.flowgraph.utils;

import org.jgrapht.*;
import org.jgrapht.alg.interfaces.KShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.*;

import bobo4.flowgraph.elements.FlowEdge;
import bobo4.flowgraph.elements.Graph;

import java.util.*;

import javax.swing.JOptionPane;

public class FindPath {
	private ListenableGraph<String, FlowEdge> graph;
	private List<GraphPath<String, FlowEdge>> listPath = new ArrayList<GraphPath<String,FlowEdge>>();
	
	public List<GraphPath<String, FlowEdge>> getListPath() {
		return listPath;
	}

	public void setListPath(List<GraphPath<String, FlowEdge>> listPath) {
		this.listPath = listPath;
	}

	public FindPath(ListenableGraph<String, FlowEdge> graph)
	{
		this.graph = graph;
//		AllDirectedPaths<String, FlowEdge> allPath = new AllDirectedPaths<>(this.graph);
//		List<GraphPath<String, FlowEdge>> list = allPath.getAllPaths("1", Integer.toString(this.graph.vertexSet().size()), true, 200);
		
		YenKShortestPath<String, FlowEdge> somePath = new YenKShortestPath<>(this.graph);
		List<GraphPath<String, FlowEdge>> list = somePath.getPaths("1", Integer.toString(this.graph.vertexSet().size()), 10);
		
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
		
		if (listPath.size() == 0)
		{
			
		}
		else
		{
			for (int i = 0; i < strPath.length; i++) strPath[i] = "";
			Graph myGraph = new Graph();
			myGraph.init();
			for (int i = 0; i < listPath.size(); i++)
			{
				strPath[i] += "1";
				myGraph.paintNode("1", 0);
				for (FlowEdge edge : listPath.get(i).getEdgeList())
				{
					strPath[i] += " => " + edge.getTarget();
					myGraph.paintNode(edge.getTarget().toString(), 0);
					myGraph.paintEdge(edge.getSource().toString(), edge.getTarget().toString(), 0);
				}
				myGraph.saveImage();
				myGraph.repaintGraph();
			}
			String strPrint = String.join("\n", strPath);
			JOptionPane.showMessageDialog(null, strPrint, "All Path", JOptionPane.INFORMATION_MESSAGE);
			
		}
		for (int i = 0; i < listPath.size(); i++)
		{
			
			for (FlowEdge edge : listPath.get(i).getEdgeList())
			{
				strPath[i] += " => " + edge.getTarget();
			}
		}
		
	}
}
