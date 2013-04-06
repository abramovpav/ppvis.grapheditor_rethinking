package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponent;

public class DesktopListener implements MouseListener, MouseMotionListener {

	private boolean isDoubleClick(final MouseEvent e) {

		return e.getClickCount() == 2;
	}

	private boolean isLeftButtonPressed(final MouseEvent e) {

		return e.getButton() == MouseEvent.BUTTON1;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {

		final DesktopInterface desktop = (DesktopInterface) e.getSource();
		if (isDoubleClick(e)) {
			if (isLeftButtonPressed(e)) {
				System.out.println("mouseClicked - doubleClick(leftButton): desktop - "
						+ desktop.getID());
				desktop.notifyObserversNewVertex(e.getX(), e.getY());
			}
		}

	}

	@Override
	public void mouseDragged(final MouseEvent e) {

		final DesktopInterface desktop = (DesktopInterface) e.getSource();

	}

	@Override
	public void mouseEntered(final MouseEvent e) {

		final DesktopInterface desktop = (DesktopInterface) e.getSource();
	}

	@Override
	public void mouseExited(final MouseEvent e) {

		final DesktopInterface desktop = (DesktopInterface) e.getSource();
	}

	@Override
	public void mouseMoved(final MouseEvent e) {

		final DesktopInterface desktop = (DesktopInterface) e.getSource();
		if (desktop.isPaintEdge()) {
			desktop.setTempVertexPaintEdge(
					e.getX() - VertexComponent.getBoundsSize() / 2, e.getY()
							- VertexComponent.getBoundsSize() / 2);
		}
	}

	@Override
	public void mousePressed(final MouseEvent e) {

		final DesktopInterface desktop = (DesktopInterface) e.getSource();
	}

	@Override
	public void mouseReleased(final MouseEvent e) {

		final DesktopInterface desktop = (DesktopInterface) e.getSource();
	}

}
