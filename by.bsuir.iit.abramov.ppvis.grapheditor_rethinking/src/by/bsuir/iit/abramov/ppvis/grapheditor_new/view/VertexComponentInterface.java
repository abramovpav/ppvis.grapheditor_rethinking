package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.Point;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.VertexObserver;

public interface VertexComponentInterface {
	public void addEdge();

	public void deleteEdge(EdgeComponentInterface edge);

	public void deleteEdges();

	public boolean existPaintEdge();

	public void finishPaintEdge();

	public Point getCoordinates();

	public DesktopInterface getDesktop();

	public int getDesktopID();

	public int getEditMode();

	public String getID();

	public int getMouseX();

	public int getMouseY();

	public int getX();

	public int getY();

	public boolean isSelected();

	public void lightSelect();

	public void moveSelectedVertices(int x, int y);

	public void moveVertex(int x, int y);

	public void notifyNewID();

	public void notifyObserversNewLocation(int x, int y);

	public void registerEdge(EdgeComponentInterface edge);

	public void registerObserver(VertexObserver observer);

	public void removeEdge(EdgeComponentInterface edge);

	public void removeObserver(VertexObserver observer);

	public void removeObservers();

	public void select();

	public void selectRed();

	public void setID(String ID);

	public void setLocation(int x, int y);

	public void setMouseX(int mouseX);

	public void setMouseY(int mouseY);

	public void setTempVertexPaintEdge(int x, int y);

	public void unselect();

	public void unselectAll();

	public void unselectSpecial();

	public void updateEdges();
}
