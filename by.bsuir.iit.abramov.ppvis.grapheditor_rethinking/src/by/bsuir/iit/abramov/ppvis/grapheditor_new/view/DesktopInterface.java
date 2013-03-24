package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.GraphObserverInterface;

public interface DesktopInterface {
	public void registerObserver(GraphObserverInterface observer);
	 public void removeObserver(GraphObserverInterface observer);
	 public void notifyObservers();
}
