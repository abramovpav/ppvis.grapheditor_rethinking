package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.ModelInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;

public interface GraphObserverInterface {
	public void modelUpdate();
	public void desktopUpdate();
	public void setModel(ModelInterface model);
	public void setDesktop(DesktopInterface desktop);
}
