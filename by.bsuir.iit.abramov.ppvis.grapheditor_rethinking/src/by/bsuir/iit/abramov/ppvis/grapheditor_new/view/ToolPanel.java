package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.NewTabListener;

public class ToolPanel extends JPanel {
	JPanel parent;
	public ToolPanel(JPanel parent) {
		super();
		System.out.println("ToolPanel()");
		this.parent = parent;
		initialize();
		createComponents();
	}
	
	private void initialize() {
		parent.add(this, BorderLayout.EAST);
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));		
	}
	
	private void createComponents() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		add(toolBar);
		//buttons
		ExtJButton button = new ExtJButton("New Tab", this);
		toolBar.add(button);
		button.addActionListener(new NewTabListener());
		button = new ExtJButton("Node", this);
		toolBar.add(button);
		button = new ExtJButton("Edge", this);
		toolBar.add(button);
	}
	
	public JPanel getParent() {
		return this.parent;
	}
}
