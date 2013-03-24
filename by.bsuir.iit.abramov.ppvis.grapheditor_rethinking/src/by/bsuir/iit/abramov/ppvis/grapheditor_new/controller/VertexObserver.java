package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Vertex;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.VertexInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponent;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public class VertexObserver implements VertexObserverInterface {
	private VertexInterface vertex;
	private VertexComponentInterface vertexComponent;
	@Override
	public void setVertex(VertexInterface vertex) {
		this.vertex = vertex;
		
	}
	@Override
	public void setVertex(VertexComponentInterface vertexComponent) {
		this.vertexComponent = vertexComponent;
		
	}
	
}
