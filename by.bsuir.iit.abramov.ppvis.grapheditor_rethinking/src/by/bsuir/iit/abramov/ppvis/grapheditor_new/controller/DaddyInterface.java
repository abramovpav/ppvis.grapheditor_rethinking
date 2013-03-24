package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.ModelInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;

public interface DaddyInterface {
	public void modelUpdate();
	public void windowUpdate();
	public void setModel(ModelInterface model);
	public ModelInterface getModel();
	public void newTab(DesktopInterface desktop);
}
