package bobo4.flowgraph.gui;

import java.awt.BorderLayout;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.text.BadLocationException;
import javax.swing.JOptionPane;
import org.jgrapht.GraphPath;

import bobo4.flowgraph.elements.FlowEdge;
import bobo4.flowgraph.elements.Graph;
import bobo4.flowgraph.exception.WrongVertexException;
import bobo4.flowgraph.utils.GraphIllustrate;
import bobo4.flowgraph.utils.FindPath;
public class GUIPrintPath extends JFrame{
	private final Dimension DEFAULT_SIZE = new Dimension(1600, 900);

	private JTextField textFieldStart;
	private JButton btnQUESTION;
	private Graph graph = new Graph();
//	private JLabel lblNewLabel;
	private JTextField textFieldEnd;
	private JPanel panelGRAPH;
	private JButton btnPrintPath;
	private JButton btnPrintAllPath;
	private String nodeStart = "";
	private String nodeEnd = "";
	private int currentPath;
	private List<GraphPath<String, FlowEdge>> listPath = new ArrayList<GraphPath<String, FlowEdge>>();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIPrintPath frame = new GUIPrintPath();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public GUIPrintPath() {
		setBackground(Color.LIGHT_GRAY);

		//final GraphIllustrate GraphManager = new GraphIllustrate(graphScrollPanel);

		setForeground(Color.LIGHT_GRAY);
		setFont(new Font("Arial", Font.PLAIN, 14));
		setTitle("Print Path");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1500, 900);
		setSize(DEFAULT_SIZE);
		
		final int[] isQuestion = { 0 };

