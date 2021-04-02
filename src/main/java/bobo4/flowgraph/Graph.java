package bobo4.flowgraph;

import com.mxgraph.layout.*;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
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


public class Graph
    extends
    JApplet
{
    private static final Dimension DEFAULT_SIZE = new Dimension(1920, 1080);
      
    // This is a Graph Adapter to connect between JGrgaphT and JFrame use print graph
    private JGraphXAdapter<String, FlowEdge> jgxAdapter;

    private ListenableGraph<String, FlowEdge> graph =
            new DefaultListenableGraph<>(new DefaultDirectedGraph<String, FlowEdge>(FlowEdge.class));
    
    
    public ListenableGraph<String, FlowEdge> getGraph() {
		return graph;
	}

	public void setGraph(ListenableGraph<String, FlowEdge> graph) {
		this.graph = graph;
	}

	// All attribute of the vertex style you can declare in this hashmap 
	Map<Object, Object> vertexStyle = new HashMap<Object, Object>() {{
    	put(mxConstants.STYLE_FONTSIZE, 20);
    	put(mxConstants.STYLE_FILLCOLOR, "D98282");
    	put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
    	put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
    	put(mxConstants.STYLE_FONTCOLOR, "FFFFFF");
    }};
    
    Map<Object, Object> edgeStyle = new HashMap<Object, Object>() {{
//        put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ARROW)
    }};
    
    public Graph()
    {
          try {
        	  // Need to improve that can get current file of App and read graph txt
        	  File graphFile = new File("C:\\Users\\Admin\\eclipse-workspace\\flowgraph\\src\\main\\java\\bobo4\\flowgraph\\graph.txt");
        	  // Open file to read by scanner
        	  Scanner fileReader = new Scanner(graphFile);
			
			while (fileReader.hasNextLine())
			{
				String data = fileReader.nextLine();
				String[] verticesList = data.strip().split(" ");
				
				this.graph.addVertex(verticesList[0]);
				
				for (int i = 1; i < verticesList.length; i++)
				{
					this.graph.addVertex(verticesList[i]);
					this.graph.addEdge(verticesList[0], verticesList[i]);
				}				
			}

			fileReader.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("An error occurred");
			e.printStackTrace();
		}	
    }
    
    public void print()
    {

        this.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setTitle("Flow Graph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
    }

    @Override
    public void init()
    {
        // Create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<>(graph);

        setPreferredSize(DEFAULT_SIZE);

        mxGraphComponent component = new mxGraphComponent(jgxAdapter); 
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

        	Object[] vertices = new Object[this.graph.vertexSet().size()];
        	Object[] edges = new Object[this.graph.edgeSet().size()];
        	
        	int length = 0;
        	int numEdge = 0;
        	for (Object c : cells)
        	{
        		mxCell cell = (mxCell) c;
        		mxGeometry geometry = cell.getGeometry();
        		if (cell.isVertex())
        		{
        			geometry.setWidth(50); // This is size of Circle
        			geometry.setHeight(50); // This is size of other radius Circle
        			vertices[length] = c;
        			length ++;
        		}
        		else
        		{
        			if (cell.isEdge())
        			{
        				edges[numEdge] = c;
        				numEdge++;
        			}
        		}
        	}

        	jgxAdapter.setSelectionCells(vertices);
        	cells = jgxAdapter.getSelectionCells();
        	for (Map.Entry<Object, Object> e : vertexStyle.entrySet())
        	{
            	myStyle.setCellStyles(jgxAdapter.getModel(), cells, e.getKey().toString() , e.getValue().toString());
        	}
        	
        	// Modify the edge now
        	jgxAdapter.clearSelection();
        	jgxAdapter.setSelectionCell(edges);
        	for (Map.Entry<Object, Object> e : edgeStyle.entrySet())
        	{
            	myStyle.setCellStyles(jgxAdapter.getModel(),edges ,e.getKey().toString(), e.getValue().toString());
        	}
        }
        finally {
			jgxAdapter.getModel().endUpdate();
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
        
        // Render into JPG b mxCellRender
        BufferedImage image = mxCellRenderer.createBufferedImage(jgxAdapter, null, 2, Color.WHITE, true, null);
        File imgFile = new File("C:\\Users\\Admin\\eclipse-workspace\\flowgraph\\src\\main\\java\\bobo4\\flowgraph\\graph.jpg");
        try {
			ImageIO.write(image, "JPG", imgFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}