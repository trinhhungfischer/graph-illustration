package bobo4.flowgraph.utils;

import java.awt.Choice;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import org.jgrapht.GraphPath;

import bobo4.flowgraph.elements.FlowEdge;
import bobo4.flowgraph.elements.Graph;
import bobo4.flowgraph.exception.WrongVertexException;

public class GraphIllustrate {

	private Graph graphIllustrate;
	private List<String> PathHistory = new ArrayList<>();
	private Stack<String> RedoStack = new Stack<>();
	private int delayTime = 2000;
	public static boolean unlock = true;
	
	
	public GraphIllustrate(Graph graphIllustrate) {
		// TODO Auto-generated constructor stub
		this.graphIllustrate = graphIllustrate;
	}

	public void Start(String nodeStart, Choice choice, JLabel currentNodeLabel) {
		// Clear all history of before graph and text Path Log
		graphIllustrate.repaintGraph();
		RedoStack.clear();
		PathHistory.clear();

		try {
			Integer.parseInt(nodeStart);
			try {
				graphIllustrate.paintNode(nodeStart, 0);
				PathHistory.add(nodeStart);
				this.SetChoiceAndUpdate(choice, currentNodeLabel);
			} catch (WrongVertexException e1) {
				// TODO Auto-generated catch block
				throw e1;
			}
		} catch (Exception e1) {
			// TODO: handle exception
		}
	}

	public void Reset() {
		graphIllustrate.init();
	}

	public void Save() {
		graphIllustrate.saveImage(true);
	}

