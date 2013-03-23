package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.GraphObserver;

public class Model implements ModelInterface {	
	private Graph graph;
	
	public Model() {  
		super();
		graph = new Graph(0);
	}
}
