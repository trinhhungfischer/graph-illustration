package bobo4.flowgraph;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PrintGraph extends JFrame {

	public PrintGraph() {
		// TODO Auto-generated constructor stub
		setTitle("Graph Path Demo");
		setForeground(Color.LIGHT_GRAY);
		setFont(new Font("Arial", Font.PLAIN, 14));
		setTitle("Graph");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1920, 1080);
		
		final Graph graphIllustrate = new Graph();
		graphIllustrate.setBounds(10, 11, 1884, 1019);
		graphIllustrate.init();
		graphIllustrate.saveImage();
		getContentPane().setLayout(null);
		
		JButton btnRETURN = new JButton("RETURN");
		btnRETURN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		btnRETURN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRETURN.setBounds(10, 34, 108, 71);
		getContentPane().add(btnRETURN);
		getContentPane().add(graphIllustrate);
		
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		PrintGraph print = new PrintGraph();
	}
}
