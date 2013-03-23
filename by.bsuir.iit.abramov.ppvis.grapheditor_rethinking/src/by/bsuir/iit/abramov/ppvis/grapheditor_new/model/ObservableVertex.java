package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexObserver;

public interface ObservableVertex {
	public void registerObserver(VertexObserver observer);
	public void removeObserver(VertexObserver observer);
	public void notifyObservers();
}
