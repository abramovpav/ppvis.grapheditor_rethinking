package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ContentPane;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.Desktop;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ExtJButton;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ToolPanel;

public class ExtJButtonListener implements ActionListener {

	@Override
	public void actionPerformed(final ActionEvent e) {

		final ExtJButton button = (ExtJButton) e.getSource();
		final JPanel container = button.getContainer();
		final ContentPane contentPane = (ContentPane) container.getParent();
		int mode = Desktop.VERTEX_MODE;
		if (isEdgeButton(button)) {
			mode = Desktop.EDGE_MODE;
		}
		contentPane.setEditMode(mode);
	}

	private boolean isEdgeButton(final ExtJButton button) {

		return button.getText() == ToolPanel.EDGE;
	}

}
