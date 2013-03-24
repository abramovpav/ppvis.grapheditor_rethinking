package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.GraphObserverInterface;


public class Model implements ModelInterface {	
	private Graph graph;
	private List<GraphObserverInterface> observers;
	private List<DaddyInterface> daddies;
	
	public Model() {  
		super();
		graph = new Graph(0);
		observers = new ArrayList<GraphObserverInterface>();
		daddies = new ArrayList<DaddyInterface>();
	}

	@Override
	public void registerObserver(GraphObserverInterface observer) {
		observers.add(observer);
		
	}

	@Override
	public void removeObserver(GraphObserverInterface observer) {
		observers.remove(observer);
		
	}

	@Override
	public void notifyObservers() {
		Iterator<GraphObserverInterface> iterator = observers.iterator();
		while(iterator.hasNext()) {
			iterator.next().modelUpdate();
		}
		
	}

	@Override
	public void registerObserver(DaddyInterface daddy) {
		daddies.add(daddy);
		daddy.setModel(this);
	}

	@Override
	public void removeObserver(DaddyInterface daddy) {
		daddies.remove(daddy);
		
	}

	@Override
	public void notifyDaddy() {
		Iterator<DaddyInterface> iterator = daddies.iterator();
		while(iterator.hasNext()) {
			iterator.next().modelUpdate();
		}
		
	}
}
