package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;

public class Model implements ModelInterface {
	private final List<Graph>			graphs;
	private final List<DaddyInterface>	daddies;

	public Model() {

		super();
		graphs = new ArrayList<Graph>();
		daddies = new ArrayList<DaddyInterface>();
	}

	@Override
	public void doAlgorithm(final int index) {

		final Iterator<Graph> iterator = graphs.iterator();
		while (iterator.hasNext()) {
			final Graph graph = iterator.next();
			if (graph.getID() == index) {
				graph.doAlgorithm();
			}
		}
	}

	@Override
	public Graph newGraph(final int ID) {

		final Graph graph = new Graph(ID);
		graphs.add(graph);
		return graph;

	}

	@Override
	public void notifyDaddy() {

		final Iterator<DaddyInterface> iterator = daddies.iterator();
		while (iterator.hasNext()) {
			iterator.next().modelUpdate();
		}

	}

	@Override
	public void registerObserver(final DaddyInterface daddy) {

		daddies.add(daddy);
		daddy.setModel(this);
	}

	@Override
	public void removeObserver(final DaddyInterface daddy) {

		daddies.remove(daddy);

	}
}
