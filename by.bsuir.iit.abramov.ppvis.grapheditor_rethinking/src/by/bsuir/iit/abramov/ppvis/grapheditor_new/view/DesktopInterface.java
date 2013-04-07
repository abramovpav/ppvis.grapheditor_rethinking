package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.Component;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopObserver;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Graph;

public interface DesktopInterface {
	public VertexComponentInterface addNode(String ID, int x, int y);

	public void addSelectedEdge(EdgeComponentInterface edge);

	public void addSelectedVertex(VertexComponentInterface vertex);

	public void cancelPaintEdge();

	public void deleteEdge(EdgeComponentInterface edge);

	public void deleteSelectedItems();

	public void finishPaintEdge(VertexComponentInterface vertex);

	public int getEditMode();

	public int getID();

	public boolean isPaintEdge();

	public void moveSelectedVertices(int x, int y);

	public void notifyObservers();

	public void notifyObservers(VertexComponentInterface vertex);

	public void notifyObserversDeleteEdge(EdgeComponentInterface edge);

	public void notifyObserversDeleteVertex(VertexComponentInterface vertex);

	public void notifyObserversLightDeleteEdge(EdgeComponentInterface edge);

	public void notifyObserversLoadVertex(String ID, int x, int y);

	public void notifyObserversNewEdge(EdgeComponentInterface edge);

	public void notifyObserversNewVertex(int x, int y);

	public void openDialogNewID();

	public void paintEdge(EdgeComponentInterface edge);
	
	public void save();
	public List<VertexComponentInterface> getVertices();
	
	public void removeVertex(final VertexComponentInterface vertex);
	
	public void clean();
	public void removeObservers();
	
	public boolean isSaved();

	public void registerObserver(DesktopObserver observer);

	public void removeObserver(DesktopObserver observer);

	public void setComponentZOrder(Component comp, int index);

	public void setEditMode(final int editMode);

	public void setState(Graph graph);

	public void setTempVertexPaintEdge(int x, int y);

	public void unselectAll();

	public void unselectEdge(final EdgeComponentInterface edge);

	public void unselectVertex(VertexComponentInterface vertex);
}
