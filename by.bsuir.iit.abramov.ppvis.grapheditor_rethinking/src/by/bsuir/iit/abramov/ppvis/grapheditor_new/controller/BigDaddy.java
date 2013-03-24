package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.ModelInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;

public class BigDaddy implements DaddyInterface {
	private ModelInterface model;
	private List<DesktopObserverInterface> graphs;

	public BigDaddy() {
		graphs = new ArrayList<DesktopObserverInterface>();
	}

	@Override
	public void modelUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public ModelInterface getModel() {
		return this.model;
	}

	@Override
	public void setModel(ModelInterface model) {
		this.model = model;

	}

	@Override
	public void newTab(DesktopInterface desktop) {
		DesktopObserverInterface desktopObserver = new DesktopObserver();
		(model.newGraph(desktop.getID())).registerObserver(desktopObserver);
		desktop.registerObserver(desktopObserver);
		graphs.add(desktopObserver);
	}

}
