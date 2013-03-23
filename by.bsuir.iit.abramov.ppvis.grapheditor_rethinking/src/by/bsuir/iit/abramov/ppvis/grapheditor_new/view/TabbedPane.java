package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabbedPane extends  JTabbedPane {
	private JPanel parent;
	public TabbedPane(JPanel parent) {
		super();
		this.parent = parent;
		initialize();
	}
	
	private void initialize() {
		parent.add(this, BorderLayout.CENTER);
		setFocusable(false);
	}
}
