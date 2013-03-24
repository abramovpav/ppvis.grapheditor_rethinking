package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopObserver;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.GraphObserverInterface;

public class Desktop extends JLayeredPane implements DesktopInterface {
	private static final int NODE = 100;
	private static final int LINE = 50;
	private final int ID;
	private List<DesktopObserver> observers;
	private List<VertexComponent> vertices;
	private List<VertexComponent> selectedVertices;
	private List<EdgeComponent> edges;
	private List<EdgeComponent> selectedEdges;
	private JPanel parent;
	
	public Desktop(int ID, JPanel parent) {
		super();
		this.ID = ID;
		this.parent = parent;
		initialize();
	}
	
	private void initialize() {
		setLayout(null);
		observers = new ArrayList<DesktopObserver>();
		vertices = new ArrayList<VertexComponent>();
		selectedVertices = new ArrayList<VertexComponent>();
		edges = new ArrayList<EdgeComponent>();
		selectedEdges = new ArrayList<EdgeComponent>();
	}
/*
	@Override
	public void registerObserver(DesktopObserver observer) {
		observers.add(observer);		
	}

	@Override
	public void removeObserver(DesktopObserver observer) {
		observers.remove(observer);
		
	}

	@Override
	public void notifyObservers() {
		Iterator<DesktopObserver> iterator = observers.iterator();
		while(iterator.hasNext()) {
			iterator.next().update();
		}
	}*/


	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerObserver(GraphObserverInterface observer) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void removeObserver(GraphObserverInterface observer) {
		// TODO Auto-generated method stub
		
	}
}
