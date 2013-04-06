package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import javax.swing.JScrollPane;

public class ExtJScrollPane extends JScrollPane {
	private final DesktopInterface	desktop;

	public ExtJScrollPane(final DesktopInterface desktop) {

		super();
		this.desktop = desktop;
	}

	public final DesktopInterface getDesktop() {

		return desktop;
	}
}
