package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopListener;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopObserverInterface;

public class Desktop extends JLayeredPane implements DesktopInterface {
	public static final int NODE = 100;
	public static final int LINE = 50;
	public static final int TOP_LAYER = 0;
	private final int ID;
	private int editMode = 0;
	private List<DesktopObserverInterface> observers;
	private List<VertexComponentInterface> vertices;
	private List<VertexComponentInterface> selectedVertices;
	private List<EdgeComponentInterface> edges;
	private List<EdgeComponentInterface> selectedEdges;
	private JPanel parent;

	public Desktop(int ID, JPanel parent) {
		super();
		System.out.println("Desktop()");
		this.ID = ID;
		this.parent = parent;
		initialize();
		DesktopListener listener = new DesktopListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}

	private void initialize() {
		setLayout(null);
		observers = new ArrayList<DesktopObserverInterface>();
		vertices = new ArrayList<VertexComponentInterface>();
		selectedVertices = new ArrayList<VertexComponentInterface>();
		edges = new ArrayList<EdgeComponentInterface>();
		selectedEdges = new ArrayList<EdgeComponentInterface>();
	}

	@Override
	public int getID() {
		return this.ID;
	}

	public int getEditMode() {
		return this.editMode;
	}

	public void setEditMode(int editMode) {
		this.editMode = editMode;
	}

	@Override
	public void notifyObservers() {
		Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update();
		}
	}

	@Override
	public void notifyObserversAllInformation() {
		Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update(vertices, selectedVertices, edges,
					selectedEdges);
		}

	}

	@Override
	public void notifyObserversBasicInformation() {
		Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update(vertices, edges);
		}

	}

	@Override
	public void notifyObservers(VertexComponentInterface vertex) {
		Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update(vertex);
		}

	}

	@Override
	public void notifyObservers(EdgeComponentInterface edge) {
		Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update(edge);
		}

	}

	@Override
	public void registerObserver(DesktopObserverInterface observer) {
		observers.add(observer);
		observer.setDesktop(this);
	}

	@Override
	public void removeObserver(DesktopObserverInterface observer) {
		observers.remove(observer);
	}

	@Override
	public void addNode(String ID, int x, int y) {
		System.out.println("Desktop(" + getID() + ") - addNode()");
		VertexComponentInterface vertex = new VertexComponent(ID, x, y, this);
		vertices.add(vertex);
		add((VertexComponent)vertex);
		notifyObservers(vertex);
	}

	@Override
	public void addSelectedVertex(VertexComponentInterface vertex) {
		selectedVertices.add(vertex);
	}

	@Override
	public void notifyObserversNewVertex(int x, int y) {
		Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().newVertex(x, y);
		}
		
	}

}
