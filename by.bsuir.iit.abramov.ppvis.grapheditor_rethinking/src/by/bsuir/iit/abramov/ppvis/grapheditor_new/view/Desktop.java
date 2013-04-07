package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopListener;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopObserver;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Edge;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Graph;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Vertex;

public class Desktop extends JLayeredPane implements DesktopInterface {
	public static final int					NODE_LAYER			= 100;
	public static final int					EDGE_LAYER			= 50;
	public static final int					VERTEX_MODE			= 0;
	public static final int					EDGE_MODE			= 1;
	public static final int					TOP_LAYER			= 0;
	private int								editMode			= 0;
	private final int						ID;
	private List<VertexComponentInterface>	selectedVertices;
	private List<DesktopObserver>			observers;
	private List<VertexComponentInterface>	vertices;
	private List<EdgeComponentInterface>	selectedEdges;
	// private List<EdgeComponentInterface> edges;
	private final JPanel					parent;
	private EdgeComponentInterface			currentPaintEdge	= null;
	private boolean							saved				= false;

	public Desktop(final int ID, final JPanel parent) {

		super();
		this.ID = ID;
		System.out.println("Desktop(" + getID() + ")");
		this.parent = parent;
		initialize();
		final DesktopListener listener = new DesktopListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}

	@Override
	public VertexComponentInterface addNode(final String ID, final int x, final int y) {

		saved = false;
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

		saved = false;
		System.out.println("Desktop(" + getID() + ") - addSelectedEdge()");
		selectedEdges.add(edge);
	}

	@Override
	public void addSelectedVertex(final VertexComponentInterface vertex) {

		saved = false;
		System.out.println("Desktop(" + getID() + ") - addSelectedVertex()");
		selectedVertices.add(vertex);
	}

	@Override
	public void cancelPaintEdge() {

		unselectAll();
		currentPaintEdge.deleteFromVertices();
		removeEdge(currentPaintEdge);
		currentPaintEdge = null;

	}

	@Override
	public void clean() {

		selectedVertices.clear();
		selectedEdges.clear();
		vertices.clear();

	}

	@Override
	public void deleteEdge(final EdgeComponentInterface edge) {

		saved = false;
		removeEdge(edge);
		notifyObserversLightDeleteEdge(edge);

	}

	private void deleteSelectedEdges() {

		saved = false;
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

		saved = false;
		final Iterator<VertexComponentInterface> iterator = selectedVertices.iterator();
		while (iterator.hasNext()) {
			final VertexComponentInterface vertex = iterator.next();
			removeVertex(vertex);
			notifyObserversDeleteVertex(vertex);
			iterator.remove();
		}
	}

	private final VertexComponentInterface findVertexByID(final String ID) {

		final Iterator<VertexComponentInterface> iterator = vertices.iterator();
		while (iterator.hasNext()) {
			final VertexComponentInterface vertex = iterator.next();
			if (vertex.getID() == ID) {
				return vertex;
			}
		}
		return null;
	}

