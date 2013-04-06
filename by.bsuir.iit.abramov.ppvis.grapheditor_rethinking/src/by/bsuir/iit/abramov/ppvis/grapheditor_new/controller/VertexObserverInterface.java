package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.VertexInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public interface VertexObserverInterface {
	public void delVertex();

	public void delVertexComponent();

	public void newLocation(int x, int y);

	public void setVertex(VertexComponentInterface vertexComponent);

	public void setVertex(VertexInterface vertex);

	public void update();
}
