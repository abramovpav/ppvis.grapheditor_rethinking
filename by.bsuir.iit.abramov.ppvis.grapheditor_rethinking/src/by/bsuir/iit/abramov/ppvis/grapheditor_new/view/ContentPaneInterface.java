package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.util.Iterator;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;

public interface ContentPaneInterface {
	public void registerObserver(DaddyInterface daddy);
	public void removeObserver(DaddyInterface daddy);
	public void notifyDaddy();
	public void newTab(DesktopInterface desktop);
}
