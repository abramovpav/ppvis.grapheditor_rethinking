package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ContentPane extends JPanel {
	private ToolPanel toolPanel;
	private TabbedPane tabbedPane;
	public ContentPane() {
		super();
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0,0));
		setBounds(0,0, 800, 600);
		this.toolPanel = new ToolPanel(this);
		this.tabbedPane = new TabbedPane(this);
	}
	
	public void addTab(JComponent component) {
		if (component != null)
			tabbedPane.addTab("New Tab", null, component, null);
	}
}
