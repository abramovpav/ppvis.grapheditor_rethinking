package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.VertexObserverInterface;

public class Vertex implements VertexInterface {
	private List<VertexObserverInterface>	observers;
	private Point							coordinates;
	private final String					ID;
	private boolean							selected;

	public Vertex(final String iD, final int x, final int y) {

		coordinates = new Point(x, y);
		ID = iD;
		initialize();
	}

	public Vertex(final String iD, final Point coordinates) {

		this.coordinates = coordinates;
		ID = iD;
		initialize();
	}

	public Point getCoordinates() {

		return coordinates;
	}

	@Override
	public String getID() {

		return ID;
	}

	private void initialize() {

		selected = false;
		observers = new ArrayList<VertexObserverInterface>();
	}

	public boolean isSelected() {

		return selected;
	}

	@Override
	public void notifyObservers() {

		final Iterator<VertexObserverInterface> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update();
		}

	}

	@Override
	public void registerObserver(final VertexObserverInterface observer) {

		observers.add(observer);
		observer.setVertex(this);

	}

	@Override
	public void removeObserver(final VertexObserverInterface observer) {

		observers.remove(observer);

	}

	public void setCoordinates(final int x, final int y) {

		coordinates = new Point(x, y);
	}

	public void setCoordinates(final Point coordinates) {

		this.coordinates = coordinates;
	}

	@Override
	public void setLocation(final int x, final int y) {

		coordinates.x = x;
		coordinates.y = y;

	}

	public void setSelection(final boolean selected) {

		this.selected = selected;
	}

}
