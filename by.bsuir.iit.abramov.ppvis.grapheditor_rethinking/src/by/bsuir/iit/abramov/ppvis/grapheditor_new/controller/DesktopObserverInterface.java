package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.GraphInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.EdgeComponentInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public interface DesktopObserverInterface {
	public void update(List<VertexComponentInterface> vertices, List<VertexComponentInterface> selectedVertices, List<EdgeComponentInterface> edges, List<EdgeComponentInterface> selectedEdges);
	public void update(List<VertexComponentInterface> vertices, List<EdgeComponentInterface> edges);
	public void update(VertexComponentInterface vertex);
	public void update(EdgeComponentInterface edge);
	public void update();
	public void newVertex(int x, int y);
	
	public void setGraph(GraphInterface graph);
	public void setDesktop(DesktopInterface desktop);
}
