package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.util.Support;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ContentPane;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ExtJButton;

public class NewTabListener implements ActionListener {

	@Override
	public void actionPerformed(final ActionEvent e) {

		final ExtJButton button = (ExtJButton) e.getSource();
		final JPanel container = button.getContainer();
		final ContentPane contentPane = (ContentPane) container.getParent();
		Support.newTab(contentPane);

	}

}
