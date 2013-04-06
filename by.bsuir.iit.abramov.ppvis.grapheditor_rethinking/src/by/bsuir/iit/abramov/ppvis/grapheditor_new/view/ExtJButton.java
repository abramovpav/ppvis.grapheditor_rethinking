package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ExtJButton extends JButton {
	private final JPanel	container;
	private final String	caption;

	public ExtJButton(final String caption, final JPanel container) {

		super(caption);
		this.container = container;
		this.caption = caption;
		setFocusable(false);
	}

	public String getCaption() {

		return caption;
	}

	public JPanel getContainer() {

		return container;
	}
}
