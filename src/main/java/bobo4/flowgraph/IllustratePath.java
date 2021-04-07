package bobo4.flowgraph;

import com.mxgraph.layout.*;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.*;
import com.mxgraph.util.*;

import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

import java.util.*;
import java.util.List;

public class IllustratePath extends JApplet {
	private static final Dimension DEFAULT_SIZE = new Dimension(1920, 1080);

	public JFrame frame = new JFrame("Path Demmo");
	// This is a Graph Adapter to connect between JGrgaphT and JFrame use print graph
	public JGraphXAdapter<String, FlowEdge> jgxAdapter;

	private ListenableGraph<String, FlowEdge> graph = new Graph().getGraph();

	private List<GraphPath<String, FlowEdge>> graphPath = new FindPath(this.graph).getListPath();
	
	private GraphPath<String, FlowEdge> path = graphPath.get(0);
	private static int index = 0;
	private int maxElement = path.getVertexList().size() + path.getEdgeList().size();
	
	private mxGraphComponent component;
	
	private HashMap<FlowEdge, mxICell> edgeToCellMap;
	private HashMap<String, mxICell> vertexToCellMap;
	
	// All attribute of the vertex style you can declare in this hashmap
	private Map<Object, Object> vertexDefaultStyle = new HashMap<Object, Object>() {
		{
			put(mxConstants.STYLE_FONTSIZE, 20);
			put(mxConstants.STYLE_FILLCOLOR, "D98282");
			put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
			put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
			put(mxConstants.STYLE_FONTCOLOR, "FFFFFF");
		}
	};
	// All attribute of the edge style you can declare in this hashmap
	private Map<Object, Object> edgeDefaultStyle = new HashMap<Object, Object>() {
		{
//			put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ARROW);
		}
	};
	
	private Map<Object, Object> vertexAfterStyle = new HashMap<Object, Object>() {
		{
			put(mxConstants.STYLE_FONTSIZE, 20);
			put(mxConstants.STYLE_FILLCOLOR, "BD2BE8");
//			put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
			put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
			put(mxConstants.STYLE_FONTCOLOR, "FFFFFF");
		}
	};
	// All attribute of the edge style you can declare in this hashmap
	private Map<Object, Object> edgeAfterStyle = new HashMap<Object, Object>() {
		{
			put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ARROW);
		}
	};

	public IllustratePath () {
		// Create a visualization using JGraph, via an adapter
		
		jgxAdapter = new JGraphXAdapter<>(graph);
				
		edgeToCellMap = jgxAdapter.getEdgeToCellMap();
		vertexToCellMap = jgxAdapter.getVertexToCellMap();
		System.out.println(path.getEdgeList().size());
		System.out.println(path.getVertexList().size());

	}

	public void printOnePath() throws InterruptedException {
		IllustratePath applet = new IllustratePath();
		
		frame.setTitle("Path Demo");
		
		applet.init();
		frame.getContentPane().add(applet);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Thread.sleep(1000);
		frame.setVisible(false);
		
		for (; index < maxElement; )
		{
			applet.init();
			frame.pack();
			
			frame.getContentPane().add(applet);
			frame.setVisible(true);
		
			Thread.sleep(2000);
			frame.setVisible(false);
		}
		frame.setVisible(true);
		
		applet.destroy();
	}

	
	@Override
	public void init() {
		
		setPreferredSize(DEFAULT_SIZE);
		// TODO Auto-generated method stub
		component = new mxGraphComponent(jgxAdapter);
		component.setConnectable(false);
		component.getGraph().setAllowDanglingEdges(false);
		getContentPane().add(component);
		resize(DEFAULT_SIZE);

		jgxAdapter.getModel().beginUpdate();
		try {
			jgxAdapter.clearSelection();
			jgxAdapter.selectAll();
			Object[] cells = jgxAdapter.getSelectionCells();

			mxStyleUtils myStyle = new mxStyleUtils();

			// Clear all selection before of jGraphXAdpter
			jgxAdapter.clearSelection();

			jgxAdapter.selectVertices();
			cells = jgxAdapter.getSelectionCells();
			for (Object c : cells) {
				mxCell cell = (mxCell) c;
				mxGeometry geometry = cell.getGeometry();
				if (cell.isVertex()) {
					geometry.setWidth(50); // This is size of Circle
					geometry.setHeight(50); // This is size of other radius Circle
				}
			}
			for (Map.Entry<Object, Object> e : vertexDefaultStyle.entrySet()) {
				myStyle.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(), e.getValue().toString());
			}
			
			// Modify the edge now
			jgxAdapter.clearSelection();
	
			jgxAdapter.selectEdges();
			cells = jgxAdapter.getSelectionCells();
			
			for (Map.Entry<Object, Object> e : edgeDefaultStyle.entrySet()) {
				myStyle.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(), e.getValue().toString());
			}

			Object[] vertexList = new Object[path.getVertexList().size()];
			Object[] edgeList = new Object[path.getEdgeList().size()];
			
			
			for (int i = 0; i <= index; i++)
			{
				
				if ((i % 2) == 0)
				{
					Object cell = (Object)vertexToCellMap.get(path.getVertexList().get(i / 2));
					vertexList[i / 2] = cell;
				} 
				else {
					if (index > maxElement - 1)
						break;
					else
					{
					Object cell = (Object)edgeToCellMap.get(path.getEdgeList().get(i / 2));
					edgeList[i / 2] = cell;
					}
				}
			}
			
			jgxAdapter.clearSelection();
			jgxAdapter.setSelectionCells(vertexList);
			cells = jgxAdapter.getSelectionCells();
			for (Map.Entry<Object, Object> e : vertexAfterStyle.entrySet()) {
				new mxStyleUtils().setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(), e.getValue().toString());
			}
			
			jgxAdapter.clearSelection();
			jgxAdapter.setSelectionCells(edgeList);
			cells = jgxAdapter.getSelectionCells();
			for (Map.Entry<Object, Object> e : edgeAfterStyle.entrySet()) {
				new mxStyleUtils().setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(), e.getValue().toString());
			}
			

			
		} finally {
			jgxAdapter.clearSelection();
			jgxAdapter.getModel().endUpdate();
			this.index ++;
		}
		

		// Positioning via jGraphX layouts
		mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
		layout.setDisableEdgeStyle(true);

		// Center the circle
		int radius = 300;
		layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
		layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
		layout.setRadius(radius);
		layout.setMoveCircle(true);
		layout.setDisableEdgeStyle(false);		
		

		layout.execute(jgxAdapter.getDefaultParent());
		
	}

}