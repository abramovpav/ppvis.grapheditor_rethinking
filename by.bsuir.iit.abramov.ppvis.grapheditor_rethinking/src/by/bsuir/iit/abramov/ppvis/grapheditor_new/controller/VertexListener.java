package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public class VertexListener implements MouseListener, MouseMotionListener {

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		VertexComponentInterface vertex = (VertexComponentInterface)e.getSource();
		System.out.println("mouseClicked: VertexComponent(" + vertex.getDesktopID() + ", " + vertex.getID() + ")");
		if (vertex.isSelected()) {
			vertex.unselect();
		}
		else {
			vertex.select();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		VertexComponentInterface vertex = (VertexComponentInterface)e.getSource();
		System.out.println("mousePressed: VertexComponent(" + vertex.getDesktopID() + ", " + vertex.getID() + ")");
		vertex.setMouseX(e.getX());
		vertex.setMouseY(e.getY());
	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
