package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.VertexObserver;

public class Vertex implements Serializable {
	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1L;
	private transient List<VertexObserver>	observers;
	private Point							coordinates;
	private String							ID;
	private final Graph						graph;

	public Vertex(final Graph graph, final String iD, final int x, final int y) {

		this.graph = graph;
		coordinates = new Point(x, y);
		ID = iD;
		initialize();
	}

	public Vertex(final Graph graph, final String iD, final Point coordinates) {

		this.graph = graph;
		this.coordinates = coordinates;
		ID = iD;
		initialize();
	}

	public Point getCoordinates() {

		return coordinates;
	}

	public String getID() {

		return ID;
	}

	public final int getX() {

		return coordinates.x;
	}

	public final int getY() {

		return coordinates.y;
	}

	private void initialize() {

		observers = new ArrayList<VertexObserver>();
	}

	public void registerObserver(final VertexObserver observer) {

		observers.add(observer);
		observer.setVertex(this);

	}

	public void removeObserver(final VertexObserver observer) {

		observers.remove(observer);

	}

	public void select(final Color color) {

		final Iterator<VertexObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().select(color);
		}
	}

	public void setCoordinates(final int x, final int y) {

		coordinates = new Point(x, y);
	}

	public void setCoordinates(final Point coordinates) {

		this.coordinates = coordinates;
	}

	public void setID(final String ID) {

		graph.newVertexID(this.ID, ID);
		this.ID = ID;
	}

	public void setLocation(final int x, final int y) {

		coordinates.x = x;
		coordinates.y = y;

	}

	public void unselect() {

		final Iterator<VertexObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().unselect();
		}
	}

}
