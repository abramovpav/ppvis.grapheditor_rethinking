package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.Color;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Vertex;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public class VertexObserver {
	private Vertex						vertex;
	private VertexComponentInterface	vertexComponent;

	public void delVertex() {

		vertex = null;

	}

	public void delVertexComponent() {

		vertexComponent = null;

	}

	public void newLocation(final int x, final int y) {

		vertex.setLocation(x, y);

	}

	public void select(final Color color) {

		vertexComponent.select(color);
	}

	public void setVertex(final Vertex vertex) {

		this.vertex = vertex;

	}

	public void setVertex(final VertexComponentInterface vertexComponent) {

		this.vertexComponent = vertexComponent;

	}

	public void unselect() {

		vertexComponent.unselect();
	}

	public void update(final String ID) {

		vertex.setID(ID);

	}

}
