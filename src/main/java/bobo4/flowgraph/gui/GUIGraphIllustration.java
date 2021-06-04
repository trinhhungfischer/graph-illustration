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

import bobo4.flowgraph.elements.Graph;
import bobo4.flowgraph.utils.GraphIllustrate;

public class GUIGraphIllustration extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Dimension DEFAULT_SIZE = new Dimension(1600, 900);

	private JTextField textFieldStart;

	private Choice choice;
	private JTextArea txtPATHLOG;
	private JButton btnQUESTION;
	private Graph graphScrollPanel = new Graph();
	private JLabel lblNewLabel;
	private JTextField textFieldEnd;
	private JPanel panelGRAPH;
	public static boolean hasTimerTask = false;
	private JButton btnPAUSE;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIGraphIllustration frame = new GUIGraphIllustration();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIGraphIllustration() {
		setBackground(Color.LIGHT_GRAY);

		final GraphIllustrate GraphManager = new GraphIllustrate(graphScrollPanel);

		setForeground(Color.LIGHT_GRAY);
		setFont(new Font("Arial", Font.PLAIN, 14));
		setTitle("Graph");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1500, 900);
		setSize(DEFAULT_SIZE);

		// This variable to know whenever question be clicked
		final int[] isQuestion = { 0 };

		Image image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/zoom-out.png"))
				.getImage();
		Image newImg = image.getScaledInstance(ImageObserver.WIDTH * 30, ImageObserver.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon zoomOutImage = new ImageIcon(newImg);

		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/zoom-in.png")).getImage();
		newImg = image.getScaledInstance(ImageObserver.WIDTH * 30, ImageObserver.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon zoomInImage = new ImageIcon(newImg);

		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/question.png")).getImage();
		newImg = image.getScaledInstance(ImageObserver.WIDTH * 30, ImageObserver.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon QuestionImage = new ImageIcon(newImg);

		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/save.png")).getImage();
		newImg = image.getScaledInstance(ImageObserver.WIDTH * 30, ImageObserver.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon SaveImage = new ImageIcon(newImg);

		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/undo.png")).getImage();
		newImg = image.getScaledInstance(ImageObserver.WIDTH * 30, ImageObserver.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon UndoImage = new ImageIcon(newImg);

		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/redo.png")).getImage();
		newImg = image.getScaledInstance(ImageObserver.WIDTH * 30, ImageObserver.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon RedoImage = new ImageIcon(newImg);

		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/pause.png")).getImage();
		newImg = image.getScaledInstance(ImageObserver.WIDTH * 30, ImageObserver.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		final ImageIcon PauseImage = new ImageIcon(newImg);

		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/play.png")).getImage();
		newImg = image.getScaledInstance(ImageObserver.WIDTH * 30, ImageObserver.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		final ImageIcon PlayImage = new ImageIcon(newImg);

		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/speed-up.png")).getImage();
		newImg = image.getScaledInstance(ImageObserver.WIDTH * 30, ImageObserver.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon SpeedUpImage = new ImageIcon(newImg);

		image = new ImageIcon(GraphIllustrate.class.getResource("/bobo4/flowgraph/asset/speed-down.png")).getImage();
		newImg = image.getScaledInstance(ImageObserver.WIDTH * 30, ImageObserver.HEIGHT * 20,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon SpeedDownImage = new ImageIcon(newImg);

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
				try {
					txtPATHLOG.replaceRange(null, txtPATHLOG.getLineStartOffset(0),
							txtPATHLOG.getLineEndOffset(txtPATHLOG.getLineCount() - 1));
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}

				// Try to input node start by some method
				String nodeStart;

				if (!(textFieldStart.getText().equals(""))) {
					nodeStart = textFieldStart.getText();
				} else {
					nodeStart = JOptionPane.showInputDialog(null, "Input your start node", "Input",
							JOptionPane.QUESTION_MESSAGE);
					textFieldStart.setText(nodeStart);
				}

				GraphManager.Start(nodeStart, choice, lblNewLabel);
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
					GraphManager.Start(nodeStart, choice, lblNewLabel);
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
				String nodeEnd;
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

		final JButton btnRESET = new JButton("RESET");
		btnRESET.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnRESET, "Reset graph to default state", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				textFieldEnd.setText("");
				textFieldStart.setText("");
				btnPAUSE.setIcon(PauseImage);
				hasTimerTask = false;
				GraphManager.Reset(choice, lblNewLabel, txtPATHLOG, textFieldStart, textFieldEnd);
			}
		});
		btnRESET.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelHEAD.add(btnRESET);

		lblNewLabel = new JLabel("Current node is: None");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelHEAD.add(lblNewLabel);

		choice = new Choice();
		choice.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(choice, "Choose in list next node which can be reached",
							"Instruction", JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
			}
		});
		choice.add("<None>");

		choice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String nextNode = (String) e.getItem();
				GraphManager.ChoiceNode(nextNode, txtPATHLOG, choice, lblNewLabel);
			}
		});

		final JButton btnLIST = new JButton("LIST NEXT NODE");
		btnLIST.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLIST.setHorizontalAlignment(SwingConstants.LEFT);
		btnLIST.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnLIST, "List all next node which can be reached", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}

				GraphManager.ListNextNode(txtPATHLOG, choice, lblNewLabel);;
			}
		});

		panelHEAD.add(btnLIST);
		panelHEAD.add(choice);

		final JButton btnAUTO = new JButton("AUTO");
		btnAUTO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnAUTO,
							"Auto walk to end node, if end node is null \n max vertex is default.", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}

				GUIGraphIllustration.hasTimerTask = true;
				GraphManager.Auto(textFieldStart.getText(), textFieldEnd.getText(), choice, lblNewLabel, txtPATHLOG);

			}
		});
		
		JButton btnAUTONEXT = new JButton("AUTO NEXT");
		btnAUTONEXT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnRESET, "Reset graph to default state", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				GUIGraphIllustration.hasTimerTask = true;
				btnPAUSE.setIcon(PauseImage);
				GraphManager.AutoNextNode(choice, lblNewLabel, txtPATHLOG);
			}
		});
		
		final JButton btnNEXT = new JButton("NEXT");
		btnNEXT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnNEXT, "Random walk to next node", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}

				GraphManager.Next(choice, lblNewLabel, txtPATHLOG);
			}
		});
		btnNEXT.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelHEAD.add(btnNEXT);
		btnAUTONEXT.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelHEAD.add(btnAUTONEXT);
		btnAUTO.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelHEAD.add(btnAUTO);

		JButton btnLISTPATH = new JButton("LIST PATH");
		btnLISTPATH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnLIST, "List all next node which can be reached", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				GraphManager.ListPath(textFieldStart.getText(), textFieldEnd.getText(), txtPATHLOG, choice,
						lblNewLabel);
			}
		});
		btnLISTPATH.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelHEAD.add(btnLISTPATH);
		JPanel panelLEFTLOG = new JPanel();
		panelLEFTLOG.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(panelLEFTLOG, BorderLayout.WEST);
		panelLEFTLOG.setLayout(new BorderLayout(0, 0));

		JLabel lblPATHLOG = new JLabel("  PATH LOG  ");
		lblPATHLOG.setHorizontalAlignment(SwingConstants.CENTER);
		lblPATHLOG.setFont(new Font("Tahoma", Font.PLAIN, 35));
		panelLEFTLOG.add(lblPATHLOG, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		panelLEFTLOG.add(scrollPane);

		txtPATHLOG = new JTextArea();
		txtPATHLOG.setFont(new Font("Courier New", Font.PLAIN, 14));
		txtPATHLOG.setEditable(false);
		scrollPane.setViewportView(txtPATHLOG);

		JPanel panelLEFTBUTTON = new JPanel();
		panelLEFTBUTTON.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(panelLEFTBUTTON, BorderLayout.EAST);
		panelLEFTBUTTON.setLayout(new GridLayout(0, 1, 0, 0));

		final JButton btnZOOMIN = new JButton();
		btnZOOMIN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnZOOMIN, "Zoom in graph illustration", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}

				GraphManager.ZoomIn();
			}
		});
		panelLEFTBUTTON.add(btnZOOMIN);
		btnZOOMIN.setBackground(Color.LIGHT_GRAY);
		btnZOOMIN.setIcon(zoomInImage);

		final JButton btnZOOMOUT = new JButton();
		btnZOOMOUT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnZOOMOUT, "Zoom out graph illustration", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}

				GraphManager.ZoomOut();
			}
		});
		panelLEFTBUTTON.add(btnZOOMOUT);
		btnZOOMOUT.setBackground(Color.LIGHT_GRAY);
		btnZOOMOUT.setIcon(zoomOutImage);

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

				// Save graph image here
				GraphManager.Save();
			}
		});
		btnSAVE.setIcon(SaveImage);

		final JButton btnUNDO = new JButton();
		panelLEFTBUTTON.add(btnUNDO);
		btnUNDO.setBackground(Color.LIGHT_GRAY);

		btnUNDO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Instruction your user here
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnUNDO, "Turn back previous node", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}

				GraphManager.Undo(choice, lblNewLabel, txtPATHLOG);
			}

		});
		btnUNDO.setIcon(UndoImage);

		final JButton btnREDO = new JButton();
		panelLEFTBUTTON.add(btnREDO);
		btnREDO.setBackground(Color.LIGHT_GRAY);
		btnREDO.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnREDO, "Turn back previous node", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}

				GraphManager.Redo(choice, lblNewLabel, txtPATHLOG);
			}
		});
		btnREDO.setIcon(RedoImage);

		final JButton btnSPEEDUP = new JButton();
		btnSPEEDUP.setBackground(Color.LIGHT_GRAY);
		btnSPEEDUP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnSPEEDUP, "Speed up when a path running", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				GraphManager.SpeedUp();
			}
		});
		panelLEFTBUTTON.add(btnSPEEDUP);
		btnSPEEDUP.setIcon(SpeedUpImage);

		final JButton btnSPEEDDOWN = new JButton();
		btnSPEEDDOWN.setBackground(Color.LIGHT_GRAY);
		btnSPEEDDOWN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnSPEEDDOWN, "Speed down when a path running", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				GraphManager.SpeedDown();
			}
		});
		panelLEFTBUTTON.add(btnSPEEDDOWN);
		btnSPEEDDOWN.setIcon(SpeedDownImage);

		btnPAUSE = new JButton();
		btnPAUSE.setBackground(Color.LIGHT_GRAY);
		btnPAUSE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isQuestion[0] == 1) {
					btnQUESTION.setBackground(Color.LIGHT_GRAY);
					JOptionPane.showMessageDialog(btnPAUSE, "Pause when a path running", "Instruction",
							JOptionPane.PLAIN_MESSAGE);
					isQuestion[0] = 0;
				}
				if (hasTimerTask)
					if (GraphIllustrate.isRunAuto()) {
						GraphManager.Stop();
						btnPAUSE.setIcon(PlayImage);
					} else {
						GraphManager.Continue();
						btnPAUSE.setIcon(PauseImage);
					}
			}

		});
		panelLEFTBUTTON.add(btnPAUSE);
		btnPAUSE.setIcon(PauseImage);

		panelGRAPH = new JPanel();
		panelGRAPH.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(panelGRAPH, BorderLayout.CENTER);
		panelGRAPH.setLayout(new BorderLayout(0, 0));

		panelGRAPH.add(graphScrollPanel);

	}
}
