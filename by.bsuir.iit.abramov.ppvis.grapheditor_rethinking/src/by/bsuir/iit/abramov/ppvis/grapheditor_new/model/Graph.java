package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.GraphObserver;

public class Graph implements GraphInterface, ObservableGraph {
	private List observers;
	private final String ID;
	private List<String> vertexIDs;
	private List<Vertex> vertices;
	private List<Edge> edges;
	
	public Graph(String ID) {
		super();
		this.ID = ID;
		initialize();
	}
	
	public Graph(int ID) {
		super();
		this.ID = Integer.toString(ID);
		initialize();
	}
	
	private void initialize() {
		this.observers = new ArrayList();
		this.vertexIDs = new ArrayList<String>();
		this.vertices = new ArrayList<Vertex>();
		this.edges = new ArrayList<Edge>();
	}
	
	public boolean checkID(String id) {
		return isContained(id);
	}
	
	private boolean isContained(String id) {
		Iterator iterator = this.vertexIDs.iterator();
		while(iterator.hasNext()) {
			if (id == (String)iterator.next())
				return true;			
		}
		return false;
	}
	
	@Override
	public boolean addVertex(String ID, Point coordinates) {
		if (checkID(ID))
			return false;
		this.vertexIDs.add(ID);
		this.vertices.add(new Vertex(ID, coordinates));
		return true;
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
		int index = 0;
		index = findVertex(ID);
		if (index != -1)
			this.vertices.remove(index);
		index = findID(ID);
		if (index != -1)
			this.vertexIDs.remove(index);
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
	
	private int findVertex(String ID) {
		for (int i = 0; i < this.vertices.size(); ++i)
			if (ID == this.vertices.get(i).getID())
				return i;
		return -1;
	}
	
	private int findEdge(String firstID, String secondID) {
		for (int i = 0; i < this.edges.size(); ++i)
			if (isCorrectEdge(firstID, secondID, i))
				return i;
		return -1;
	}
	
	private boolean isCorrectEdge(String firstID, String secondID, int index)
	{
		String first = this.edges.get(index).getFirstID();
		String second = this.edges.get(index).getSecondID();
		if ((firstID == first && secondID == second) || (firstID == second && secondID == first))
			return true;
		return false;
	}
	
	private int findID(String ID) {
		for (int i = 0; i < this.vertices.size(); ++i)
			if (ID == this.vertexIDs.get(i))
				return i;
		return -1;
	}
	@Override
	public List<Vertex> getVertices() {
		return this.vertices;
	}
	
	@Override
	public List<Edge> getEdges() {
		return this.edges;
	}
	
	@Override
	public void registerObserver(GraphObserver observer) {
		this.observers.add(observer);
	}

	@Override
	public void removeObserver(GraphObserver observer) {
		this.observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		Iterator iterator = observers.iterator();
		while(iterator.hasNext()) {
			GraphObserver observer = (GraphObserver)iterator.next();
			observer.update(this.vertices, this.edges);
		}
	}
}