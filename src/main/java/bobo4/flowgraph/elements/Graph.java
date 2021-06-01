package bobo4.flowgraph.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import org.jgrapht.GraphPath;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxStyleUtils;

import bobo4.flowgraph.exception.WrongVertexException;
import bobo4.flowgraph.readgraph.ReadGraph;

public class Graph extends JScrollPane {

	private static final long serialVersionUID = 1L;

	private static int numberImage = 1;

	private static final Dimension DEFAULT_SIZE = new Dimension(1190, 750);

	public JFrame frame = new JFrame("Path Demmo");
	// This is a Graph Adapter to connect between JGrgaphT and JFrame use print
	// graph
	public JGraphXAdapter<String, FlowEdge> jgxAdapter;

	private ListenableGraph<String, FlowEdge> graph = ReadGraph.getGraph();

	private mxGraphComponent component;

	private HashMap<FlowEdge, mxICell> edgeToCellMap;
	private HashMap<String, mxICell> vertexToCellMap;
	public HashMap<mxICell, String> cellToVertexMap;

	// All attribute of the vertex style you can declare in this hash map

	public Graph() {
		// Create a visualization using JGraph, via an adapter
		jgxAdapter = new JGraphXAdapter<>(graph);
		jgxAdapter.setCellsResizable(false);
		jgxAdapter.setAutoSizeCells(true);

		edgeToCellMap = jgxAdapter.getEdgeToCellMap();
		vertexToCellMap = jgxAdapter.getVertexToCellMap();
		cellToVertexMap = jgxAdapter.getCellToVertexMap();

		setPreferredSize(DEFAULT_SIZE);
		component = new mxGraphComponent(jgxAdapter);
		component.setConnectable(false);
		component.getGraph().setAllowDanglingEdges(false);
		jgxAdapter.setCellsSelectable(true);
		this.init();

		add(component);
		setViewportView(component);
		setWheelScrollingEnabled(false);
		component.setWheelScrollingEnabled(false);
		component.addMouseWheelListener(new MyMouseListener());
	}

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
			for (Map.Entry<Object, Object> e : CellStyle.vertexDefaultStyle.entrySet()) {
				new mxStyleUtils();
				mxStyleUtils.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(),
						e.getValue().toString());
			}

			// Modify the edge now
			jgxAdapter.clearSelection();

			jgxAdapter.selectEdges();
			cells = jgxAdapter.getSelectionCells();

			for (Map.Entry<Object, Object> e : CellStyle.edgeDefaultStyle.entrySet()) {
				new mxStyleUtils();
				mxStyleUtils.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(),
						e.getValue().toString());
			}

		} finally {
			jgxAdapter.clearSelection();
			jgxAdapter.getModel().endUpdate();
		}

		mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
		layout.setMaxDistanceLimit(200.0f);
		layout.setMinDistanceLimit(0.5f);
		layout.setInitialTemp(200f);
		layout.setMaxIterations(2000);
		layout.execute(jgxAdapter.getDefaultParent());
	}

	public void paintNode(String node, int mode) throws WrongVertexException {
		jgxAdapter.getModel().beginUpdate();

		if (Integer.parseInt(node) > graph.vertexSet().size())
			throw new WrongVertexException();
		else
			try {
				Map<Object, Object> vertexStyle = null;
				switch (mode) {
				case 0: {
					vertexStyle = CellStyle.vertexAfterStyle;
					break;
				}
				case 1: {
					vertexStyle = CellStyle.vertexDefaultStyle;
					break;
				}
				case 2: {
					vertexStyle = CellStyle.vertexCurrentStyle;
					break;
				}
				}

				Object[] cells = { (Object) vertexToCellMap.get(node) };

				for (Map.Entry<Object, Object> e : vertexStyle.entrySet()) {
					new mxStyleUtils();
					mxStyleUtils.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(),
							e.getValue().toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				jgxAdapter.clearSelection();
				jgxAdapter.getModel().endUpdate();
			}

	}

	public void paintEdge(String startVertex, String targetVertex, int mode) {

		jgxAdapter.getModel().beginUpdate();
		try {
			Object[] cells = { (Object) edgeToCellMap.get(this.graph.getEdge(startVertex, targetVertex)) };
			Map<Object, Object> edgeStyle = null;
			switch (mode) {
			case 0: {
				edgeStyle = CellStyle.edgeAfterStyle;
				break;
			}
			case 1: {
				edgeStyle = CellStyle.edgeDefaultStyle;
				break;
			}
			}
			for (Map.Entry<Object, Object> e : edgeStyle.entrySet()) {
				new mxStyleUtils();
				mxStyleUtils.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(),
						e.getValue().toString());
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

	public void repaintGraph() {
		jgxAdapter.getModel().beginUpdate();
		jgxAdapter.selectAll();
		Object[] cells = jgxAdapter.getSelectionCells();
		List<Object> vertexList = new ArrayList<>();
		List<Object> edgeList = new ArrayList<>();

		for (Object c : cells) {
			mxCell cell = (mxCell) c;
			if (cell.isVertex()) {
				vertexList.add(c);
			} else if (cell.isEdge()) {
				edgeList.add(c);
			}
		}
		for (Map.Entry<Object, Object> e : CellStyle.vertexDefaultStyle.entrySet()) {
			new mxStyleUtils();
			mxStyleUtils.setCellStyles(jgxAdapter.getModel(), vertexList.toArray(), e.getKey().toString(),
					e.getValue().toString());
		}

		for (Map.Entry<Object, Object> e : CellStyle.edgeDefaultStyle.entrySet()) {
			new mxStyleUtils();
			mxStyleUtils.setCellStyles(jgxAdapter.getModel(), edgeList.toArray(), e.getKey().toString(),
					e.getValue().toString());
		}
		jgxAdapter.clearSelection();
		jgxAdapter.getModel().endUpdate();

	}

	public void zoomIn(int x, int y) {
		component.setCenterZoom(component.isCenterPage());
		Point leftPoint = new Point();
		leftPoint.setLocation(x, y);

		component.zoomIn();
		component.scrollRectToVisible(new Rectangle(leftPoint, component.getViewport().getSize()));
	}

	public void zoomOut(int x, int y) {
		component.setCenterZoom(component.isCenterPage());
		Point leftPoint = new Point();
		leftPoint.setLocation(x, y);

		component.zoomOut();
		component.scrollRectToVisible(new Rectangle(leftPoint, component.getViewport().getSize()));
	}

	public void saveImage(boolean isNotify) {
		// Render into JPG b mxCellRender
		BufferedImage image = mxCellRenderer.createBufferedImage(jgxAdapter, null, 2, Color.WHITE, true, null);
		String str = ".\\src\\main\\java\\bobo4\\flowgraph\\asset\\images\\graph_" + numberImage + ".jpg";
		
		File imgFile = new File(str);
		try {
			ImageIO.write(image, "JPG", imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (isNotify)
			JOptionPane.showMessageDialog(null,
					"Save graph as image graph_" + numberImage + ".jpg at folder\n"
							+ "./src/main/java/bobo4/flowgraph/asset/images",
					"Notice", JOptionPane.INFORMATION_MESSAGE);
		Graph.numberImage += 1;

	}

	public void paintPath(GraphPath<String, FlowEdge> path) {
		ArrayList<Object> vertices = new ArrayList<>();
		ArrayList<Object> edges = new ArrayList<>();

		for (int i = 0; i < path.getVertexList().size(); i++) {
			vertices.add(vertexToCellMap.get(path.getVertexList().get(i)));
		}

		for (int i = 0; i < path.getEdgeList().size(); i++) {
			edges.add(edgeToCellMap.get(path.getEdgeList().get(i)));
		}

		// Beginning update jgxGraph model here
		jgxAdapter.getModel().beginUpdate();
		// Paint Edge here
		for (Map.Entry<Object, Object> e : CellStyle.edgeAfterStyle.entrySet()) {
			new mxStyleUtils();
			mxStyleUtils.setCellStyles(jgxAdapter.getModel(), edges.toArray(), e.getKey().toString(),
					e.getValue().toString());
		}
		// Paint node here
		for (Map.Entry<Object, Object> e : CellStyle.vertexAfterStyle.entrySet()) {
			new mxStyleUtils();
			mxStyleUtils.setCellStyles(jgxAdapter.getModel(), vertices.toArray(), e.getKey().toString(),
					e.getValue().toString());
		}
		jgxAdapter.getModel().endUpdate();

	}

	// Inner class to solve mouse listener
	private class MyMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

		Point startPoint = new Point();

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if (e.getWheelRotation() > 0)
				zoomOut(e.getX(), e.getY());
			else
				zoomIn(e.getX(), e.getY());
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			JViewport vport = (JViewport) e.getSource();
			Point pt = e.getPoint();
			int dx = startPoint.x - pt.x;
			int dy = startPoint.y - pt.y;
			Point vp = vport.getViewPosition();
			vp.translate(dx, dy);
			component.scrollRectToVisible(new Rectangle(vp, vport.getSize()));
			startPoint.setLocation(pt);
		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			startPoint = e.getPoint();
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}
}