package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponent;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public class VertexListenerEdgeMode implements MouseListener, MouseMotionListener {

	private boolean isLeftButtonPressed(final MouseEvent e) {

		return e.getButton() == MouseEvent.BUTTON1;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {

		if (isLeftButtonPressed(e)) {
			final VertexComponentInterface vertex = (VertexComponentInterface) e
					.getSource();
			System.out.println("mouseClicked: VertexComponentInterface("
					+ vertex.getDesktopID() + ", " + vertex.getID() + ")");
			if (!vertex.existPaintEdge()) {
				vertex.unselectAll();
				vertex.selectRed();
				vertex.addEdge();
			} else {
				vertex.finishPaintEdge();
				vertex.unselectAll();
			}
		}

	}

	@Override
	public void mouseDragged(final MouseEvent e) {

		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(final MouseEvent e) {

		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(final MouseEvent e) {

		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(final MouseEvent e) {

		final VertexComponentInterface vertex = (VertexComponentInterface) e.getSource();
		if (vertex.existPaintEdge()) {
			vertex.setTempVertexPaintEdge(
					vertex.getX() + e.getX() - VertexComponent.getBoundsSize() / 2,
					vertex.getY() + e.getY() - VertexComponent.getBoundsSize() / 2);
		}

	}

	@Override
	public void mousePressed(final MouseEvent e) {

		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(final MouseEvent e) {

		// TODO Auto-generated method stub

	}

}
