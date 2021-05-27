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
import java.util.concurrent.ThreadFactory;

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
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxStyleUtils;

import bobo4.flowgraph.exception.WrongVertexException;
import bobo4.flowgraph.readgraph.ReadGraph;
import bobo4.flowgraph.utils.GraphIllustrate;
import bobo4.flowgraph.utils.Mutex;

public class Graph extends JScrollPane implements Runnable {
	/**
	 * 
	 */
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
	// All attribute of the edge style you can declare in this hash map
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
	// All attribute of the edge style you can declare in this hash map
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
		cellToVertexMap = jgxAdapter.getCellToVertexMap();

		setPreferredSize(DEFAULT_SIZE);
		// TODO Auto-generated method stub
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
			for (Map.Entry<Object, Object> e : vertexDefaultStyle.entrySet()) {
				new mxStyleUtils();
				mxStyleUtils.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString(),
						e.getValue().toString());
			}

			// Modify the edge now
			jgxAdapter.clearSelection();

			jgxAdapter.selectEdges();
			cells = jgxAdapter.getSelectionCells();

			for (Map.Entry<Object, Object> e : edgeDefaultStyle.entrySet()) {
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
		// TODO Auto-generated method stub

		jgxAdapter.getModel().beginUpdate();

		if (Integer.parseInt(node) > graph.vertexSet().size())
			throw new WrongVertexException();
		else
			try {

				Object[] cells = { (Object) vertexToCellMap.get(node) };

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
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				jgxAdapter.clearSelection();
				jgxAdapter.getModel().endUpdate();
			}

	}

	public void paintEdge(String startVertex, String targetVertex, int mode) {
		// TODO Auto-generated method stub

		jgxAdapter.getModel().beginUpdate();
		try {
			Object[] cells = { (Object) edgeToCellMap.get(this.graph.getEdge(startVertex, targetVertex)) };
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

	public void zoomIn(int x, int y) {
		component.setCenterZoom(component.isCenterPage());
//		double newX = x - component.getViewport().getSize().getWidth() / 2;
//		double newY = y - component.getViewport().getSize().getHeight() / 2;

		Point leftPoint = new Point();
		leftPoint.setLocation(x, y);

		component.zoomIn();
		component.scrollRectToVisible(new Rectangle(leftPoint, component.getViewport().getSize()));
	}

	public void zoomOut(int x, int y) {
		component.setCenterZoom(component.isCenterPage());

//		double newX = x - component.getViewport().getSize().getWidth() / 2;
//		double newY = y - component.getViewport().getSize().getHeight() / 2;

		Point leftPoint = new Point();
		leftPoint.setLocation(x, y);

		component.zoomOut();
		component.scrollRectToVisible(new Rectangle(leftPoint, component.getViewport().getSize()));
	}

	public void saveImage(boolean isNotify) {
		// Render into JPG b mxCellRender
		BufferedImage image = mxCellRenderer.createBufferedImage(jgxAdapter, null, 2, Color.WHITE, true, null);
		String str = ".\\src\\main\\java\\bobo4\\flowgraph\\asset\\images\\graph_" + numberImage + ".jpg";
		Graph.numberImage += 1;
		File imgFile = new File(str);
		try {
			ImageIO.write(image, "JPG", imgFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isNotify)
			JOptionPane.showMessageDialog(null,
					"Save graph as image graph_" + numberImage + ".jpg in asset/images folder", "Notice",
					JOptionPane.INFORMATION_MESSAGE);

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
		for (Map.Entry<Object, Object> e : edgeAfterStyle.entrySet()) {
			new mxStyleUtils();
			mxStyleUtils.setCellStyles(jgxAdapter.getModel(), edges.toArray(), e.getKey().toString(),
					e.getValue().toString());
		}
		// Paint node here
		for (Map.Entry<Object, Object> e : vertexAfterStyle.entrySet()) {
			new mxStyleUtils();
			mxStyleUtils.setCellStyles(jgxAdapter.getModel(), vertices.toArray(), e.getKey().toString(),
					e.getValue().toString());
		}
		jgxAdapter.getModel().endUpdate();

	}

	public String getLastNode() {
		return Integer.toString(graph.vertexSet().size());
	}

	public void paintPathInDelay(GraphPath<String, FlowEdge> path, int timeDelay) throws WrongVertexException, InterruptedException {
		jgxAdapter.getModel().beginUpdate();
		this.paintNode(path.getStartVertex(), 0);
		for (int i = 0; i < path.getEdgeList().size(); i++) {
			this.paintNode(path.getEdgeList().get(i).getTarget().toString(), 0);
			this.paintEdge(path.getEdgeList().get(i).getSource().toString(),
					path.getEdgeList().get(i).getTarget().toString(), 0);
		}
		Thread.sleep(timeDelay);
		jgxAdapter.getModel().endUpdate();

	}

	// Inner class to solve mouse listener
	private class MyMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

		Point startPoint = new Point();

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			if (e.getWheelRotation() > 0)
				zoomOut(e.getX(), e.getY());
			else
				zoomIn(e.getX(), e.getY());
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			startPoint = e.getPoint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.init();
	}
}