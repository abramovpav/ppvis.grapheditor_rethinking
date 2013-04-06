package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopListener;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopObserverInterface;

public class Desktop extends JLayeredPane implements DesktopInterface {
	public static final int					NODE_LAYER			= 100;
	public static final int					EDGE_LAYER			= 50;
	public static final int					VERTEX_MODE			= 0;
	public static final int					EDGE_MODE			= 1;
	public static final int					TOP_LAYER			= 0;
	private int								editMode			= 0;

	private final int						ID;
	private List<VertexComponentInterface>	selectedVertices;
	private List<DesktopObserverInterface>	observers;
	private List<VertexComponentInterface>	vertices;
	private List<EdgeComponentInterface>	selectedEdges;
	private List<EdgeComponentInterface>	edges;
	private final JPanel					parent;
	private EdgeComponentInterface			currentPaintEdge	= null;

	public Desktop(final int ID, final JPanel parent) {

		super();
		System.out.println("Desktop(" + getID() + ")");
		this.ID = ID;
		this.parent = parent;
		initialize();
		final DesktopListener listener = new DesktopListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}

	@Override
	public VertexComponentInterface addNode(final String ID, final int x, final int y) {

		System.out.println("Desktop(" + getID() + ") - addNode()");
		final VertexComponentInterface vertex = new VertexComponent(ID, x, y, this);
		vertices.add(vertex);

		add((VertexComponent) vertex);
		setLayer((VertexComponent) vertex, Desktop.NODE_LAYER);
		notifyObservers(vertex);
		return vertex;
	}

	@Override
	public void addSelectedEdge(final EdgeComponentInterface edge) {

		System.out.println("Desktop(" + getID() + ") - addSelectedEdge()");
		selectedEdges.add(edge);
	}

	@Override
	public void addSelectedVertex(final VertexComponentInterface vertex) {

		System.out.println("Desktop(" + getID() + ") - addSelectedVertex()");
		selectedVertices.add(vertex);
	}

	@Override
	public void deleteEdge(final EdgeComponentInterface edge) {

		removeEdge(edge);
		notifyObserversLightDeleteEdge(edge);

	}

	private void deleteSelectedEdges() {

		final Iterator<EdgeComponentInterface> iterator = selectedEdges.iterator();
		while (iterator.hasNext()) {
			final EdgeComponentInterface edge = iterator.next();
			removeEdge(edge);

			notifyObserversDeleteEdge(edge);
			iterator.remove();
		}
	}

	@Override
	public void deleteSelectedItems() {

		deleteSelectedEdges();
		deleteSelectedVertices();

	}

	private void deleteSelectedVertices() {

		final Iterator<VertexComponentInterface> iterator = selectedVertices.iterator();
		Rectangle rec = new Rectangle();
		while (iterator.hasNext()) {
			final VertexComponentInterface vertex = iterator.next();
			rec = ((VertexComponent) vertex).getBounds();
			remove((VertexComponent) vertex);
			this.repaint(rec);
			notifyObserversDeleteVertex(vertex);
			iterator.remove();
		}
	}

	@Override
	public void finishPaintEdge(final VertexComponentInterface vertex) {

		currentPaintEdge.setSecondVertex(vertex);
		vertex.registerEdge(currentPaintEdge);
		notifyObserversNewEdge(currentPaintEdge);
		currentPaintEdge = null;

	}

	@Override
	public int getEditMode() {

		return editMode;
	}

	@Override
	public final int getID() {

		return ID;
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
	public boolean isPaintEdge() {

		if (currentPaintEdge != null) {
			return true;
		}
		return false;
	}

	@Override
	public void moveSelectedVertices(final int x, final int y) {

		System.out.println("Desktop(" + getID() + ") - moveSelectedVertices(" + x + ", "
				+ y + ")");
		final Iterator<VertexComponentInterface> iterator = selectedVertices.iterator();
		while (iterator.hasNext()) {
			iterator.next().moveVertex(x, y);
		}
	}

	@Override
	public void notifyObservers() {

		final Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update();
		}
	}

	@Override
	public void notifyObservers(final EdgeComponentInterface edge) {

		final Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update(edge);
		}

	}

	@Override
	public void notifyObservers(final VertexComponentInterface vertex) {

		final Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update(vertex);
		}

	}

	@Override
	public void notifyObserversAllInformation() {

		final Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update(vertices, selectedVertices, edges, selectedEdges);
		}

	}

	@Override
	public void notifyObserversBasicInformation() {

		final Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update(vertices, edges);
		}

	}

	@Override
	public void notifyObserversDeleteEdge(final EdgeComponentInterface edge) {

		final Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().deleteEdge(edge);
		}

	}

	@Override
	public void notifyObserversDeleteVertex(final VertexComponentInterface vertex) {

		final Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().deleteVertex(vertex);
		}

	}

	@Override
	public void notifyObserversLightDeleteEdge(final EdgeComponentInterface edge) {

		final Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().lightDeleteEdge(edge);
		}

	}

	@Override
	public void notifyObserversNewEdge(final EdgeComponentInterface edge) {

		final Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().newEdge(edge);
		}

	}

	@Override
	public void notifyObserversNewVertex(final int x, final int y) {

		final Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().newVertex(x, y);
		}

	}

	@Override
	public void paintEdge(final EdgeComponentInterface edge) {

		System.out.println("Desktop(" + getID() + ") - paintEdge()");
		currentPaintEdge = edge;
		add((EdgeComponent) edge);
		setLayer((EdgeComponent) edge, Desktop.EDGE_LAYER);

	}

	@Override
	public void registerObserver(final DesktopObserverInterface observer) {

		observers.add(observer);
		observer.setDesktop(this);
	}

	private void removeEdge(final EdgeComponentInterface edge) {

		Rectangle rec = new Rectangle();
		rec = ((EdgeComponent) edge).getBounds();
		this.remove((EdgeComponent) edge);
		this.repaint(rec);
	}

	@Override
	public void removeObserver(final DesktopObserverInterface observer) {

		observers.remove(observer);
	}

	@Override
	public void setEditMode(final int editMode) {

		System.out.println("Desktop(" + getID() + ") - setEditMode(" + editMode + ")");
		this.editMode = editMode;
	}

	@Override
	public void setTempVertexPaintEdge(final int x, final int y) {

		currentPaintEdge.setSecondTempVertex(x, y);

	}

	@Override
	public void unselectAll() {

		System.out.println("Desktop(" + getID() + ") - unselectAll()");
		final Iterator<VertexComponentInterface> iterator = selectedVertices.iterator();
		while (iterator.hasNext()) {
			iterator.next().unselectSpecial();
			iterator.remove();
		}

		final Iterator<EdgeComponentInterface> iteratorEdge = selectedEdges.iterator();
		while (iteratorEdge.hasNext()) {
			iteratorEdge.next().unselectSpecial();
			iteratorEdge.remove();
		}
	}

	@Override
	public void unselectVertex(final VertexComponentInterface vertex) {

		System.out.println("Desktop(" + getID() + ") - unselectVertex()");
		if (selectedVertices.contains(vertex)) {
			selectedVertices.remove(vertex);
		}

	}
	
	@Override
	public void unselectEdge(final EdgeComponentInterface edge) {

		System.out.println("Desktop(" + getID() + ") - unselectEdge()");
		if (selectedEdges.contains(edge)) {
			selectedEdges.remove(edge);
		}

	}

}
