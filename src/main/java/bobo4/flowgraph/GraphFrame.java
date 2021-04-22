package bobo4.flowgraph;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.Window.Type;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GraphFrame extends JFrame {
	private JTextArea txtPATHLOG;

	private List<String> PathHistory = new ArrayList<>();

	public GraphFrame() {
		setTitle("Graph Path Demo");
		setForeground(Color.LIGHT_GRAY);
		setFont(new Font("Arial", Font.PLAIN, 14));
		setTitle("Graph");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 900);

		final IllustrateGraph graphIllustrate = new IllustrateGraph();
		graphIllustrate.setBounds(328, 11, 1146, 720);
		graphIllustrate.init();
		getContentPane().setLayout(null);

		getContentPane().add(graphIllustrate);

		JPanel panelButton = new JPanel();
		panelButton.setBounds(328, 742, 1146, 108);
		getContentPane().add(panelButton);
		panelButton.setLayout(null);

		JButton btnEND = new JButton("END");
		btnEND.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnEND.setBounds(218, 11, 125, 75);
		panelButton.add(btnEND);

		final JButton btnSTART = new JButton("START");
		btnSTART.addMouseListener(new MouseAdapter() {
			int numEnter = 0;

			@Override
			public void mouseClicked(MouseEvent e) {
				String nodeStart = JOptionPane.showInputDialog(null, "Fuck", "Fuck", JOptionPane.QUESTION_MESSAGE);
				graphIllustrate.paintNode(nodeStart, 0);
				PathHistory.add(nodeStart);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (numEnter == 0)
					JOptionPane.showMessageDialog(btnSTART, "Fuck", "Fuck", JOptionPane.PLAIN_MESSAGE);
				numEnter++;
			}
		});
		btnSTART.setBounds(28, 11, 125, 75);
		panelButton.add(btnSTART);

		final JButton btnNEXT = new JButton("NEXT");
		btnNEXT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
						PathHistory.add(vnext);
						graphIllustrate.paintNode(vnext, 0);
					}
				} else {
					JOptionPane.showMessageDialog(btnNEXT, "There's no start node", "Alert", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnNEXT.setBounds(598, 11, 125, 75);
		panelButton.add(btnNEXT);

		JButton btnBACK = new JButton("BACK");
		btnBACK.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				graphIllustrate.paintNode(PathHistory.get(PathHistory.size() - 1), 1);
				PathHistory.remove(PathHistory.size() - 1);
			}
		});
		btnBACK.setBounds(788, 11, 125, 75);
		panelButton.add(btnBACK);

		JButton btnLIST = new JButton("LIST NEXT");
		btnLIST.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
						PathHistory.add(vnext);
						graphIllustrate.paintNode(vnext, 0);
					}
				} else
					JOptionPane.showMessageDialog(btnNEXT, "There's no start node", "Alert", JOptionPane.ERROR_MESSAGE);

			}
		});
		btnLIST.setBounds(978, 11, 125, 75);
		panelButton.add(btnLIST);

		JButton btnRESET = new JButton("RESET");
		btnRESET.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				graphIllustrate.init();
				PathHistory.clear();
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

		txtPATHLOG = new JTextArea();
		txtPATHLOG.setEditable(false);
		txtPATHLOG.setFont(new Font("Courier New", Font.PLAIN, 14));
		txtPATHLOG.setBounds(10, 80, 286, 748);
		panelLEFT.add(txtPATHLOG);

		Scrollbar scrollbar = new Scrollbar();
		scrollbar.setBounds(279, 85, 17, 743);
		panelLEFT.add(scrollbar);

		setVisible(true);
	}

	public static void main(String[] args) {
		try {
			GraphFrame hung = new GraphFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
