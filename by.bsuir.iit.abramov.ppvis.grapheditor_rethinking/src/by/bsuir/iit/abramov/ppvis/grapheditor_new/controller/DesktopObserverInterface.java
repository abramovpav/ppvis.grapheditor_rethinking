package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.EdgeComponent;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponent;

public interface DesktopObserverInterface {
	public void update(List<VertexComponent> vertices, List<VertexComponent> selectedVertices, List<EdgeComponent> edges, List<EdgeComponent> selectedEdges);
	public void update(List<VertexComponent> vertices, List<EdgeComponent> edges);
	public void update(VertexComponent vertex);
	public void update(EdgeComponent edge);
	public void update();
}
