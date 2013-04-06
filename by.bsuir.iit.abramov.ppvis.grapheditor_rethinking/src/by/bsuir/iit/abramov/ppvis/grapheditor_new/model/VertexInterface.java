package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.VertexObserverInterface;

public interface VertexInterface {
	public String getID();

	public void notifyObservers();

	public void registerObserver(VertexObserverInterface observer);

	public void removeObserver(VertexObserverInterface observer);

	public void setLocation(int x, int y);
}
