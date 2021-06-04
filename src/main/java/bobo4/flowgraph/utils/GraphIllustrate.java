package bobo4.flowgraph.utils;

import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;

import bobo4.flowgraph.elements.FlowEdge;
import bobo4.flowgraph.elements.Graph;
import bobo4.flowgraph.exception.WrongVertexException;
import bobo4.flowgraph.gui.GUIGraphIllustration;

public class GraphIllustrate {

	private Graph graphIllustrate;
	private List<String> PathHistory = new ArrayList<>();
	private Stack<String> RedoStack = new Stack<>();
	private int delayTime = 4096;
	private static boolean isRunAuto = false;
	private Timer timer;

	public static boolean isRunAuto() {
		return isRunAuto;
	}

	private boolean isStopRunAuto = false;

	public GraphIllustrate(Graph graphIllustrate) {
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
				PaintCurrentNode(true);
			} catch (WrongVertexException e1) {
				throw e1;
			}
		} catch (Exception e1) {
		}
	}

	public void Reset(Choice choice, JLabel myLabel, JTextArea txtPATHLOG, JTextField textFieldStart, JTextField textFieldEnd) {
		isRunAuto = false;
		isStopRunAuto = false;
		GUIGraphIllustration.hasTimerTask = false;
		graphIllustrate.init();
		PathHistory.clear();
		txtPATHLOG.setText(null);
		textFieldStart.setText("");
		textFieldEnd.setText("");
		SetChoiceAndUpdate(choice, myLabel);
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
					PaintCurrentNode(true);
				} catch (WrongVertexException e1) {
					e1.printStackTrace();
				}
				graphIllustrate.paintEdge(currentVertex, vnext, 0);

			}
			SetChoiceAndUpdate(choice, myLabel);
		} else {
			JOptionPane.showMessageDialog(null, "There's no start node", "Alert", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void ListNextNode(JTextArea txtPATHLOG, Choice choice, JLabel myLabel) {
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
						PaintCurrentNode(true);
					} catch (WrongVertexException e1) {
						e1.printStackTrace();
					}
					graphIllustrate.paintEdge(currentVertex, vnext, 0);
					SetChoiceAndUpdate(choice, myLabel);
				}
			}
		} else
			JOptionPane.showMessageDialog(null, "There's no start node", "Alert", JOptionPane.ERROR_MESSAGE);
	}

	public void ChoiceNode(String nextNode, JTextArea txtPATHLOG, Choice choice, JLabel myLabel) {
		try {
			graphIllustrate.paintNode(nextNode, 0);
		} catch (WrongVertexException e1) {
			e1.printStackTrace();
		}
		graphIllustrate.paintEdge(PathHistory.get(PathHistory.size() - 1), nextNode, 0);
		txtPATHLOG.append(
				PathHistory.size() + ")  " + PathHistory.get(PathHistory.size() - 1) + " => " + nextNode + "\n");
		PathHistory.add(nextNode);
		try {
			PaintCurrentNode(true);
		} catch (WrongVertexException e) {
			e.printStackTrace();
		}
		SetChoiceAndUpdate(choice, myLabel);
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
			// Make Path string to string like "+1 ,+2 ,+3 ,+5 ,+6"

			String strPath = PathHistory.toString().replaceAll(", ", " ,+").replace("[", "+").replace("]", " ");

			// Check the node is in the Path History
			if (!(PathHistory.contains(currentNode))) {
				try {
					graphIllustrate.paintNode(currentNode, 1);
				} catch (WrongVertexException e1) {
					e1.printStackTrace();
				}
				if (currentIndex > 0)
					graphIllustrate.paintEdge(PathHistory.get(currentIndex - 1), currentNode, 1);
			} else {
				try {
					graphIllustrate.paintNode(currentNode, 0);
				} catch (WrongVertexException e1) {
					e1.printStackTrace();
				}
				String strEdge = "+" + PathHistory.get(currentIndex - 1) + " ,+" + currentNode;
				if (!(strPath.indexOf(strEdge) >= 0)) {
					graphIllustrate.paintEdge(PathHistory.get(currentIndex - 1), currentNode, 1);
				}
			}

			try {
				PaintCurrentNode(true);
			} catch (WrongVertexException e) {
				e.printStackTrace();
			}

			int line = PathHistory.size() - 1;
			if (line >= 0)
				try {
					txtPATHLOG.replaceRange(null, txtPATHLOG.getLineStartOffset(line),
							txtPATHLOG.getLineEndOffset(line));
				} catch (BadLocationException e1) {
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
				e1.printStackTrace();
			}
			if (currentIndex >= 0) {
				graphIllustrate.paintEdge(PathHistory.get(currentIndex), newNode, 1);
				txtPATHLOG.append((currentIndex + 1) + ")  " + PathHistory.get(currentIndex) + " => " + newNode + "\n");
				graphIllustrate.paintEdge(PathHistory.get(currentIndex), newNode, 0);
			}
			PathHistory.add(newNode);
			try {
				PaintCurrentNode(true);
			} catch (WrongVertexException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "There's no redo node", "Alert", JOptionPane.ERROR_MESSAGE);
		}
		SetChoiceAndUpdate(choice, myLabel);
	}

	final int[] i = { 0 };
	private List<String> vertexList;

	public void Auto(String nodeStart, String nodeEnd, final Choice choice, final JLabel myLabel,
			final JTextArea txtPATHLOG) {
		txtPATHLOG.setText("");
		myLabel.setText("Current node is: None");
		choice.removeAll();
		choice.add("<None>");
		if(PathHistory.size()>1)
		graphIllustrate.init();
		PathHistory.clear();
		RedoStack.clear();
		timer = new Timer(delayTime, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					graphIllustrate.paintNode(vertexList.get(i[0]), 0);
					if (PathHistory.size() > 0) {
						graphIllustrate.paintEdge(vertexList.get(i[0] - 1), vertexList.get(i[0]), 0);
						txtPATHLOG.append(
								(i[0]) + ")  " + vertexList.get(i[0] - 1) + " => " + vertexList.get(i[0]) + "\n");
					}
					PathHistory.add(vertexList.get(i[0]));
					PaintCurrentNode(true);
					SetChoiceAndUpdate(choice, myLabel);
					i[0]++;
					if (i[0] >= vertexList.size()) {
						timer.stop();
						isRunAuto = false;
						GUIGraphIllustration.hasTimerTask = false;
					}
				} catch (WrongVertexException e1) {
					e1.printStackTrace();
				}
			}
		});
		new FindPath(nodeStart, nodeEnd);
		if (FindPath.getListPath().size() > 0) {
			i[0] = 0;
			vertexList = FindPath.getRandomPath().getVertexList();
			if (isRunAuto == false) {
				isRunAuto = true;
				timer.start();
			} else {
				isRunAuto = false;
				timer.stop();
			}
		} else {
			JOptionPane.showMessageDialog(null, "There is no path from node " + nodeStart + " to node " + nodeEnd,
					"Alert", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void ListPath(String nodeStart, String nodeEnd, JTextArea txtPATHLOG, Choice choice, JLabel myLabel) {
		new FindPath(nodeStart, nodeEnd);
		if (FindPath.getListPath().size() > 0) {
			String[] strPath = new String[FindPath.getListPath().size()];
			for (int i = 0; i < strPath.length; i++)
				strPath[i] = FindPath.getListPath().get(i).getStartVertex();
			for (int i = 0; i < strPath.length; i++)
				for (FlowEdge edge : FindPath.getListPath().get(i).getEdgeList()) {
					strPath[i] += " => " + edge.getTarget();
				}
			String choose = (String) JOptionPane.showInputDialog(null, "Choose your path: ", "Path Selection",
					JOptionPane.INFORMATION_MESSAGE, null, strPath, strPath[0]);
			int index = 0;
			for (; index < strPath.length; index++)
				if (strPath[index].equals(choose))
					break;
			graphIllustrate.repaintGraph();
			graphIllustrate.paintPath(FindPath.getListPath().get(index));
			String strUpdate = "";
			PathHistory.clear();
			RedoStack.clear();
			for (int i = 0; i < FindPath.getListPath().get(index).getEdgeList().size(); i++) {
				FlowEdge currentEdge = FindPath.getListPath().get(index).getEdgeList().get(i);
				PathHistory.add((String) currentEdge.getSource());
				strUpdate += (i + 1) + ")  " + currentEdge.getSource() + " => " + currentEdge.getTarget() + "\n";
			}
			PathHistory.add(FindPath.getListPath().get(index).getEndVertex());
			try {
				PaintCurrentNode(true);
			} catch (WrongVertexException e) {
				e.printStackTrace();
			}
			txtPATHLOG.setText(strUpdate);
			SetChoiceAndUpdate(choice, myLabel);

		} else {
			JOptionPane.showMessageDialog(null, "There is no path from node " + nodeStart + " to node " + nodeEnd,
					"Alert", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void AutoNextNode(final Choice choice, final JLabel myLabel, final JTextArea txtPATHLOG) {
		timer = new Timer(delayTime, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int currentIndex = PathHistory.size() - 1;
				if (currentIndex < 0) {
					JOptionPane.showMessageDialog(null, "There is no start node", "Alert",
							JOptionPane.INFORMATION_MESSAGE);
					timer.stop();
					isRunAuto = false;
				}

				List<String> sideVertex = graphIllustrate.getNextVertex(PathHistory.get(currentIndex));
				if (sideVertex.size() > 0) {
					int i = new Random().nextInt(sideVertex.size());
					PathHistory.add(sideVertex.get(i));
					txtPATHLOG.append((PathHistory.size() - 1) + ")  " + PathHistory.get(currentIndex) + " => "
							+ sideVertex.get(i) + "\n");
					SetChoiceAndUpdate(choice, myLabel);
					try {
						graphIllustrate.paintEdge(PathHistory.get(currentIndex), sideVertex.get(i), 0);
						PaintCurrentNode(true);
					} catch (WrongVertexException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"There is no next node from " + PathHistory.get(currentIndex) + " to node ", "Alert",
							JOptionPane.INFORMATION_MESSAGE);
					timer.stop();
					isRunAuto = false;
				}
			}
		});
		int currentIndex = PathHistory.size() - 1;
		if (currentIndex < 0) {
			JOptionPane.showMessageDialog(null, "There is no start node", "Alert", JOptionPane.INFORMATION_MESSAGE);
		} else if (isRunAuto == false) {
			isRunAuto = true;
			timer.start();
		} else {
			isRunAuto = false;
			timer.stop();
		}
	}

	public void Stop() {
		if (isRunAuto == true) {
			isRunAuto = false;
			isStopRunAuto = true;
			timer.stop();
		}
	}

	public void Continue() {
		if (isStopRunAuto == true) {
			isRunAuto = true;
			timer.start();
		}
	}

	public void SpeedUp() {
		if (timer != null) {
			this.delayTime /= 2;
			timer.setDelay(this.delayTime);
			System.out.println(this.delayTime);
		}
	}

	public void SpeedDown() {
		if (timer != null) {
			this.delayTime *= 2;
			timer.setDelay(this.delayTime);
			System.out.println(this.delayTime);
		}
	}

	private void SetChoiceAndUpdate(Choice choice, JLabel myLabel) {
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

	private void PaintCurrentNode(boolean isForWard) throws WrongVertexException {
		int i = PathHistory.size();
		if (i > 0) {
			graphIllustrate.paintNode(PathHistory.get(i - 1), 2);
			if (PathHistory.size() > 1 && isForWard)
				graphIllustrate.paintNode(PathHistory.get(i - 2), 0);
		}

	}
}
