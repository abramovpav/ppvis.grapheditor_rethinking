package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.ModelInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;

public interface DaddyInterface {
	public void doAlgorithm(int index);

	public ModelInterface getModel();

	public void modelUpdate();

	public void newTab(DesktopInterface desktop);

	public void setModel(ModelInterface model);
}
