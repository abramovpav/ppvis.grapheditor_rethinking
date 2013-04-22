package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopObserver;

class DoublePoint {
	public double	x;
	public double	y;
}

public class Graph implements GraphInterface, Serializable {

	private static final long				serialVersionUID	= 1L;
	private final int						ID;

	private Map<String, Vertex>				vertices;
	private List<Edge>						edges;
	private transient List<DesktopObserver>	observers;

	private final int						maxIntID			= 99999;

	static final double						EPS					= 1e-9;

	public Graph(final int ID) {

		super();
		this.ID = ID;
		initialize();
	}

	@Override
	public boolean addEdge(final int weight, final String firstID, final String secondID) {

		if (!checkID(firstID) || !checkID(secondID)) {
			return false;
		}
		edges.add(new Edge(weight, firstID, secondID));
		return true;
	}

	@Override
	public Vertex addVertex(final Point coordinates) {

		final String ID = generateVertexID();
		final Vertex vertex = new Vertex(this, ID, coordinates);
		vertices.put(ID, vertex);
		return vertex;
	}

	@Override
	public Vertex addVertex(final String ID, final Point coordinates) {

		String tmpID = ID;
		if (checkID(tmpID)) {
			tmpID = generateVertexID();
		}
		final Vertex vertex = new Vertex(this, tmpID, coordinates);
		vertices.put(tmpID, vertex);
		return vertex;
	}

	@Override
	public void blockInterface() {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().blockInterface();
		}

	}

	@Override
	public boolean checkID(final String id) {

		return vertices.containsKey(id);
	}

	@Override
	public void deleteEdge(final String firstID, final String secondID) {

		if (!checkID(firstID) || !checkID(secondID)) {
			return;
		}
		final int index = findEdge(firstID, secondID);
		if (index != -1) {
			edges.remove(index);
		}
	}

	@Override
	public void deleteEdge(final Vertex beginVertex, final Vertex endVertex) {

		deleteEdge(beginVertex.getID(), endVertex.getID());
	}

	@Override
	public void deleteVertex(final String ID) {

		if (vertices.containsKey(ID)) {
			vertices.remove(ID);
		}
	}

	@Override
	public void deleteVertex(final Vertex vertex) {

		deleteVertex(vertex.getID());
	}

	@Override
	public void devastation() {

		vertices.clear();
		edges.clear();
		observers.clear();
	}

	@Override
	public void doAlgorithm() {

		final Algorithm algorithm = new Algorithm(this, vertices);
		algorithm.start();

	}

	private int findEdge(final String firstID, final String secondID) {

		for (int i = 0; i < edges.size(); ++i) {
			if (isCorrectEdge(firstID, secondID, i)) {
				return i;
			}
		}
		return -1;
	}

	private List<Integer> findEdgesOneID(final String ID) {

		final List<Integer> mas = new ArrayList<Integer>();
		for (int i = 0; i < edges.size(); ++i) {
			final Edge edge = edges.get(i);
			if (edge.getFirstID().equalsIgnoreCase(ID)
					|| edge.getSecondID().equalsIgnoreCase(ID)) {
				mas.add(i);
			}
		}

		return mas;
	}

	private String generateVertexID() {

		final Random random = new Random();
		int num = random.nextInt(maxIntID);
		while (vertices.containsKey(Integer.toString(num = random.nextInt(maxIntID)))) {
		}
		return Integer.toString(num);
	}

	@Override
	public List<Edge> getEdges() {

		return edges;
	}

	public int getID() {

		return ID;
	}

	@Override
	public Map<String, Vertex> getVertices() {

		return vertices;
	}

	private void initialize() {

		observers = new ArrayList<DesktopObserver>();
		vertices = new HashMap<String, Vertex>();
		edges = new ArrayList<Edge>();
	}

	private boolean isCorrectEdge(final String firstID, final String secondID,
			final int index) {

		final String first = edges.get(index).getFirstID();
		final String second = edges.get(index).getSecondID();
		if ((firstID.equalsIgnoreCase(first) && secondID.equalsIgnoreCase(second))
				|| (firstID.equalsIgnoreCase(second) && secondID.equalsIgnoreCase(first))) {
			return true;
		}
		return false;
	}

	@Override
	public void newVertexID(final String oldID, final String newID) {

		if (vertices.containsKey(oldID)) {
			final Vertex vertex = vertices.get(oldID);
			vertices.remove(oldID);
			vertices.put(newID, vertex);
		}
	}

	@Override
	public void newWeight(final String firstID, final String secondID, final int weight) {

		final int index = findEdge(firstID, secondID);
		if (index == -1) {
			showError("in newWeight(" + firstID + ", " + secondID + ", " + weight
					+ ") edge not found");
			return;
		}
		final Edge edge = edges.get(index);
		edge.setWeight(weight);

	}

	@Override
	public void registerObserver(final DesktopObserver observer) {

		observers.add(observer);
		observer.setGraph(this);

	}

	@Override
	public void removeObserver(final DesktopObserver observer) {

		observers.remove(observer);
	}

	@Override
	public void selectEdge(final String firstID, final String secondID) {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().selectEdge(firstID, secondID);
		}
	}

	public void showError(final String text) {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().showError(text);
		}
	}

	@Override
	public void unlockInterface() {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().unlockInterface();
		}

	}

	@Override
	public void unselectEdge(final String firstID, final String secondID) {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().unselectEdge(firstID, secondID);
		}
	}

	@Override
	public void vertexNewID(final String oldID, final String newID) {

		final List<Integer> indexes = findEdgesOneID(oldID);
		for (final int index : indexes) {
			final Edge edge = edges.get(index);
			if (edge.getFirstID().equalsIgnoreCase(oldID)) {
				edge.setFirstID(newID);
			}
			if (edge.getSecondID().equalsIgnoreCase(oldID)) {
				edge.setSecondID(newID);
			}
		}
	}
}

