package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.ModelInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;

public class BigDaddy implements DaddyInterface {
	private ModelInterface							model;
	private final List<DesktopObserverInterface>	graphs;

	public BigDaddy() {

		graphs = new ArrayList<DesktopObserverInterface>();
	}

	@Override
	public void doAlgorithm(final int index) {

		model.doAlgorithm(index);

	}

	@Override
	public ModelInterface getModel() {

		return model;
	}

	@Override
	public void modelUpdate() {

		// TODO Auto-generated method stub

	}

	@Override
	public void newTab(final DesktopInterface desktop) {

		final DesktopObserverInterface desktopObserver = new DesktopObserver();
		(model.newGraph(desktop.getID())).registerObserver(desktopObserver);
		desktop.registerObserver(desktopObserver);
		graphs.add(desktopObserver);
	}

	@Override
	public void setModel(final ModelInterface model) {

		this.model = model;

	}

}