	@Override
	public void finishPaintEdge(final VertexComponentInterface vertex) {

		saved = false;
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

	@Override
	public List<VertexComponentInterface> getVertices() {

		return vertices;
	}

	private void initialize() {

		saved = false;
		setLayout(null);
		observers = new ArrayList<DesktopObserver>();
		vertices = new ArrayList<VertexComponentInterface>();
		selectedVertices = new ArrayList<VertexComponentInterface>();
		// edges = new ArrayList<EdgeComponentInterface>();
		selectedEdges = new ArrayList<EdgeComponentInterface>();
	}

	private int isDigit(final String text) {

		int k = 0, d = 1, s = 0;

		for (int i = 0; i < text.length(); ++i) {
			if (Character.isDigit(text.charAt(i))) {
				s += Character.getNumericValue(text.charAt(i)) * d;
				d *= 10;
				k++;
			} else {
				return -1;
			}
		}
		if (k > 0) {
			return s;
		} else {
			return -1;
		}
	}

	private boolean isOneSelectedEdge() {

		return selectedVertices.size() == 0 && selectedEdges.size() == 1;
	}

	private boolean isOneSelectedVertex() {

		return selectedVertices.size() == 1 && selectedEdges.size() == 0;
	}

	@Override
	public boolean isPaintEdge() {

		return currentPaintEdge != null;
	}

	@Override
	public boolean isSaved() {

		return saved;
	}

	private void moveNotSelectedVertices(final int x, final int y) {

		saved = false;
		System.out.println("Desktop(" + getID() + ") - moveNotSelectedVertices(" + x
				+ ", " + y + ")");
		final Iterator<VertexComponentInterface> iterator = vertices.iterator();
		while (iterator.hasNext()) {
			final VertexComponentInterface vertex = iterator.next();
			if (!vertex.isSelected()) {
				vertex.moveVertex(x, y);
			}
		}
	}

	@Override
	public void moveSelectedVertices(final int x, final int y) {

		saved = false;
		System.out.println("Desktop(" + getID() + ") - moveSelectedVertices(" + x + ", "
				+ y + ")");

		Iterator<VertexComponentInterface> iterator = selectedVertices.iterator();
		VertexComponentInterface vertex = selectedVertices.get(0);
		int minX = vertex.getX() + x, minY = vertex.getY() + y, currX, currY, pX = 0, pY = 0;
		int maxX = minX, maxY = minY;
		while (iterator.hasNext()) {
			vertex = iterator.next();
			currX = vertex.getX() + x;
			currY = vertex.getY() + y;
			minX = setMin(minX, currX);
			minY = setMin(minY, currY);
			maxX = setMax(maxX, currX);
			maxY = setMax(maxY, currY);
		}
		final int bSize = VertexComponent.getBoundsSize();
		if (maxX > parent.getPreferredSize().width - bSize
				|| maxY > parent.getPreferredSize().height - bSize) {
			if (maxX > parent.getPreferredSize().width - bSize) {
				pX = maxX - parent.getPreferredSize().width + bSize;
			}
			if (maxY > parent.getPreferredSize().height - bSize) {
				pY = maxY - parent.getPreferredSize().height + bSize;
			}
			System.out.println(pX + " " + pY);
			parent.setPreferredSize(new Dimension(parent.getPreferredSize().width + pX,
					parent.getPreferredSize().height + pY));
			parent.revalidate();
			System.out.println(parent.getPreferredSize());
		}
		pX = pY = 0;
		if (minX >= 0 && minY >= 0) {
			iterator = selectedVertices.iterator();
			while (iterator.hasNext()) {
				iterator.next().moveVertex(x, y);
			}
		} else {
			if (minX < 0) {
				pX = minX;
			}
			if (minY < 0) {
				pY = minY;
			}
			System.out.println("Desktop(" + getID() + ") - moveParent(" + pX + ", " + pY
					+ ")");
			parent.setBounds(parent.getX() + pX, parent.getY() + pY, parent.getWidth()
					+ Math.abs(pX), parent.getHeight() + Math.abs(pY));
			int oldWidth = parent.getPreferredSize().width;
			int oldHeight = parent.getPreferredSize().height;
			if (oldHeight < parent.getHeight() + Math.abs(pY)) {
				oldHeight = parent.getHeight() + Math.abs(pY);
			}
			if (oldWidth < parent.getWidth() + Math.abs(pX)) {
				oldWidth = parent.getWidth() + Math.abs(pX);
			}
			parent.setPreferredSize(new Dimension(oldWidth, oldHeight));
			parent.revalidate();
			moveNotSelectedVertices(Math.abs(pX), Math.abs(pY));
		}
	}

	@Override
	public void notifyObservers() {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update();
		}
	}

