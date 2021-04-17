package bobo4.flowgraph.simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxStyleUtils;

import bobo4.flowgraph.GraphData;
import bobo4.flowgraph.JGraphtGraph;

/***
 * jgxAdapter: This is a Graph Adapter to connect between JGrgaphT and JFrame
 * use print graph
 * 
 * @author kudos
 *
 */
public class GraphSimulation extends JApplet {
	private static final Dimension DEFAULT_SIZE = new Dimension(1130, 640);
	public JFrame frame;
	private JGraphXAdapter<String, DefaultWeightedEdge> jgxAdapter;
	private mxGraphComponent component;

	// All attribute of the vertex style you can declare in this hashmap
	private Map<Object, Object> vertexStyle = new HashMap<Object, Object>() {
		{
			put(mxConstants.STYLE_FONTSIZE, 20);
			put(mxConstants.STYLE_FILLCOLOR, "D98282");
			put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
			put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
			put(mxConstants.STYLE_FONTCOLOR, "FFFFFF");
		}
	};
	// All attribute of the edge style you can declare in this hashmap
	private Map<Object, Object> edgeStyle = new HashMap<Object, Object>() {
		{
//			put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ARROW);
		}
	};

	public GraphSimulation(GraphData graphData) {
		JGraphtGraph jGraphtGraph = new JGraphtGraph(graphData);
		jgxAdapter = new JGraphXAdapter<>(jGraphtGraph.getGraph());
		init();
	}

	public void init() {
		// Create a visualization using JGraph, via an adapter

		setPreferredSize(DEFAULT_SIZE);

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
			for (Map.Entry<Object, Object> e : vertexStyle.entrySet()) {
				myStyle.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(), e.getValue().toString());
			}

			// Modify the edge now
			jgxAdapter.clearSelection();

			jgxAdapter.selectEdges();
			cells = jgxAdapter.getSelectionCells();

			for (Map.Entry<Object, Object> e : edgeStyle.entrySet()) {
				myStyle.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(), e.getValue().toString());
			}
		} finally {
			jgxAdapter.clearSelection();
			jgxAdapter.getModel().endUpdate();
		}

		// Positioning via jGraphX layouts
		mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
//		layout.setDisableEdgeStyle(true);

		// Center the circle
		int radius = 300;
		layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
		layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
		layout.setRadius(radius);
		layout.setMoveCircle(true);
		layout.setDisableEdgeStyle(false);

		layout.execute(jgxAdapter.getDefaultParent());

		// Render into JPG b mxCellRender
		BufferedImage image = mxCellRenderer.createBufferedImage(jgxAdapter, null, 2, Color.WHITE, true, null);
		File imgFile = new File(".\\src\\main\\java\\bobo4\\flowgraph\\graph.jpg");
		try {
			ImageIO.write(image, "JPG", imgFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}
}
