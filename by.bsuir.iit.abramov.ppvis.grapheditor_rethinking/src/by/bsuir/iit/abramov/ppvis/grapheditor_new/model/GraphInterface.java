package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopObserverInterface;

public interface GraphInterface {
	public String addVertex(Point coordinates);
	public boolean addEdge(String beginID, String endID);
	public void deleteVertex(String ID);
	public void deleteVertex(Vertex vertex);
	public void deleteEdge(Vertex beginVertex, Vertex endVertex);
	public void deleteEdge(String beginID, String endID);
	public Map<String, Vertex> getVertices();
	public List<Edge> getEdges();
	
	public void registerObserver(DesktopObserverInterface observer);
	public void removeObserver(DesktopObserverInterface observer);
	public void notifyObservers();
}
