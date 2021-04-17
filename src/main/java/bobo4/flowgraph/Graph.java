package bobo4.flowgraph;

import java.io.*;
import java.util.*;

public class Graph {
	private int numberNode;
	private int numberEdge;
	private HashMap<Integer, NodeGraph> nodeList;

	public Graph() {
		try {
			// Need to improve that can get current file of App and read graph txt
			File graphFile = new File(".\\src\\main\\java\\bobo4\\flowgraph\\graph.txt");
			// Open file to read by scanner
			Scanner fileReader = new Scanner(graphFile);
			
			this.numberNode = Integer.parseInt(fileReader.nextLine());
			this.numberEdge = Integer.parseInt(fileReader.nextLine());
			
			for(int i = 1; i <= this.numberEdge; i++) {
				String data = fileReader.nextLine();
				String[] edgeData = data.strip().split(" ");
				int idNode1 = Integer.parseInt(edgeData[0]);
				int idNode2 = Integer.parseInt(edgeData[1]);
				int length = Integer.parseInt(edgeData[2]);
				
				if(this.nodeList.containsKey(idNode1)) {
					if(!this.nodeList.containsKey(idNode2)) {
						NodeGraph node = new NodeGraph(idNode2);
						this.nodeList.put(idNode2, node);
					}
					
					this.nodeList.get(idNode1).addNextNode(this.nodeList.get(idNode2), length);
				}
			}

			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred");
			e.printStackTrace();
		}
	}
	
	public HashMap<Integer, NodeGraph> getNodeList(){
		return this.nodeList;
	}
}