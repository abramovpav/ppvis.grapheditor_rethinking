package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Edge;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.ModelInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Vertex;

public class Window extends JFrame {
	private static final int defaultX = 300;
	private static final int defaultY = 100;
	private static final int defaultWidth = 800;
	private static final int defaultHeight = 600;
	
	private ContentPane contentPane;
	private JPanel panel;

	/**
	 * Create the frame.
	 */
	public Window() {
		System.out.println("Window()");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Window.defaultX, Window.defaultY, Window.defaultWidth, Window.defaultHeight);
		this.setExtendedState(MAXIMIZED_BOTH);
		contentPane = new ContentPane();
		setContentPane(contentPane);
		Menu menu = new Menu();
		setJMenuBar(menu);
	}

	public void registerObserver(DaddyInterface daddy) {
		contentPane.registerObserver(daddy);
	}
}
