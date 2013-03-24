package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopObserverInterface;

public class Graph implements GraphInterface {
	private final int ID;
	private Map<String, Vertex> vertices;
	private List<Edge> edges;
	private List<DesktopObserverInterface> observers;
	private int maxIntID = 99999999;

	public Graph(int ID) {
		super();
		this.ID = ID;
		initialize();
	}

	private void initialize() {
		this.observers = new ArrayList<DesktopObserverInterface>();
		this.vertices = new HashMap<String, Vertex>();
		this.edges = new ArrayList<Edge>();
	}

	public int getID() {
		return this.ID;
	}

	public boolean checkID(String id) {
		return vertices.containsKey(id);
	}

	@Override
	public String addVertex(Point coordinates) {
		String ID = generateVertexID();
		Vertex vertex = new Vertex(ID, coordinates);
		this.vertices.put(ID, vertex);
		return ID;
	}
	
	private String generateVertexID() {
		Random random = new Random();
		int num = random.nextInt(this.maxIntID);
		while (this.vertices.containsKey(Integer.toString(num = random.nextInt(this.maxIntID)))) {
		}
		return Integer.toString(num);
	}

	@Override
	public boolean addEdge(String firstID, String secondID) {
		if (!checkID(firstID) || !checkID(secondID))
			return false;
		this.edges.add(new Edge(firstID, secondID));
		return true;
	}

	@Override
	public void deleteVertex(String ID) {
		if (this.vertices.containsKey(ID))
			this.vertices.remove(ID);
	}

	@Override
	public void deleteVertex(Vertex vertex) {
		deleteVertex(vertex.getID());
	}

	@Override
	public void deleteEdge(Vertex beginVertex, Vertex endVertex) {
		deleteEdge(beginVertex.getID(), endVertex.getID());
	}

	@Override
	public void deleteEdge(String firstID, String secondID) {
		if (!checkID(firstID) || !checkID(secondID))
			return;
		int index = findEdge(firstID, secondID);
		if (index != -1)
			edges.remove(index);
	}

	private int findEdge(String firstID, String secondID) {
		for (int i = 0; i < this.edges.size(); ++i)
			if (isCorrectEdge(firstID, secondID, i))
				return i;
		return -1;
	}

	private boolean isCorrectEdge(String firstID, String secondID, int index) {
		String first = this.edges.get(index).getFirstID();
		String second = this.edges.get(index).getSecondID();
		if ((firstID == first && secondID == second)
				|| (firstID == second && secondID == first))
			return true;
		return false;
	}

	@Override
	public Map<String, Vertex> getVertices() {
		return this.vertices;
	}

	@Override
	public List<Edge> getEdges() {
		return this.edges;
	}

	@Override
	public void registerObserver(DesktopObserverInterface observer) {
		observers.add(observer);
		observer.setGraph(this);

	}

	@Override
	public void removeObserver(DesktopObserverInterface observer) {
		observers.remove(observer);

	}

	@Override
	public void notifyObservers() {
		Iterator<DesktopObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update();
		}

	}
}