class IntersectionPoint {
	private final double		x;
	private final double		y;
	private final LineFullForm	line;

	public IntersectionPoint(final LineFullForm line, final double x, final double y) {

		this.line = line;
		this.x = x;
		this.y = y;
	}

	public LineFullForm getLine() {

		return line;
	}

	public double getX() {

		return x;
	}

	public double getY() {

		return y;
	}
}

class Line {
	private final boolean	mode;
	private final double	k1;
	private final double	k2;
	private final double	b1;
	private final double	b2;
	public double			y;
	public double			x;
	public Edge				edge;

	public Line(final double y, final double x, final Edge edge, final boolean mode,
			final double k1, final double b1, final double k2, final double b2) {

		this.y = y;
		this.x = x;
		this.edge = edge;
		this.mode = mode;
		this.k1 = k1;
		this.b1 = b1;
		this.k2 = k2;
		this.b2 = b2;
	}

	public double getB1() {

		return b1;
	}

	public double getB2() {

		return b2;
	}

	public double getK1() {

		return k1;
	}

	public double getK2() {

		return k2;
	}

	public double getValue(final double x) {

		double y = 0;
		if (!mode) {
			y = k1 * x + b1;
		} else {
			if (x < this.x) {
				y = k1 * x + b1;
			} else {
				y = k2 * x + b2;;
			}
		}
		return y;
	}

	public boolean isMode() {

		return mode;
	}
}

class LineFullForm {
	public final double	a;
	public final double	b;
	public final double	c;
	public final double	right;
	public final double	left;
	public final int	indexInListOfLine;
	public final Edge	edge;

	public LineFullForm(final double a, final double b, final double c,
			final double left, final double right, final int index, final Edge edge) {

		this.a = a;
		this.b = b;
		this.c = c;
		this.left = left;
		this.right = right;
		indexInListOfLine = index;
		this.edge = edge;
	}
}
