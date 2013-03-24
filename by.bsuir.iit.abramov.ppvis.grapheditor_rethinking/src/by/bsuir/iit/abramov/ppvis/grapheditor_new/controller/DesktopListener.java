package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponent;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public class DesktopListener implements MouseListener, MouseMotionListener {

	@Override
	public void mouseDragged(MouseEvent e) {
		DesktopInterface desktop = (DesktopInterface)e.getSource();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		DesktopInterface desktop = (DesktopInterface)e.getSource();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		DesktopInterface desktop = (DesktopInterface)e.getSource();
		System.out.println("mouseClicked: desktop - " + desktop.getID());
		desktop.notifyObserversNewVertex(e.getX(), e.getY());//addNode(e.getX(), e.getY());
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		DesktopInterface desktop = (DesktopInterface)e.getSource();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		DesktopInterface desktop = (DesktopInterface)e.getSource();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		DesktopInterface desktop = (DesktopInterface)e.getSource();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		DesktopInterface desktop = (DesktopInterface)e.getSource();
	}

}
