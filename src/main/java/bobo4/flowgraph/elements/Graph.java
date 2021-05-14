package bobo4.flowgraph.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgrapht.GraphPath;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxStyleUtils;
import com.mxgraph.view.mxGraph;

import bobo4.flowgraph.readgraph.ReadGraph;
import bobo4.flowgraph.utils.FindPath;

public class Graph extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int numberImage = 1;
	
	private static final Dimension DEFAULT_SIZE = new Dimension(1080, 720);

	public JFrame frame = new JFrame("Path Demmo");
	// This is a Graph Adapter to connect between JGrgaphT and JFrame use print
	// graph
	public JGraphXAdapter<String, FlowEdge> jgxAdapter;

	private ListenableGraph<String, FlowEdge> graph = new ReadGraph().getGraph();

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
			put(mxConstants.STYLE_STROKECOLOR, "000000");
		}
	};

	private Map<Object, Object> vertexAfterStyle = new HashMap<Object, Object>() {
		{
			put(mxConstants.STYLE_FONTSIZE, 20);
			put(mxConstants.STYLE_FILLCOLOR, "BD2BE8");
//			put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
			put(mxConstants.STYLE_FONTCOLOR, "FFFFFF");
		}
	};
	// All attribute of the edge style you can declare in this hashmap
	private Map<Object, Object> edgeAfterStyle = new HashMap<Object, Object>() {
		{
//			put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ARROW);
			put(mxConstants.STYLE_FILLCOLOR, "F9F900");
			put(mxConstants.STYLE_STROKECOLOR, "FD0000");

		}
	};

	public Graph() {
		// Create a visualization using JGraph, via an adapter
		jgxAdapter = new JGraphXAdapter<>(graph);

		edgeToCellMap = jgxAdapter.getEdgeToCellMap();
		vertexToCellMap = jgxAdapter.getVertexToCellMap();

		setPreferredSize(DEFAULT_SIZE);
		// TODO Auto-generated method stub
		component = new mxGraphComponent(jgxAdapter);
		component.setConnectable(false);
		component.getGraph().setAllowDanglingEdges(false);
		getContentPane().add(component);

	}

	@Override
	public void init() {
		jgxAdapter.getModel().beginUpdate();
		try {
			jgxAdapter.clearSelection();
			jgxAdapter.selectAll();
			Object[] cells = jgxAdapter.getSelectionCells();

			// Clear all selection before of jGraphXAdpter
			jgxAdapter.clearSelection();

			jgxAdapter.selectVertices();
			cells = jgxAdapter.getSelectionCells();
			for (Object c : cells) {
				mxCell cell = (mxCell) c;
				mxGeometry geometry = cell.getGeometry();
				if (cell.isVertex()) {
					geometry.setWidth(40); // This is size of Circle
					geometry.setHeight(40); // This is size of other radius Circle
				}
			}
			for (Map.Entry<Object, Object> e : vertexDefaultStyle.entrySet()) {
				new mxStyleUtils();
				mxStyleUtils.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(), e.getValue().toString());
			}

			// Modify the edge now
			jgxAdapter.clearSelection();

			jgxAdapter.selectEdges();
			cells = jgxAdapter.getSelectionCells();

			for (Map.Entry<Object, Object> e : edgeDefaultStyle.entrySet()) {
				new mxStyleUtils();
				mxStyleUtils.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(), e.getValue().toString());
			}

		} finally {
			jgxAdapter.clearSelection();
			jgxAdapter.getModel().endUpdate();
		}

		mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);		
		layout.setMaxDistanceLimit(200.0f);
		layout.setMinDistanceLimit(2f);		
		layout.setInitialTemp(200f);
		layout.setMaxIterations(2000);
		layout.execute(jgxAdapter.getDefaultParent());
	}

	public void paintNode(String node, int mode) {
		// TODO Auto-generated method stub

		jgxAdapter.getModel().beginUpdate();
		try {
			Object[] cells;
			Object[] vertexList = new Object[1];

			Object cell = (Object) vertexToCellMap.get(node);
			vertexList[0] = cell;

			jgxAdapter.clearSelection();
			jgxAdapter.setSelectionCells(vertexList);
			cells = jgxAdapter.getSelectionCells();
			switch (mode) {
			case 0: {
				for (Map.Entry<Object, Object> e : vertexAfterStyle.entrySet()) {
					new mxStyleUtils();
					mxStyleUtils.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(),
							e.getValue().toString());
				}
				break;
			}
			case 1: {
				for (Map.Entry<Object, Object> e : vertexDefaultStyle.entrySet()) {
					new mxStyleUtils();
					mxStyleUtils.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(),
							e.getValue().toString());
				}
			}
			}

		} finally {
			jgxAdapter.clearSelection();
			jgxAdapter.getModel().endUpdate();
		}
	}

	public void paintEdge(String startVertex,String targetVertex, int mode) {
		// TODO Auto-generated method stub

		jgxAdapter.getModel().beginUpdate();
		try {
			Object[] cells;
			Object[] edgeList = new Object[1];

			Object cell = (Object) edgeToCellMap.get(this.graph.getEdge(startVertex, targetVertex));
			edgeList[0] = cell;

			jgxAdapter.clearSelection();
			jgxAdapter.setSelectionCells(edgeList);
			cells = jgxAdapter.getSelectionCells();
			switch (mode) {
			case 0: {
				for (Map.Entry<Object, Object> e : edgeAfterStyle.entrySet()) {
					new mxStyleUtils();
					mxStyleUtils.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(),
							e.getValue().toString());
				}
				break;
			}
			case 1: {
				for (Map.Entry<Object, Object> e : edgeDefaultStyle.entrySet()) {
					new mxStyleUtils();
					mxStyleUtils.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(),
							e.getValue().toString());
				}
			}
			}

		} finally {
			jgxAdapter.clearSelection();
			jgxAdapter.getModel().endUpdate();
		}
	}
	
	public List<String> getNextVertex(String currentVertex) {
		List<String> listNode = new ArrayList<>();
		for (FlowEdge edge : graph.outgoingEdgesOf(currentVertex)) {
			listNode.add((String) edge.getTarget());
		}

		return listNode;
	}

	public void repaintGraph()
	{
		jgxAdapter.getModel().beginUpdate();
		jgxAdapter.selectAll();
		Object[] cells = jgxAdapter.getSelectionCells();
		List<Object> vertexList = new ArrayList<>();
		List<Object> edgeList = new ArrayList<>();
				
		for (Object c : cells)
		{
			mxCell cell = (mxCell) c;
			if (cell.isVertex())
			{
				vertexList.add(c);
			}
			else if (cell.isEdge())
			{
				edgeList.add(c);
			}
		}
		for (Map.Entry<Object, Object> e : vertexDefaultStyle.entrySet()) {
			new mxStyleUtils();
			mxStyleUtils.setCellStyles(jgxAdapter.getModel(), vertexList.toArray(), e.getKey().toString(),
					e.getValue().toString());
		}
		
		for (Map.Entry<Object, Object> e : edgeDefaultStyle.entrySet()) {
			new mxStyleUtils();
			mxStyleUtils.setCellStyles(jgxAdapter.getModel(), edgeList.toArray(), e.getKey().toString(),
					e.getValue().toString());
		}
		jgxAdapter.clearSelection();
		jgxAdapter.getModel().endUpdate();
		
	}
	
	public void zoomIn() {
		component.setCenterZoom(component.isCenterPage());
		
		int newX = component.getBounds().width;
		int newY = component.getBounds().height;
		
		component.zoomIn();
		System.out.println(component.getBounds().height);
		System.out.println(component.getBounds().width);
		component.getViewport().setViewPosition(new Point(newX, newY));
		Object cell = (Object) vertexToCellMap.get("1");
		
		component.setCenterZoom(true);
		
	}

	public void zoomOut() {
		component.zoomOut();
		
		int newX = component.getBounds().width / 2;
		int newY = component.getBounds().height / 2;
		System.out.println(newX);
		
		
		
		component.getViewport().setViewPosition(new Point(newX, newY));
	}

	public void saveImage() {
		// Render into JPG b mxCellRender
		BufferedImage image = mxCellRenderer.createBufferedImage(jgxAdapter, null, 2, Color.WHITE, true, null);
		String str = ".\\src\\main\\java\\bobo4\\flowgraph\\asset\\graph_"+ numberImage +".jpg";
		this.numberImage += 1;
		File imgFile = new File(str);
		try {
			ImageIO.write(image, "JPG", imgFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}