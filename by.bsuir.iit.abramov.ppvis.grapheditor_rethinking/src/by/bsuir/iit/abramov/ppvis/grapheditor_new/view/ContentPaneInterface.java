package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;

public interface ContentPaneInterface {
	public void deleteSelectedItems();

	public void doAlgorithm();

	public void newTab(DesktopInterface desktop);

	public void notifyDaddy(int index);

	public void registerObserver(DaddyInterface daddy);

	public void removeObserver(DaddyInterface daddy);

	public void setEditMode(int mode);

	public void unselectAll();
}
