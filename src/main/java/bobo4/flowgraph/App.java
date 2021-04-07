package bobo4.flowgraph;

import java.util.*;

import com.mxgraph.model.mxICell;

public class App {
	
	public static void main(String[] args) throws InterruptedException {
		Graph hung = new Graph();
		hung.print();
		FindPath dog = new FindPath(hung.getGraph());
		dog.print();
		hung.getFrame().setVisible(false);
		
		new IllustratePath().printOnePath();
	}
}
