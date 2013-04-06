package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.Component;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopObserverInterface;

public interface DesktopInterface {
	public VertexComponentInterface addNode(String ID, int x, int y);

	public void addSelectedEdge(EdgeComponentInterface edge);

	public void addSelectedVertex(VertexComponentInterface vertex);

	public void deleteEdge(EdgeComponentInterface edge);

	public void deleteSelectedItems();

	public void finishPaintEdge(VertexComponentInterface vertex);

	public int getEditMode();

	public int getID();

	public boolean isPaintEdge();

	public void moveSelectedVertices(int x, int y);

	public void notifyObservers();

	public void notifyObservers(EdgeComponentInterface edge);

	public void notifyObservers(VertexComponentInterface vertex);

	public void notifyObserversAllInformation();

	public void notifyObserversBasicInformation();

	public void notifyObserversDeleteEdge(EdgeComponentInterface edge);

	public void notifyObserversDeleteVertex(VertexComponentInterface vertex);

	public void notifyObserversLightDeleteEdge(EdgeComponentInterface edge);

	public void notifyObserversNewEdge(EdgeComponentInterface edge);

	public void notifyObserversNewVertex(int x, int y);

	public void paintEdge(EdgeComponentInterface edge);

	public void registerObserver(DesktopObserverInterface observer);

	public void removeObserver(DesktopObserverInterface observer);

	public void setComponentZOrder(Component comp, int index);

	public void setEditMode(final int editMode);

	public void setTempVertexPaintEdge(int x, int y);

	public void unselectAll();

	public void unselectVertex(VertexComponentInterface vertex);
	
	public void unselectEdge(final EdgeComponentInterface edge);
}
