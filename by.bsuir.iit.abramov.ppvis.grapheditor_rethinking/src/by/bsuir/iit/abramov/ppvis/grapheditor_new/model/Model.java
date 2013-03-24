package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;


public class Model implements ModelInterface {	
	private List<Graph> graphs;
	private List<DaddyInterface> daddies;
	
	public Model() {  
		super();
		graphs = new ArrayList<Graph>();
		daddies = new ArrayList<DaddyInterface>();
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

	@Override
	public Graph newGraph(int ID) {
		Graph graph = new Graph(ID);
		graphs.add(graph);
		return graph;
		
	}
}
