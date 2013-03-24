package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.Component;
import java.util.List;

import javax.swing.JComponent;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopObserverInterface;

public interface DesktopInterface {
	public int getID();
	
	public void registerObserver(DesktopObserverInterface observer);
	public void removeObserver(DesktopObserverInterface observer);
	public void notifyObservers();
	
	public void notifyObserversAllInformation();
	public void notifyObserversBasicInformation();
	public void notifyObservers(VertexComponentInterface vertex);
	public void notifyObservers(EdgeComponentInterface edge);
	public void notifyObserversNewVertex(int x, int y);
	
	public void setComponentZOrder(Component comp, int index);
	public void addNode(String ID, int x, int y);
	public void addSelectedVertex(VertexComponentInterface vertex);
}