		Image image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/question.png")).getImage();
		Image newImg = image.getScaledInstance(ImageObserver.WIDTH * 30, ImageObserver.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon QuestionImage = new ImageIcon(newImg);

		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/save.png")).getImage();
		newImg = image.getScaledInstance(ImageObserver.WIDTH * 30, ImageObserver.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon SaveImage = new ImageIcon(newImg);

		
		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/speed-up.png")).getImage();
		newImg = image.getScaledInstance(ImageObserver.WIDTH * 30, ImageObserver.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon NextImage = new ImageIcon(newImg);

		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/speed-down.png")).getImage();
		newImg = image.getScaledInstance(ImageObserver.WIDTH * 30, ImageObserver.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon BackImage = new ImageIcon(newImg);
		
		
		getContentPane().setLayout(new BorderLayout(10, 10));

		JPanel panelHEAD = new JPanel();
		panelHEAD.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(panelHEAD, BorderLayout.NORTH);

		final JButton btnSTART = new JButton("INPUT START NODE");
		btnSTART.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnSTART, "Input your start node", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				// Try to input node start by some method

				if (!(textFieldStart.getText().equals(""))) {
					nodeStart = textFieldStart.getText();
				} else {
					nodeStart = JOptionPane.showInputDialog(null, "Input your start node", "Input",
							JOptionPane.QUESTION_MESSAGE);
					textFieldStart.setText(nodeStart);
				}
			}
		});
		btnSTART.setFont(new Font("Tahoma", Font.PLAIN, 18));
		FlowLayout fl_panelHEAD = new FlowLayout(FlowLayout.CENTER, 15, 10);
		panelHEAD.setLayout(fl_panelHEAD);
		panelHEAD.add(btnSTART);
		
		textFieldStart = new JTextField();
		textFieldStart.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(textFieldStart, "Input your start node", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
			}
		});
		textFieldStart.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldStart.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String nodeStart = textFieldStart.getText();
				}
			}
		});
		panelHEAD.add(textFieldStart);
		textFieldStart.setColumns(3);

		final JButton btnEND = new JButton("INPUT END NODE");
		btnEND.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnEND, "Input your end node", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				if(!textFieldEnd.getText().equals("")) {
				nodeEnd = textFieldEnd.getText();
				} else {
			     nodeEnd = JOptionPane.showInputDialog(null, "Input your end node", "Input",
						JOptionPane.QUESTION_MESSAGE);
				textFieldEnd.setText(nodeEnd);}
			}
		});
		btnEND.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelHEAD.add(btnEND);

		textFieldEnd = new JTextField();
		textFieldEnd.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(textFieldEnd, "Input your end node", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}

			}
		});
		textFieldEnd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelHEAD.add(textFieldEnd);
		textFieldEnd.setColumns(3);
		
		btnPrintPath = new JButton("Print Path");
		
		btnPrintPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnPrintPath, "Print Path", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				if(!nodeStart.equals("")&&!nodeEnd.equals("")) {
					FindPath hoang = new FindPath(nodeStart, nodeEnd);
					listPath = hoang.getListPath();
					if(listPath.size()== 0) JOptionPane.showMessageDialog(null, "No Path", "All Path", JOptionPane.INFORMATION_MESSAGE);
					else {
						    String  strPath = "";
						    strPath += nodeStart;
						    
							for (FlowEdge edge : listPath.get(0).getEdgeList()) {
								strPath+= " => " + edge.getTarget();
							}
							graph.paintPath(listPath.get(0));
						JOptionPane.showMessageDialog(null, strPath, " Path", JOptionPane.INFORMATION_MESSAGE);
						currentPath = 1;
						
					}
				}
			}
		});
		btnPrintPath.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelHEAD.add(btnPrintPath);
		
        btnPrintAllPath = new JButton("Print All Path");
		
		btnPrintAllPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnPrintAllPath, "Print All Path", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				if(!nodeStart.equals("")&&!nodeEnd.equals("")) {
					FindPath hoang = new FindPath(nodeStart, nodeEnd);
					hoang.print();
				}
			}
		});
		btnPrintAllPath.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelHEAD.add(btnPrintAllPath);
		
		final JButton btnRESET = new JButton("RESET");
		btnRESET.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnRESET, "Reset graph to default state", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				reset();
			}
		});
		btnRESET.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelHEAD.add(btnRESET);
		
		JPanel panelLEFTBUTTON = new JPanel();
		panelLEFTBUTTON.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(panelLEFTBUTTON, BorderLayout.EAST);
		panelLEFTBUTTON.setLayout(new GridLayout(0, 1, 0, 0));
		

		btnQUESTION = new JButton();
		btnQUESTION.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnQUESTION.setBackground(Color.YELLOW);
				isQuestion[0] = 1;
			}
		});
		panelLEFTBUTTON.add(btnQUESTION);
		btnQUESTION.setBackground(Color.LIGHT_GRAY);
		btnQUESTION.setIcon(QuestionImage);

		final JButton btnSAVE = new JButton();
		panelLEFTBUTTON.add(btnSAVE);
		btnSAVE.setBackground(Color.LIGHT_GRAY);
		btnSAVE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnSAVE, "Save image of graph", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				
				graph.saveImage(true);
			}
		});
		btnSAVE.setIcon(SaveImage);
		
		final JButton btnNEXT = new JButton();
		btnNEXT.setBackground(Color.LIGHT_GRAY);
		btnNEXT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnNEXT, "Next Path", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				if(currentPath < listPath.size()) {
			            currentPath++;
					    String  strPath = "";
					    strPath += nodeStart;
					    graph.init();
						for (FlowEdge edge : listPath.get(currentPath - 1).getEdgeList()) {
							strPath+= " => " + edge.getTarget();
						}
						graph.paintPath(listPath.get(currentPath -1));
					JOptionPane.showMessageDialog(null, strPath, " Path", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		panelLEFTBUTTON.add(btnNEXT);
		btnNEXT.setIcon(NextImage);

		final JButton btnBACK = new JButton();
		btnBACK.setBackground(Color.LIGHT_GRAY);
		btnBACK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnBACK, "Previou Path", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				if(currentPath > 1) {
		            currentPath--;
				    String  strPath = "";
				    strPath += nodeStart;
				    graph.init();
					for (FlowEdge edge : listPath.get(currentPath -1 ).getEdgeList()) {
						strPath+= " => " + edge.getTarget();
					}
					graph.paintPath(listPath.get(currentPath -1));
				JOptionPane.showMessageDialog(null, strPath, "Previous Path", JOptionPane.INFORMATION_MESSAGE);
			}
			}
		});
		panelLEFTBUTTON.add(btnBACK);
		btnBACK.setIcon(BackImage);

		
		panelGRAPH = new JPanel();
		panelGRAPH.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(panelGRAPH, BorderLayout.CENTER);
		panelGRAPH.setLayout(new BorderLayout(0, 0));
		panelGRAPH.add(graph);
		
	}
	public void reset() {
		nodeStart = "";
		nodeEnd = "";
		currentPath = 1;
		graph.init();
		textFieldStart.setText("");
		textFieldEnd.setText("");
		listPath.clear();
	}
		
}
