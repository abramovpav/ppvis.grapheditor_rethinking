package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ExtJButton extends JButton {
	private JPanel container;
	public ExtJButton(String caption, JPanel container) {
		super(caption);
		this.container = container;
	}
	
	public JPanel getContainer() {
		return this.container;
	}
}