	public void Next(Choice choice, JLabel myLabel, JTextArea txtPATHLOG) {
		RedoStack.clear();
		if (PathHistory.size() > 0) {
			String currentVertex = PathHistory.get(PathHistory.size() - 1);
			List<String> vtarget = graphIllustrate.getNextVertex(currentVertex);
			if (vtarget.size() == 0)
				JOptionPane.showMessageDialog(null,
						"The current vertex: " + currentVertex + "\nThere is no next vertex", "Choose next vertex",
						JOptionPane.PLAIN_MESSAGE);
			else {
				int i = new Random().nextInt(vtarget.size());
				String vnext = vtarget.get(i);
				txtPATHLOG.append(
						PathHistory.size() + ")  " + PathHistory.get(PathHistory.size() - 1) + " => " + vnext + "\n");
				PathHistory.add(vnext);
				try {
					graphIllustrate.paintNode(vnext, 0);
				} catch (WrongVertexException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				graphIllustrate.paintEdge(currentVertex, vnext, 0);

			}
			SetChoiceAndUpdate(choice, myLabel);
		} else {
			JOptionPane.showMessageDialog(null, "There's no start node", "Alert", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void ListNextNode(JTextArea txtPATHLOG) {
		if (PathHistory.size() > 0) {
			String currentVertex = PathHistory.get(PathHistory.size() - 1);
			List<String> vtarget = graphIllustrate.getNextVertex(currentVertex);

			if (vtarget.size() == 0)
				JOptionPane.showMessageDialog(null,
						"The current vertex: " + currentVertex + "\nThere is no next vertex", "Choose next vertex",
						JOptionPane.PLAIN_MESSAGE);
			else {
				String vnext = (String) JOptionPane.showInputDialog(null,
						"The current vertex: " + currentVertex + "\nThe next vertex :", "Choose next vertex",
						JOptionPane.INFORMATION_MESSAGE, null, vtarget.toArray(), vtarget.toArray()[0]);

				if (vnext != null) {
					txtPATHLOG.append(PathHistory.size() + ")  " + PathHistory.get(PathHistory.size() - 1) + " => "
							+ vnext + "\n");
					PathHistory.add(vnext);
					try {
						graphIllustrate.paintNode(vnext, 0);
					} catch (WrongVertexException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					graphIllustrate.paintEdge(currentVertex, vnext, 0);
				}
			}
		} else
			JOptionPane.showMessageDialog(null, "There's no start node", "Alert", JOptionPane.ERROR_MESSAGE);
	}

	public void ChoiceNode(String nextNode, JTextArea txtPATHLOG, Choice choice, JLabel myLabel) {
		try {
			graphIllustrate.paintNode(nextNode, 0);
		} catch (WrongVertexException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		graphIllustrate.paintEdge(PathHistory.get(PathHistory.size() - 1), nextNode, 0);
		txtPATHLOG.append(
				PathHistory.size() + ")  " + PathHistory.get(PathHistory.size() - 1) + " => " + nextNode + "\n");
		PathHistory.add(nextNode);
		SetChoiceAndUpdate(choice, myLabel);
	}

	public void SetChoiceAndUpdate(Choice choice, JLabel myLabel) {
		choice.removeAll();
		choice.add("<None>");
		if (PathHistory.size() > 0) {
			String currentVertex = PathHistory.get(PathHistory.size() - 1);
			List<String> nextVertex = graphIllustrate.getNextVertex(currentVertex);
			for (int i = 0; i < nextVertex.size(); i++) {
				choice.add(nextVertex.get(i));
			}
		}
		if (PathHistory.size() > 0) {
			myLabel.setText("Current node is: " + PathHistory.get(PathHistory.size() - 1));
		} else
			myLabel.setText("Current node is: None");
	}

	public void ZoomIn() {
		graphIllustrate.zoomIn(graphIllustrate.getBounds().x / 2, graphIllustrate.getBounds().y / 2);
	}

	public void ZoomOut() {
		graphIllustrate.zoomOut(graphIllustrate.getBounds().x / 2, graphIllustrate.getBounds().y / 2);
	}

	public void Undo(Choice choice, JLabel myLabel, JTextArea txtPATHLOG) {
		if (PathHistory.size() > 0) {
			int currentIndex = PathHistory.size() - 1; // currentIndex = n - 1;
			RedoStack.push(PathHistory.get(currentIndex));
			String currentNode = PathHistory.get(currentIndex);
			PathHistory.remove(currentIndex);

			// Make Path string to string like "123456"

			String strPath = PathHistory.toString().replaceAll(", ", " ,+").replace("[", "+").replace("]", " ");

			// Check the node is in the Path History
			if (!(PathHistory.contains(currentNode))) {
				try {
					graphIllustrate.paintNode(currentNode, 1);
				} catch (WrongVertexException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (currentIndex > 0)
					graphIllustrate.paintEdge(PathHistory.get(currentIndex - 1), currentNode, 1);
			} else {
				String strEdge = "+" + PathHistory.get(currentIndex - 1) + " ,+" + currentNode;
				if (!(strPath.indexOf(strEdge) >= 0)) {
					graphIllustrate.paintEdge(PathHistory.get(currentIndex - 1), currentNode, 1);
				}
			}

			int line = PathHistory.size() - 1;
			if (line >= 0)
				try {
					txtPATHLOG.replaceRange(null, txtPATHLOG.getLineStartOffset(line),
							txtPATHLOG.getLineEndOffset(line));
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			SetChoiceAndUpdate(choice, myLabel);
		} else {
			JOptionPane.showMessageDialog(null, "There's no start node", "Alert", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void Redo(Choice choice, JLabel myLabel, JTextArea txtPATHLOG) {
		if (RedoStack.size() > 0) {
			int currentIndex = PathHistory.size() - 1;
			String newNode = RedoStack.pop();
			
			try {
				graphIllustrate.paintNode(newNode, 0);
			} catch (WrongVertexException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (currentIndex >= 0) {
				graphIllustrate.paintEdge(PathHistory.get(currentIndex), newNode, 1);
				txtPATHLOG.append((currentIndex + 1) + ")  " + PathHistory.get(currentIndex) + " => " + newNode + "\n");
				graphIllustrate.paintEdge(PathHistory.get(currentIndex), newNode, 0);
			}
			PathHistory.add(newNode);
		} else {
			JOptionPane.showMessageDialog(null, "There's no redo node", "Alert", JOptionPane.ERROR_MESSAGE);
		}
		SetChoiceAndUpdate(choice, myLabel);
	}

	public void Auto(String nodeStart, String nodeEnd) throws WrongVertexException, InterruptedException {
		new FindPath(nodeStart, nodeEnd);
		if (FindPath.getListPath().size() == 0) {
			JOptionPane.showMessageDialog(null, "There's no path to go last node", "Alert", JOptionPane.ERROR_MESSAGE);
		} else {
			graphIllustrate.paintPathInDelay(FindPath.getRandomPath(), 10000);
		}
	}
}
