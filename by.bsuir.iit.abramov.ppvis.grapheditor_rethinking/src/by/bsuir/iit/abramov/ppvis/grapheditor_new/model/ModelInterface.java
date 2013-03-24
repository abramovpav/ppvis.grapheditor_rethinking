package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.util.ArrayList;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;

public interface ModelInterface {	
	public Graph newGraph(int ID);	
		
	public void registerObserver(DaddyInterface daddy);
	public void removeObserver(DaddyInterface daddy);
	public void notifyDaddy();
}
