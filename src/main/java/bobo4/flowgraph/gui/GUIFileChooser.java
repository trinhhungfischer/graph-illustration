package bobo4.flowgraph.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GUIFileChooser {

	public static String DirectoryPath;

	public GUIFileChooser() {
		JFileChooser file = new JFileChooser();
		String str = null;
		file.setMultiSelectionEnabled(true);
		file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		file.setFileHidingEnabled(false);
		file.setCurrentDirectory(new File("./src/main/java//bobo4/flowgraph/graphdata"));
		
		if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		    java.io.File f = file.getSelectedFile();
			str = f.getPath();
		}
		GUIFileChooser.DirectoryPath = str;

		if (GUIFileChooser.DirectoryPath == null) {
			JOptionPane.showMessageDialog(null, "You need choose one file", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}
}
