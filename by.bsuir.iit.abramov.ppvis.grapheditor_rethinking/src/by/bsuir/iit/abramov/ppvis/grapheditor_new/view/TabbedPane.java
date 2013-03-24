package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.GraphObserverInterface;


public class TabbedPane extends JTabbedPane {
	private JPanel parent;
	private List<GraphObserverInterface> observers;

	public TabbedPane(JPanel parent) {
		super();
		this.parent = parent;
		initialize();
	}

	private void initialize() {
		parent.add(this, BorderLayout.CENTER);
		setFocusable(false);
		observers = new ArrayList<GraphObserverInterface>();
	}
}
