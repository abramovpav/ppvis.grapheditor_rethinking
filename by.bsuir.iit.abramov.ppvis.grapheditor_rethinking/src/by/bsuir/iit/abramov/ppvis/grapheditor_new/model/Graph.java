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

public class Graph implements GraphInterface, Serializable {
	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1L;
	private final int						ID;
	private Map<String, Vertex>				vertices;
	private List<Edge>						edges;
	private transient List<DesktopObserver>	observers;
	private final int						maxIntID			= 99999;
	private static final int				maxPath				= 99999999;

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
		final Vertex vertex = new Vertex(ID, coordinates);
		vertices.put(ID, vertex);
		return vertex;
	}

	@Override
	public Vertex addVertex(final String ID, final Point coordinates) {

		String tmpID = ID;
		if (checkID(tmpID)) {
			tmpID = generateVertexID();
		}
		final Vertex vertex = new Vertex(tmpID, coordinates);
		vertices.put(tmpID, vertex);
		return vertex;
	}

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

		final Map<String, Integer> mapIndexes = new HashMap<String, Integer>();
		int i = 0;
		for (final String id : vertices.keySet()) {
			mapIndexes.put(id, i);
			i++;
		}
		generateMatrix(mapIndexes);

	}

	private int findEdge(final String firstID, final String secondID) {

		for (int i = 0; i < edges.size(); ++i) {
			if (isCorrectEdge(firstID, secondID, i)) {
				return i;
			}
		}
		return -1;
	}

	private int findEdgeOneID(final String ID) {

		for (int i = 0; i < edges.size(); ++i) {
			final Edge edge = edges.get(i);
			if (edge.getFirstID() == ID || edge.getSecondID() == ID) {
				return i;
			}
		}

		return -1;
	}

	private int[][] generateMatrix(final Map<String, Integer> indexes) {

		final int[][] matrix = new int[indexes.size()][indexes.size()];
		for (int i = 0; i < indexes.size(); ++i) {
			for (int j = 0; j < indexes.size(); ++j) {
				if (i == j) {
					matrix[i][j] = Graph.maxPath;
				}
			}
		}
		final Iterator<Edge> iterator = edges.iterator();
		while (iterator.hasNext()) {
			final Edge edge = iterator.next();
			if (indexes.containsKey(edge.getFirstID())
					&& indexes.containsKey(edge.getSecondID())) {
				matrix[indexes.get(edge.getFirstID())][indexes.get(edge.getSecondID())] = edge
						.getWeight();
				matrix[indexes.get(edge.getSecondID())][indexes.get(edge.getFirstID())] = edge
						.getWeight();
			}
		}
		return matrix;
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
		if ((firstID == first && secondID == second)
				|| (firstID == second && secondID == first)) {
			return true;
		}
		return false;
	}

	@Override
	public void newWeight(final String firstID, final String secondID, final int weight) {

		final int index = findEdge(firstID, secondID);
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
	public void vertexNewID(final String oldID, final String newID) {

		final int index = findEdgeOneID(oldID);
		if (index != -1) {
			final Edge edge = edges.get(index);
			if (edge.getFirstID() == oldID) {
				edge.setFirstID(newID);
			}
			if (edge.getSecondID() == oldID) {
				edge.setSecondID(newID);
			}
		}
	}
}
