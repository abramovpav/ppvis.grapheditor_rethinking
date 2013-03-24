package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.VertexInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public interface VertexObserverInterface {
	public void setVertex(VertexInterface vertex);
	public void setVertex(VertexComponentInterface vertexComponent);
	
}