	@Override
	public void notifyObservers(final VertexComponentInterface vertex) {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update(vertex);
		}

	}

	@Override
	public void notifyObserversDeleteEdge(final EdgeComponentInterface edge) {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().deleteEdge(edge);
		}

	}

	@Override
	public void notifyObserversDeleteVertex(final VertexComponentInterface vertex) {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().deleteVertex(vertex);
		}

	}

	@Override
	public void notifyObserversLightDeleteEdge(final EdgeComponentInterface edge) {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().lightDeleteEdge(edge);
		}

	}

	@Override
	public void notifyObserversLoadVertex(final String ID, final int x, final int y) {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().loadVertex(ID, x, y);
		}

	}

	@Override
	public void notifyObserversNewEdge(final EdgeComponentInterface edge) {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().newEdge(edge);
		}

	}

	@Override
	public void notifyObserversNewVertex(final int x, final int y) {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().newVertex(x, y);
		}

	}

	@Override
	public void openDialogNewID() {

		if (isOneSelectedVertex() || isOneSelectedEdge()) {
			final DialogNewID dialog = new DialogNewID(this);
			dialog.setModal(true);
			dialog.setVisible(true);
			if (dialog.isOK()) {
				if (isOneSelectedVertex()) {
					final VertexComponentInterface vertex = selectedVertices.get(0);
					saved = false;
					vertex.setID(dialog.getText());
				}
				if (isOneSelectedEdge()) {
					final EdgeComponentInterface edge = selectedEdges.get(0);
					int res = 0;
					if ((res = isDigit(dialog.getText())) != -1) {
						edge.setWeight(res);
						saved = false;
						System.out.println("res = " + res);
					}
				}
			}
		}

	}

	@Override
	public void paintEdge(final EdgeComponentInterface edge) {

		saved = false;
		System.out.println("Desktop(" + getID() + ") - paintEdge()");
		currentPaintEdge = edge;
		add((EdgeComponent) edge);
		setLayer((EdgeComponent) edge, Desktop.EDGE_LAYER);

	}

	@Override
	public void registerObserver(final DesktopObserver observer) {

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
	public void removeObserver(final DesktopObserver observer) {

		observers.remove(observer);
	}

	@Override
	public void removeObservers() {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().devastation();
			iterator.remove();
		}
	}

	@Override
	public void removeVertex(final VertexComponentInterface vertex) {

		final Rectangle rec = ((VertexComponent) vertex).getBounds();
		this.remove((VertexComponent) vertex);
		this.repaint(rec);
	}

	@Override
	public void save() {

		saved = true;
	}

	@Override
	public void setEditMode(final int editMode) {

		System.out.println("Desktop(" + getID() + ") - setEditMode(" + editMode + ")");
		this.editMode = editMode;
	}

	private int setMax(int max, final int curr) {

		if (curr > max) {
			max = curr;
		}
		return max;
	}

	private int setMin(int min, final int curr) {

		if (curr < min) {
			min = curr;
		}
		return min;
	}

	@Override
	public void setState(final Graph graph) {

		saved = true;
		final Map<String, Vertex> loadVertices = graph.getVertices();
		for (final Vertex vertex : loadVertices.values()) {
			notifyObserversLoadVertex(vertex.getID(), vertex.getX(), vertex.getY());
		}

		final List<Edge> loadEdges = graph.getEdges();
		final Iterator<Edge> iterator = loadEdges.iterator();
		while (iterator.hasNext()) {
			final Edge edge = iterator.next();
			final VertexComponentInterface vertex1 = findVertexByID(edge.getFirstID());
			final VertexComponentInterface vertex2 = findVertexByID(edge.getSecondID());
			if (vertex1 != null && vertex2 != null) {
				final EdgeComponentInterface newEdge = new EdgeComponent(vertex1);
				vertex1.registerEdge(newEdge);
				vertex2.registerEdge(newEdge);
				newEdge.setSecondVertex(vertex2);
				add((EdgeComponent) newEdge);
				setLayer((EdgeComponent) newEdge, Desktop.EDGE_LAYER);

				notifyObserversNewEdge(newEdge);
			}
		}

	}

	@Override
	public void setTempVertexPaintEdge(final int x, final int y) {

		saved = false;
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
	public void unselectEdge(final EdgeComponentInterface edge) {

		System.out.println("Desktop(" + getID() + ") - unselectEdge()");
		if (selectedEdges.contains(edge)) {
			selectedEdges.remove(edge);
		}

	}

	@Override
	public void unselectVertex(final VertexComponentInterface vertex) {

		System.out.println("Desktop(" + getID() + ") - unselectVertex()");
		if (selectedVertices.contains(vertex)) {
			selectedVertices.remove(vertex);
		}

	}

}
