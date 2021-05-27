package bobo4.flowgraph.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import bobo4.flowgraph.readgraph.ReadGraph;
import bobo4.flowgraph.utils.FindPath;
import bobo4.flowgraph.utils.GraphIllustrate;
import bobo4.flowgraph.utils.PrintGraph;

import java.awt.GridLayout;

public class GUImain extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel optionPanel;
	private JPanel groupInfoPanel;
	private JButton buttonPrintGraph;
	private JButton buttonPrintPath;
	private JButton buttonSimulationPath;
	private JButton buttonReadGraph;

	public GUImain() {
		this.setSize(1024, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationByPlatform(true);

		while (FileChooser.DirectoryPath == null) {
			new FileChooser();
		}
		new ReadGraph(FileChooser.DirectoryPath);

		initGroupInfo();
		initOption();
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, groupInfoPanel, optionPanel);
		splitPane.setDividerLocation(350);

		getContentPane().add(splitPane);
	}

	private void initGroupInfo() {
		groupInfoPanel = new JPanel();

		JLabel label = new JLabel(
				"<html><br/><br/>WELCOME TO OUR APPLICATION! <br/><br/>20170106 - Nguyen Mai Phuong <br/>20190054 - Trinh Quang Hung <br/>20194058 - Nguyen Huy Hoang <br/>20194013 - Cao Nhu Dat <br/>20193988 - Tran Tien Bang <br/>20194762 - Nguyen Trung Hieu</html>",
				SwingConstants.CENTER);
		label.setFont(label.getFont().deriveFont(20.0f));
		label.setVisible(true);
		groupInfoPanel.add(label);
	}

	private void initOption() {
		optionPanel = new JPanel();

		initButtonPrintGraph();
		initButtonPrintPath();
		initButtonSimulationPath();
		initButtonReadGraph();
	}

	private void initButtonPrintGraph() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: add action
				PrintGraph graph = new PrintGraph();
				graph.setVisible(true);
			}
		};

		buttonPrintGraph = new JButton("1. PRINT GRAPH");
		buttonPrintGraph.setEnabled(true);
		buttonPrintGraph.setVisible(true);
		buttonPrintGraph.setPreferredSize(new Dimension(150, 60));
		buttonPrintGraph.addActionListener(action);
		optionPanel.setLayout(new GridLayout(0, 4, 0, 0));

		optionPanel.add(buttonPrintGraph);
	}

	private void initButtonPrintPath() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: add action
				FindPath path = new FindPath("1", "6");
				path.print();
			}
		};

		buttonPrintPath = new JButton("2. PRINT PATH");
		buttonPrintPath.setEnabled(true);
		buttonPrintPath.setVisible(true);
		buttonPrintPath.setPreferredSize(new Dimension(150, 60));
		buttonPrintPath.addActionListener(action);

		optionPanel.add(buttonPrintPath);
	}

	private void initButtonSimulationPath() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: add action
				try {
					GUIGraphIllustration illustration = new GUIGraphIllustration();
					illustration.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};

		buttonSimulationPath = new JButton("3. SIMULATION PATH");
		buttonSimulationPath.setEnabled(true);
		buttonSimulationPath.setVisible(true);
		buttonSimulationPath.setPreferredSize(new Dimension(150, 60));
		buttonSimulationPath.addActionListener(action);

		optionPanel.add(buttonSimulationPath);
	}

	private void initButtonReadGraph() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: add action
				do {
					new FileChooser();
				} while (FileChooser.DirectoryPath == null);
				new ReadGraph(FileChooser.DirectoryPath);
			}
		};

		buttonReadGraph = new JButton("4. READ ANOTHER GRAPH DATA");
		buttonReadGraph.setEnabled(true);
		buttonReadGraph.setVisible(true);
		buttonReadGraph.setPreferredSize(new Dimension(150, 60));
		buttonReadGraph.addActionListener(action);

		optionPanel.add(buttonReadGraph);
	}

	public static void main(String[] args) {
		GUImain hung = new GUImain();
		hung.setVisible(true);
	}

}
