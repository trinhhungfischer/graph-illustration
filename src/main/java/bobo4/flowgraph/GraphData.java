package bobo4.flowgraph;

import java.io.*;
import java.util.*;

public class GraphData {
	private int numberNode;
	private int numberEdge;
	private HashMap<String, NodeGraph> nodeList;

	public GraphData() {
		try {
			// Need to improve that can get current file of App and read graph txt
			File graphFile = new File(".\\src\\main\\java\\bobo4\\flowgraph\\graph.txt");
			// Open file to read by scanner
			Scanner fileReader = new Scanner(graphFile);
			
			this.nodeList = new HashMap<>();
			this.numberNode = Integer.parseInt(fileReader.nextLine());
			this.numberEdge = Integer.parseInt(fileReader.nextLine());
			
			for(int i = 1; i <= this.numberEdge; i++) {
				String data = fileReader.nextLine();
				String[] edgeData = data.strip().split(" ");
				String idNode1 = edgeData[0];
				String idNode2 = edgeData[1];
				int length = Integer.parseInt(edgeData[2]);
				
				if(!this.nodeList.containsKey(idNode1)) {
					NodeGraph node = new NodeGraph(idNode1);
					this.nodeList.put(idNode1, node);
				}
					
				if(!this.nodeList.containsKey(idNode2)) {
					NodeGraph node = new NodeGraph(idNode2);
					this.nodeList.put(idNode2, node);
				}
					
				this.nodeList.get(idNode1).addNextNode(this.nodeList.get(idNode2), length);
			}

			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred");
			e.printStackTrace();
		}
	}
	
	public HashMap<String, NodeGraph> getNodeList(){
		return this.nodeList;
	}
}