package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Desktop extends JLayeredPane {
	private static final int NODE = 100;
	private static final int LINE = 50;
	private JPanel parent;
	
	public Desktop(JPanel parent) {
		super();
		this.parent = parent;
		initialize();
	}
	
	private void initialize() {
		setLayout(null);
	}
}
