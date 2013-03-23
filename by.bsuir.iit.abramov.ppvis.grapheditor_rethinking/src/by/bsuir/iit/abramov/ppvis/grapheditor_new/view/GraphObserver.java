package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Edge;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Vertex;

public interface GraphObserver {
	public void update(List<Vertex> vertices, List<Edge> lines);
}
