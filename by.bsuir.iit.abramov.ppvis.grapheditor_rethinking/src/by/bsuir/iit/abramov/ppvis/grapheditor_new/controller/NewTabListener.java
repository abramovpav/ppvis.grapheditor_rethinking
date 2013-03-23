package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ContentPane;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.Desktop;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ExtJButton;

public class NewTabListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ExtJButton button = (ExtJButton)e.getSource();
		JPanel container = button.getContainer();
		ContentPane contentPane = (ContentPane)container.getParent();
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.addTab(scrollPane);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));				
		panel.setPreferredSize(new Dimension(800, 600));
		scrollPane.setViewportView(panel);
		
		Desktop desktop = new Desktop(panel);
		panel.add(desktop);
		
		
	}

}
