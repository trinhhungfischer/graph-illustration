package bobo4.flowgraph.utils;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.Window.Type;
import javax.swing.border.CompoundBorder;
import javax.swing.text.BadLocationException;

import bobo4.flowgraph.elements.Graph;

import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GraphIllustrate extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> PathHistory = new ArrayList<>();

	private final Dimension DEFAULT_SIZE = new Dimension(1500, 900);
	private JTextArea txtPATHLOG;

	public GraphIllustrate() {
		setForeground(Color.LIGHT_GRAY);
		setFont(new Font("Arial", Font.PLAIN, 14));
		setTitle("Graph");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1500, 900);
		setSize(DEFAULT_SIZE);

		final Graph graphIllustrate = new Graph();
		graphIllustrate.setBounds(328, 11, 1146, 720);
		graphIllustrate.init();
		getContentPane().setLayout(null);

		JButton btnZOOMOUT = new JButton();
		btnZOOMOUT.setBackground(Color.LIGHT_GRAY);
		btnZOOMOUT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				graphIllustrate.zoomOut();
			}
		});
		ImageIcon zoomOut = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/zoom-out.png"));
		Image image = zoomOut.getImage();
		Image newImg = image.getScaledInstance(btnZOOMOUT.WIDTH * 30, btnZOOMOUT.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		zoomOut = new ImageIcon(newImg);
		btnZOOMOUT.setIcon(zoomOut);
		btnZOOMOUT.setBounds(1409, 85, 46, 46);

		JButton btnZOOMIN = new JButton();
		btnZOOMIN.setBackground(Color.LIGHT_GRAY);
		btnZOOMIN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				graphIllustrate.zoomIn();
			}
		});
		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/zoom-in.png")).getImage();
		newImg = image.getScaledInstance(btnZOOMIN.WIDTH * 30, btnZOOMIN.HEIGHT * 20, java.awt.Image.SCALE_SMOOTH);
		ImageIcon zoomIn = new ImageIcon(newImg);
		btnZOOMIN.setIcon(zoomIn);
		btnZOOMIN.setBounds(1409, 29, 46, 46);

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
		btnQUESTION.setBounds(1409, 141, 46, 46);
		newImg = image.getScaledInstance(btnQUESTION.WIDTH * 30, btnQUESTION.HEIGHT * 20, java.awt.Image.SCALE_SMOOTH);
		ImageIcon Question = new ImageIcon(newImg);
		btnQUESTION.setIcon(Question);

		getContentPane().add(btnQUESTION);
		getContentPane().add(btnZOOMIN);
		getContentPane().add(btnZOOMOUT);

		getContentPane().add(graphIllustrate);

		JPanel panelButton = new JPanel();
		panelButton.setBounds(328, 742, 1146, 108);
		getContentPane().add(panelButton);
		panelButton.setLayout(null);

		final JButton btnSAVE = new JButton("SAVE");
		btnSAVE.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Instruction your user here
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnSAVE, "Save illustration into jpg file", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				graphIllustrate.saveImage();
			}
		});
		btnSAVE.setBounds(218, 11, 125, 75);
		panelButton.add(btnSAVE);

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

				graphIllustrate.repaintGraph();
				;
				PathHistory.clear();

				try {
					txtPATHLOG.replaceRange(null, txtPATHLOG.getLineStartOffset(0),
							txtPATHLOG.getLineEndOffset(txtPATHLOG.getLineCount() - 1));
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String nodeStart = JOptionPane.showInputDialog(null, "Input your start node", "Input",
						JOptionPane.QUESTION_MESSAGE);
				graphIllustrate.paintNode(nodeStart, 0);
				PathHistory.add(nodeStart);
			}
		});
		btnSTART.setBounds(28, 11, 125, 75);
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
						graphIllustrate.paintNode(vnext, 0);
						graphIllustrate.paintEdge(currentVertex, vnext, 0);

					}
				} else {
					JOptionPane.showMessageDialog(btnNEXT, "There's no start node", "Alert", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnNEXT.setBounds(598, 11, 125, 75);
		panelButton.add(btnNEXT);

		final JButton btnBACK = new JButton("BACK");
		btnBACK.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// Instruction your user here
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnBACK, "Turn back previous node", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}

				if (PathHistory.size() > 0) {
					int currentIndex = PathHistory.size() - 1;
					graphIllustrate.paintNode(PathHistory.get(currentIndex), 1);
					if (currentIndex > 0)
						graphIllustrate.paintEdge(PathHistory.get(currentIndex - 1), PathHistory.get(currentIndex), 1);
					PathHistory.remove(PathHistory.size() - 1);
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
					JOptionPane.showMessageDialog(btnBACK, "There's no start node", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnBACK.setBounds(788, 11, 125, 75);
		panelButton.add(btnBACK);

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
						txtPATHLOG.append(PathHistory.size() + ")  " + PathHistory.get(PathHistory.size() - 1) + " => "
								+ vnext + "\n");
						PathHistory.add(vnext);
						graphIllustrate.paintNode(vnext, 0);
						graphIllustrate.paintEdge(currentVertex, vnext, 0);
					}
				} else
					JOptionPane.showMessageDialog(btnLIST, "There's no start node", "Alert", JOptionPane.ERROR_MESSAGE);

			}
		});
		btnLIST.setBounds(978, 11, 125, 75);
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

		btnRESET.setBounds(408, 11, 125, 75);
		panelButton.add(btnRESET);

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
