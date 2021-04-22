package bobo4.flowgraph.optionFrame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

public class GUImain extends JFrame{
	private JPanel optionPanel;
	private JPanel groupInfoPanel;
	private JButton buttonPrintGraph;
	private JButton buttonPrintPath;
	private JButton buttonSimulationPath;
	public GUImain() {
		this.setSize(1024, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationByPlatform(true);
		
		initGroupInfo();
		initOption();
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, groupInfoPanel, optionPanel);
		splitPane.setDividerLocation(350);
		
		add(splitPane);
		this.setVisible(true);
	}
	
	private void initGroupInfo() {
		groupInfoPanel = new JPanel();
//		groupInfoPanel.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel label = new JLabel("<html><br/><br/>WELCOME TO OUR APPLICATION! <br/><br/>20170106 - Nguyen Mai Phuong <br/>20190054 - Trinh Quang Hung <br/>20194058 - Nguyen Huy Hoang <br/>20194013 - Cao Nhu Dat <br/>20193988 - Tran Tien Bang <br/>20194762 - Nguyen Trung Hieu</html>", SwingConstants.CENTER);
		label.setFont (label.getFont ().deriveFont (20.0f));
		label.setVisible(true);
		groupInfoPanel.add(label);
	}
	
	private void initOption() {
		optionPanel = new JPanel();
		optionPanel.setLayout(new FlowLayout());
		
		initButtonPrintGraph();
		initButtonPrintPath();
		initButtonSimulationPath();
	}
	
	private void initButtonPrintGraph() {
		ActionListener action =  new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				//TODO: add action
	        }  
		};
		
		buttonPrintGraph = new JButton("1. PRINT GRAPH");
		buttonPrintGraph.setEnabled(true);
		buttonPrintGraph.setVisible(true);
		buttonPrintGraph.setPreferredSize(new Dimension(150, 60));
		buttonPrintGraph.addActionListener(action);
		
		optionPanel.add(buttonPrintGraph);
	}
	
	private void initButtonPrintPath() {
		ActionListener action =  new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				//TODO: add action
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
		ActionListener action =  new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				//TODO: add action
	        }  
		};
		
		buttonSimulationPath = new JButton("3. SIMULATION PATH");
		buttonSimulationPath.setEnabled(true);
		buttonSimulationPath.setVisible(true);
		buttonSimulationPath.setPreferredSize(new Dimension(150, 60));
		buttonSimulationPath.addActionListener(action);
		
		optionPanel.add(buttonSimulationPath);
	}
	
	public static void main(String[] args) {
		GUImain guiMain = new GUImain();
	}
}
