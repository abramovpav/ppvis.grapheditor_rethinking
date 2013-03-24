package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.util.ArrayList;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.GraphObserverInterface;

public interface ModelInterface {	
	 public void registerObserver(GraphObserverInterface observer);
	 public void registerObserver(DaddyInterface daddy);
	 public void removeObserver(GraphObserverInterface observer);
	 public void removeObserver(DaddyInterface daddy);
	 public void notifyObservers();
	 public void notifyDaddy();
}
