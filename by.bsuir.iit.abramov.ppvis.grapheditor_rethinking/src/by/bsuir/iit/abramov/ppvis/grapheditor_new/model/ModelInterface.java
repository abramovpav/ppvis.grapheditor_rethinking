package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;

public interface ModelInterface {
	public void doAlgorithm(int index);

	public Graph newGraph(int ID);

	public void notifyDaddy();

	public void registerObserver(DaddyInterface daddy);

	public void removeObserver(DaddyInterface daddy);
}
