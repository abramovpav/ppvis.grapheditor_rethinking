package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Edge;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Vertex;

public class Window extends JFrame implements GraphObserver{

	private JPanel contentPane;
	private JPanel panel;

	/**
	 * Create the frame.
	 */
	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 800, 600);
		this.setExtendedState(MAXIMIZED_BOTH);
		contentPane = new ContentPane();
		setContentPane(contentPane);
		
		Menu menu = new Menu();
		setJMenuBar(menu);
		
		
	}

	@Override
	public void update(List<Vertex> vertices, List<Edge> lines) {
		
	}
}
