package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.VertexInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public class VertexObserver implements VertexObserverInterface {
	private VertexInterface				vertex;
	private VertexComponentInterface	vertexComponent;

	@Override
	public void delVertex() {

		vertex = null;

	}

	@Override
	public void delVertexComponent() {

		vertexComponent = null;

	}

	@Override
	public void newLocation(final int x, final int y) {

		vertex.setLocation(x, y);

	}

	@Override
	public void setVertex(final VertexComponentInterface vertexComponent) {

		this.vertexComponent = vertexComponent;

	}

	@Override
	public void setVertex(final VertexInterface vertex) {

		this.vertex = vertex;

	}

	@Override
	public void update() {

		// TODO Auto-generated method stub

	}

}
