package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopObserver;

public interface GraphInterface {
	public boolean addEdge(int weight, String beginID, String endID);

	public Vertex addVertex(Point coordinates);

	public Vertex addVertex(String ID, Point coordinates);

	public void deleteEdge(String beginID, String endID);

	public void deleteEdge(Vertex beginVertex, Vertex endVertex);

	public void deleteVertex(String ID);

	public void deleteVertex(Vertex vertex);

	public void devastation();

	public void doAlgorithm();

	public List<Edge> getEdges();
	
	public void newVertexID(String oldID, String newID);

	public Map<String, Vertex> getVertices();

	public void newWeight(String firstID, String secondID, int weight);
	public boolean checkID(final String id);

	public void registerObserver(DesktopObserver observer);

	public void removeObserver(DesktopObserver observer);

	public void vertexNewID(String oldID, String newID);
}
