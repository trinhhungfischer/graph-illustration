package bobo4.flowgraph.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;

import org.jgrapht.GraphPath;

import bobo4.flowgraph.elements.FlowEdge;
import bobo4.flowgraph.elements.Graph;
import bobo4.flowgraph.exception.WrongVertexException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GraphIllustrate extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> PathHistory = new ArrayList<>();

	private final Dimension DEFAULT_SIZE = new Dimension(1600, 900);
	private JTextArea txtPATHLOG;
	private JTextField textField;

	public GraphIllustrate() {
		setForeground(Color.LIGHT_GRAY);
		setFont(new Font("Arial", Font.PLAIN, 14));
		setTitle("Graph");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1500, 900);
		setSize(DEFAULT_SIZE);

		final Graph graphIllustrate = new Graph();
		graphIllustrate.setBounds(328, 11, 1190, 750);
		graphIllustrate.init();
		getContentPane().setLayout(null);

		JButton btnZOOMOUT = new JButton();
		btnZOOMOUT.setBackground(Color.LIGHT_GRAY);
		btnZOOMOUT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				graphIllustrate.zoomOut(graphIllustrate.getBounds().x / 2, graphIllustrate.getBounds().y / 2);
			}
		});

		Image image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/zoom-out.png"))
				.getImage();
		Image newImg = image.getScaledInstance(btnZOOMOUT.WIDTH * 30, btnZOOMOUT.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon zoomOutImage = new ImageIcon(newImg);
		btnZOOMOUT.setIcon(zoomOutImage);
		btnZOOMOUT.setBounds(1528, 87, 46, 46);

		JButton btnZOOMIN = new JButton();
		btnZOOMIN.setBackground(Color.LIGHT_GRAY);
		btnZOOMIN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				graphIllustrate.zoomIn(graphIllustrate.getBounds().x / 2, graphIllustrate.getBounds().y / 2);
			}
		});
		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/zoom-in.png")).getImage();
		newImg = image.getScaledInstance(btnZOOMIN.WIDTH * 30, btnZOOMIN.HEIGHT * 20, java.awt.Image.SCALE_SMOOTH);
		ImageIcon zoomInImage = new ImageIcon(newImg);
		btnZOOMIN.setIcon(zoomInImage);
		btnZOOMIN.setBounds(1528, 28, 46, 46);

		final int[] isQuestion = { 0 };

		final JButton btnQUESTION = new JButton();
		btnQUESTION.setBackground(Color.LIGHT_GRAY);
		btnQUESTION.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnQUESTION.setBackground(Color.YELLOW);
				isQuestion[0] = 1;
			}
		});

		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/question.png")).getImage();
		btnQUESTION.setBounds(1528, 144, 46, 46);
		newImg = image.getScaledInstance(btnQUESTION.WIDTH * 30, btnQUESTION.HEIGHT * 20, java.awt.Image.SCALE_SMOOTH);
		ImageIcon QuestionImage = new ImageIcon(newImg);
		btnQUESTION.setIcon(QuestionImage);

		final JButton btnSAVE = new JButton();
		btnSAVE.setBackground(Color.LIGHT_GRAY);
		btnSAVE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnSAVE, "Save image of graph", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				graphIllustrate.saveImage();
			}
		});
		btnSAVE.setBounds(1528, 201, 46, 46);
		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/save.png")).getImage();
		newImg = image.getScaledInstance(btnQUESTION.WIDTH * 30, btnQUESTION.HEIGHT * 20, java.awt.Image.SCALE_SMOOTH);
		ImageIcon SaveImage = new ImageIcon(newImg);
		btnSAVE.setIcon(SaveImage);

		final JButton btnUNDO = new JButton();
		btnUNDO.setBackground(Color.LIGHT_GRAY);
		final Stack<String> RedoStack = new Stack<String>();

		btnUNDO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Instruction your user here
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnUNDO, "Turn back previous node", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}

				if (PathHistory.size() > 0) {
					int currentIndex = PathHistory.size() - 1; // currentIndex = n - 1;
					RedoStack.push(PathHistory.get(currentIndex));
					String currentNode = PathHistory.get(currentIndex);
					PathHistory.remove(currentIndex);
					
					// Make Path string to string like "123456"
					String strPath = PathHistory.toString().replaceAll(", |[|]", "");

					// Check the node is in the Path History
					if (!(strPath.indexOf(currentNode) >= 0)) {
						try {
							graphIllustrate.paintNode(currentNode, 1);
						} catch (WrongVertexException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (currentIndex > 0)
							graphIllustrate.paintEdge(PathHistory.get(currentIndex - 1), currentNode, 1);
					} else {
						String strEdge = PathHistory.get(currentIndex - 1) + currentNode;
						if (!(strPath.indexOf(strEdge) >= 0)) {
							graphIllustrate.paintEdge(PathHistory.get(currentIndex - 1), currentNode, 1);
							System.out.println(strEdge);
							System.out.println(strPath);
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
				} else {
					JOptionPane.showMessageDialog(btnUNDO, "There's no start node", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		btnUNDO.setBounds(1528, 258, 46, 46);
		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/undo.png")).getImage();
		newImg = image.getScaledInstance(btnQUESTION.WIDTH * 30, btnQUESTION.HEIGHT * 20, java.awt.Image.SCALE_SMOOTH);
		ImageIcon UndoImage = new ImageIcon(newImg);

		final JButton btnREDO = new JButton();
		btnREDO.setBackground(Color.LIGHT_GRAY);
		btnUNDO.setIcon(UndoImage);
		btnREDO.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnREDO, "Turn back previous node", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
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
						txtPATHLOG.append(
								(currentIndex + 1) + ")  " + PathHistory.get(currentIndex) + " => " + newNode + "\n");
						graphIllustrate.paintEdge(PathHistory.get(currentIndex), newNode, 0);
					}
					PathHistory.add(newNode);
				} else {
					JOptionPane.showMessageDialog(btnREDO, "There's no redo node", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnREDO.setBounds(1528, 315, 46, 46);
		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/redo.png")).getImage();
		newImg = image.getScaledInstance(btnQUESTION.WIDTH * 30, btnQUESTION.HEIGHT * 20, java.awt.Image.SCALE_SMOOTH);

		ImageIcon RedoImage = new ImageIcon(newImg);

		getContentPane().add(btnREDO);
		btnREDO.setIcon(RedoImage);

		getContentPane().add(btnUNDO);
		getContentPane().add(btnSAVE);
		getContentPane().add(btnQUESTION);
		getContentPane().add(btnZOOMIN);
		getContentPane().add(btnZOOMOUT);

		getContentPane().add(graphIllustrate);

		JPanel panelButton = new JPanel();
		panelButton.setBounds(328, 773, 1146, 77);
		getContentPane().add(panelButton);
		panelButton.setLayout(null);

		final JButton btnSTART = new JButton("START");
		btnSTART.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Instruction your user here
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnSTART, "Input your start node", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}

				// Clear all history of before graph and text Path Log
				graphIllustrate.repaintGraph();
				RedoStack.clear();
				PathHistory.clear();

				try {
					txtPATHLOG.replaceRange(null, txtPATHLOG.getLineStartOffset(0),
							txtPATHLOG.getLineEndOffset(txtPATHLOG.getLineCount() - 1));
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				String nodeStart;
//				nodeStart = graphIllustrate.cellToVertexMap.get(graphIllustrate.jgxAdapter.getSelectionCell());
//				graphIllustrate.jgxAdapter.selectAll();
//				System.out.print(nodeStart);
				if (!(textField.getText().equals(""))){
					nodeStart = textField.getText();
					System.out.print(nodeStart);
				} else {
					nodeStart = JOptionPane.showInputDialog(null, "Input your start node", "Input",
							JOptionPane.QUESTION_MESSAGE);
				}
				
				try {
					graphIllustrate.paintNode(nodeStart, 0);
					PathHistory.add(nodeStart);
				} catch (WrongVertexException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(btnSTART, "There's no node in this graph", "Alert",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e2) {
					// TODO: handle exception
					
				}
			}
		});
		btnSTART.setBounds(28, 20, 100, 41);
		panelButton.add(btnSTART);

		final JButton btnNEXT = new JButton("NEXT");
		btnNEXT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Instruction your user here
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnNEXT, "Random walk to next node", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}

				RedoStack.clear();
				if (PathHistory.size() > 0) {
					String currentVertex = PathHistory.get(PathHistory.size() - 1);
					List<String> vtarget = graphIllustrate.getNextVertex(currentVertex);
					if (vtarget.size() == 0)
						JOptionPane.showMessageDialog(null,
								"The current vertex: " + currentVertex + "\nThere is no next vertex",
								"Choose next vertex", JOptionPane.PLAIN_MESSAGE);
					else {
						int i = new Random().nextInt(vtarget.size());
						String vnext = vtarget.get(i);
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
				} else {
					JOptionPane.showMessageDialog(btnNEXT, "There's no start node", "Alert", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnNEXT.setBounds(467, 20, 100, 41);
		panelButton.add(btnNEXT);

		final JButton btnLIST = new JButton("LIST NEXT");
		btnLIST.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnLIST, "List all next node which can be reached", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}

				if (PathHistory.size() > 0) {
					String currentVertex = PathHistory.get(PathHistory.size() - 1);
					List<String> vtarget = graphIllustrate.getNextVertex(currentVertex);

					if (vtarget.size() == 0)
						JOptionPane.showMessageDialog(null,
								"The current vertex: " + currentVertex + "\nThere is no next vertex",
								"Choose next vertex", JOptionPane.PLAIN_MESSAGE);
					else {
						String vnext = (String) JOptionPane.showInputDialog(null,
								"The current vertex: " + currentVertex + "\nThe next vertex :", "Choose next vertex",
								JOptionPane.INFORMATION_MESSAGE, null, vtarget.toArray(), vtarget.toArray()[0]);

						if (vnext != null) {
							txtPATHLOG.append(PathHistory.size() + ")  " + PathHistory.get(PathHistory.size() - 1)
									+ " => " + vnext + "\n");
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
					JOptionPane.showMessageDialog(btnLIST, "There's no start node", "Alert", JOptionPane.ERROR_MESSAGE);

			}
		});
		btnLIST.setBounds(654, 20, 100, 41);
		panelButton.add(btnLIST);

		final JButton btnRESET = new JButton("RESET");
		btnRESET.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnRESET, "Reset graph to default state", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}

				graphIllustrate.init();
				PathHistory.clear();
				try {
					txtPATHLOG.replaceRange(null, txtPATHLOG.getLineStartOffset(0),
							txtPATHLOG.getLineEndOffset(txtPATHLOG.getLineCount() - 1));
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnRESET.setBounds(302, 20, 81, 41);
		panelButton.add(btnRESET);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String newnode = textField.getText();

			}
		});
		textField.setBounds(138, 20, 91, 41);
		panelButton.add(textField);
		textField.setColumns(10);

		JPanel panelLEFT = new JPanel();
		panelLEFT.setBounds(12, 11, 306, 839);
		getContentPane().add(panelLEFT);
		panelLEFT.setLayout(null);

		JLabel lblPATHLOG = new JLabel("PATH LOG");
		lblPATHLOG.setHorizontalAlignment(SwingConstants.CENTER);
		lblPATHLOG.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblPATHLOG.setBounds(10, 7, 286, 72);
		panelLEFT.add(lblPATHLOG);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 77, 286, 751);
		panelLEFT.add(scrollPane);

		txtPATHLOG = new JTextArea();
		txtPATHLOG.setFont(new Font("Courier New", Font.PLAIN, 14));
		txtPATHLOG.setEditable(false);
		scrollPane.setViewportView(txtPATHLOG);
	}

	public static void main(String[] args) {
		GraphIllustrate hung = new GraphIllustrate();
		hung.setVisible(true);
	}
}
