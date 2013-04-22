package by.bsuir.iit.abramov.ppvis.grapheditor_new.util;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Graph;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ContentPane;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.Desktop;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ExtJScrollPane;

public class Support {
	public static void newTab(final ContentPane contentPane) {

		final JPanel panel = new JPanel();
		final DesktopInterface desktop = new Desktop(contentPane,
				contentPane.getNextTabID(), panel);
		final ExtJScrollPane scrollPane = new ExtJScrollPane(desktop);
		contentPane.addTab(scrollPane);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setPreferredSize(new Dimension(800, 600));
		scrollPane.setViewportView(panel);

		panel.add((Desktop) desktop);
		contentPane.newTab(desktop);
	}

	public static void newTab(final ContentPane contentPane, final Graph graph,
			final String title) {

		final JPanel panel = new JPanel();
		final DesktopInterface desktop = new Desktop(contentPane,
				contentPane.getNextTabID(), panel);
		final ExtJScrollPane scrollPane = new ExtJScrollPane(desktop);
		contentPane.addTab(scrollPane, title);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setPreferredSize(new Dimension(800, 600));
		scrollPane.setViewportView(panel);

		panel.add((Desktop) desktop);
		contentPane.newTab(desktop);
		desktop.setState(graph);
	}
}
