package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.GraphInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Vertex;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.EdgeComponent;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.EdgeComponentInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponent;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public class DesktopObserver {
	private GraphInterface		graph;
	private DesktopInterface	desktop;

	public void blockInterface() {

		desktop.blockInterface();
	}

	public void deleteEdge(final EdgeComponentInterface edge) {

		lightDeleteEdge(edge);
		edge.deleteFromVertices();
	}

	public void deleteVertex(final VertexComponentInterface vertex) {

		vertex.deleteEdges();

		for (final MouseListener listener : ((VertexComponent) vertex)
				.getMouseListeners()) {
			((VertexComponent) vertex).removeMouseListener(listener);
		}
		for (final MouseMotionListener listener : ((VertexComponent) vertex)
				.getMouseMotionListeners()) {
			((VertexComponent) vertex).removeMouseMotionListener(listener);
		}

		vertex.removeObservers();
		graph.deleteVertex(vertex.getID());

	}

	public void devastation() {

		graph.devastation();
		graph = null;
		desktop = null;
	}

	public void lightDeleteEdge(final EdgeComponentInterface edge) {

		graph.deleteEdge(edge.getFirstVertex().getID(), edge.getSecondVertex().getID());
		for (final MouseListener listener : ((EdgeComponent) edge).getMouseListeners()) {
			((EdgeComponent) edge).removeMouseListener(listener);
		}
		for (final MouseMotionListener listener : ((EdgeComponent) edge)
				.getMouseMotionListeners()) {
			((EdgeComponent) edge).removeMouseMotionListener(listener);
		}

	}

	public void loadVertex(final String ID, final int x, final int y) {

		final Vertex vertex = graph.addVertex(ID, new Point(x, y));
		final VertexComponentInterface vertexComponent = desktop.addVertex(
				vertex.getID(), x, y);
		final VertexObserver observer = new VertexObserver();
		vertex.registerObserver(observer);
		vertexComponent.registerObserver(observer);

	}

	public void newEdge(final EdgeComponentInterface edge) {

		graph.addEdge(edge.getWeight(), edge.getFirstVertex().getID(), edge
				.getSecondVertex().getID());
		((EdgeComponent) edge).addMouseListener(new EdgeListener());
	}

	public void newVertex(final int x, final int y) {

		final Vertex vertex = graph.addVertex(new Point(x, y));
		final VertexComponentInterface vertexComponent = desktop.addVertex(
				vertex.getID(), x, y);
		final VertexObserver observer = new VertexObserver();
		vertex.registerObserver(observer);
		vertexComponent.registerObserver(observer);
	}

	public void newWeight(final EdgeComponentInterface edge) {

		graph.newWeight(edge.getFirstVertex().getID(), edge.getSecondVertex().getID(),
				edge.getWeight());
	}

	public void selectEdge(final String firstID, final String secondID) {

		desktop.selectEdge(firstID, secondID);
	}

	public void setDesktop(final DesktopInterface desktop) {

		this.desktop = desktop;

	}

	public void setGraph(final GraphInterface graph) {

		this.graph = graph;

	}

	public void showError(final String text) {

		desktop.showError(text);
	}

	public void unlockInterface() {

		desktop.unlockInterface();
	}

	public void unselectEdge(final String firstID, final String secondID) {

		desktop.unselectEdge(firstID, secondID);
	}

	public void vertexNewID(final VertexComponentInterface vertex, final String newID) {

		if (!graph.checkID(newID)) {
			graph.vertexNewID(vertex.getID(), newID);
			vertex.setID(newID);
		} else {
			showError(newID + " already exist");
		}
	}

}
