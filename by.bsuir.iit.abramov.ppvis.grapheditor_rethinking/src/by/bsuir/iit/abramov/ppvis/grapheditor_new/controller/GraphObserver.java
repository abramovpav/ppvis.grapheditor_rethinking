package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.ModelInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;

public class GraphObserver implements GraphObserverInterface {
	private ModelInterface model;
	private DesktopInterface desktop;
	
	@Override
	public void modelUpdate() {
		
		
	}

	@Override
	public void desktopUpdate() {
		
		
	}

	@Override
	public void setModel(ModelInterface model) {
		this.model = model;	
	}

	@Override
	public void setDesktop(DesktopInterface desktop) {
		this.desktop = desktop;
	}
	
}
