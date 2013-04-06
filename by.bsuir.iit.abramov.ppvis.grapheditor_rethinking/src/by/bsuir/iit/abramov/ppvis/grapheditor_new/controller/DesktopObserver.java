package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.GraphInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.VertexInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.EdgeComponent;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.EdgeComponentInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponent;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public class DesktopObserver implements DesktopObserverInterface {
	private GraphInterface		graph;
	private DesktopInterface	desktop;

	@Override
	public void deleteEdge(final EdgeComponentInterface edge) {

		graph.deleteEdge(edge.getFirstVertex().getID(), edge.getSecondVertex().getID());
		for (final MouseListener listener : ((EdgeComponent) edge).getMouseListeners()) {
			((EdgeComponent) edge).removeMouseListener(listener);
		}
		for (final MouseMotionListener listener : ((EdgeComponent) edge)
				.getMouseMotionListeners()) {
			((EdgeComponent) edge).removeMouseMotionListener(listener);
		}
		edge.deleteFromVertices();
	}

	@Override
	public void deleteVertex(final VertexComponentInterface vertex) {

		graph.deleteVertex(vertex.getID());
		for (final MouseListener listener : ((VertexComponent) vertex)
				.getMouseListeners()) {
			((VertexComponent) vertex).removeMouseListener(listener);
		}
		for (final MouseMotionListener listener : ((VertexComponent) vertex)
				.getMouseMotionListeners()) {
			((VertexComponent) vertex).removeMouseMotionListener(listener);
		}

		vertex.removeObservers();
		vertex.deleteEdges();

	}

	@Override
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

	@Override
	public void newEdge(final EdgeComponentInterface edge) {

		graph.addEdge(edge.getFirstVertex().getID(), edge.getSecondVertex().getID());
		((EdgeComponent) edge).addMouseListener(new EdgeListener());
	}

	@Override
	public void newVertex(final int x, final int y) {

		final VertexInterface vertex = graph.addVertex(new Point(x, y));
		final VertexComponentInterface vertexComponent = desktop.addNode(vertex.getID(),
				x, y);
		final VertexObserverInterface observer = new VertexObserver();
		vertex.registerObserver(observer);
		vertexComponent.registerObserver(observer);
	}

	@Override
	public void setDesktop(final DesktopInterface desktop) {

		this.desktop = desktop;

	}

	@Override
	public void setGraph(final GraphInterface graph) {

		this.graph = graph;

	}

	@Override
	public void update() {

		// TODO Auto-generated method stub

	}

	@Override
	public void update(final EdgeComponentInterface edge) {

		// TODO Auto-generated method stub

	}

	@Override
	public void update(final List<VertexComponentInterface> vertices,
			final List<EdgeComponentInterface> edges) {

		// TODO Auto-generated method stub

	}

	@Override
	public void update(final List<VertexComponentInterface> vertices,
			final List<VertexComponentInterface> selectedVertices,
			final List<EdgeComponentInterface> edges,
			final List<EdgeComponentInterface> selectedEdges) {

		// TODO Auto-generated method stub

	}

	@Override
	public void update(final VertexComponentInterface vertex) {

		// graph.addVertex(vertex.getID(), vertex.getCoordinates());

	}

}
