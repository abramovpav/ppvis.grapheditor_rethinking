package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabbedPane extends JTabbedPane {
	private final JPanel	parent;

	public TabbedPane(final JPanel parent) {

		super();
		System.out.println("TabbedPane()");
		this.parent = parent;
		initialize();
	}

	public void deleteSelectedItems() {

		final DesktopInterface desktop = ((ExtJScrollPane) getSelectedComponent())
				.getDesktop();
		desktop.deleteSelectedItems();
	}

	private void initialize() {

		parent.add(this, BorderLayout.CENTER);
		setFocusable(false);
	}

	public void setEditMode(final int mode) {

		final DesktopInterface desktop = ((ExtJScrollPane) getSelectedComponent())
				.getDesktop();
		desktop.setEditMode(mode);
	}

	public void unselectAll() {

		final DesktopInterface desktop = ((ExtJScrollPane) getSelectedComponent())
				.getDesktop();
		desktop.unselectAll();
	}

}
