package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.awt.Point;
import java.util.List;

public interface GraphInterface {
	public boolean addVertex(String ID, Point coordinates);
	public boolean addEdge(String beginID, String endID);
	public void deleteVertex(String ID);
	public void deleteVertex(Vertex vertex);
	public void deleteEdge(Vertex beginVertex, Vertex endVertex);
	public void deleteEdge(String beginID, String endID);
	public List<Vertex> getVertices();
	public List<Edge> getEdges();
}
