package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.util.ExtJMenuItem;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ContentPane;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.Desktop;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ToolPanel;

public class InstrumentsActionListener implements ActionListener {

	@Override
	public void actionPerformed(final ActionEvent e) {

		final ExtJMenuItem button = (ExtJMenuItem) e.getSource();
		final JFrame container = button.getContainer();
		final ContentPane contentPane = (ContentPane) container.getContentPane();
		int mode = Desktop.VERTEX_MODE;
		if (isEdgeButton(button)) {
			mode = Desktop.EDGE_MODE;
		}
		contentPane.setEditMode(mode);
	}

	private boolean isEdgeButton(final ExtJMenuItem item) {

		return item.getText() == ToolPanel.EDGE;
	}

}
