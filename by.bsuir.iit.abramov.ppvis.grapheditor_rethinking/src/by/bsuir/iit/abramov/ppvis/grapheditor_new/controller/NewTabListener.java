package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ContentPane;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.Desktop;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ExtJButton;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ExtJScrollPane;

public class NewTabListener implements ActionListener {

	@Override
	public void actionPerformed(final ActionEvent e) {

		final ExtJButton button = (ExtJButton) e.getSource();
		final JPanel container = button.getContainer();
		final ContentPane contentPane = (ContentPane) container.getParent();

		final JPanel panel = new JPanel();
		final DesktopInterface desktop = new Desktop(contentPane.getNextTabID(), panel);
		final ExtJScrollPane scrollPane = new ExtJScrollPane(desktop);
		contentPane.addTab(scrollPane);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setPreferredSize(new Dimension(800, 600));
		scrollPane.setViewportView(panel);

		panel.add((Desktop) desktop);
		contentPane.newTab(desktop);

	}

}
