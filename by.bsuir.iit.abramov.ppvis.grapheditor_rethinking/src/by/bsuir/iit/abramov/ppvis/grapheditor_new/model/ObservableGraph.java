package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.GraphObserver;

public interface ObservableGraph {
	public void registerObserver(GraphObserver observer);
	public void removeObserver(GraphObserver observer);
	public void notifyObservers();
}
