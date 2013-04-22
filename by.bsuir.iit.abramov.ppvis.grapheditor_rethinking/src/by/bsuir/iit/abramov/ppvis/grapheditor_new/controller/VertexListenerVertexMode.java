package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.Desktop;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public class VertexListenerVertexMode implements MouseListener, MouseMotionListener {

	private boolean isLeftButtonPressed(final MouseEvent e) {

		return e.getButton() == MouseEvent.BUTTON1;
	}

	private boolean isVertexMode(final VertexComponentInterface vertex) {

		return vertex.getEditMode() == Desktop.VERTEX_MODE;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {

		if (isLeftButtonPressed(e)) {
			final VertexComponentInterface vertex = (VertexComponentInterface) e
					.getSource();
			System.out.println("mouseClicked: VertexComponentInterface("
					+ vertex.getDesktopID() + ", " + vertex.getID() + ")");
			if (!e.isControlDown()) {
				vertex.unselectAll();
			}
			if (vertex.isSelected()) {
				vertex.unselect();
			} else {
				vertex.select();
			}
		}
	}

	@Override
	public void mouseDragged(final MouseEvent e) {

		final VertexComponentInterface vertex = (VertexComponentInterface) e.getSource();
		System.out.println("mouseDragged(" + (vertex.getX() + e.getX()) + ", "
				+ (vertex.getY() + e.getY()) + "): VertexComponentInterface("
				+ vertex.getDesktopID() + ", " + vertex.getID() + ")");
		if (!vertex.isSelected()) {
			if (!e.isControlDown()) {
				vertex.unselectAll();
				vertex.select();
			} else {
				vertex.select();
			}
		}
		vertex.moveSelectedVertices(e.getX() - vertex.getMouseX(),
				e.getY() - vertex.getMouseY());
	}

	@Override
	public void mouseEntered(final MouseEvent e) {

		final VertexComponentInterface vertex = (VertexComponentInterface) e.getSource();
		if (!isVertexMode(vertex)) {
			return;
		}
		System.out.println("mouseEntered: VertexComponentInterface("
				+ vertex.getDesktopID() + ", " + vertex.getID() + ")");
		if (!vertex.isSelected()) {
			vertex.lightSelect();
		}
	}

	@Override
	public void mouseExited(final MouseEvent e) {

		final VertexComponentInterface vertex = (VertexComponentInterface) e.getSource();
		if (!isVertexMode(vertex)) {
			return;
		}
		System.out.println("mouseExited: VertexComponentInterface("
				+ vertex.getDesktopID() + ", " + vertex.getID() + ")");
		if (!vertex.isSelected()) {
			vertex.unselect();
		}

	}

	@Override
	public void mouseMoved(final MouseEvent e) {

		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(final MouseEvent e) {

		if (isLeftButtonPressed(e)) {
			final VertexComponentInterface vertex = (VertexComponentInterface) e
					.getSource();
			System.out.println("mousePressed(" + e.getX() + ", " + e.getY()
					+ "): VertexComponent(" + vertex.getDesktopID() + ", "
					+ vertex.getID() + ")");
			vertex.setMouseX(e.getX());
			vertex.setMouseY(e.getY());
		}

	}

	@Override
	public void mouseReleased(final MouseEvent e) {

		// TODO Auto-generated method stub

	}

}
