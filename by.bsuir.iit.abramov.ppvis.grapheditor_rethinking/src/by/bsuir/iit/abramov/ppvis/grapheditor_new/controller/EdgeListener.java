package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.EdgeComponentInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public class EdgeListener implements MouseListener, MouseMotionListener {

	private boolean isLeftButtonPressed(final MouseEvent e) {

		return e.getButton() == MouseEvent.BUTTON1;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {

		if (isLeftButtonPressed(e)) {
			final EdgeComponentInterface edge = (EdgeComponentInterface) e
					.getSource();
			System.out.println("mouseClicked: VertexComponentInterface()");
			if (!e.isControlDown()) {
				edge.unselectAll();
			}
			if (edge.isSelected()) {
				edge.unselect();
			} else {
				edge.select();
			}
		}
	}

	@Override
	public void mouseDragged(final MouseEvent e) {

		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(final MouseEvent e) {

		final EdgeComponentInterface edge = (EdgeComponentInterface) e.getSource();

		System.out.println("mouseEntered: EdgeComponentInterface("
				+ edge.getFirstVertex().getID() + ", " + edge.getSecondVertex().getID()
				+ ")");
		if (!edge.isSelected()) {
			edge.lightSelect();
		}
	}

	@Override
	public void mouseExited(final MouseEvent e) {

		final EdgeComponentInterface edge = (EdgeComponentInterface) e.getSource();
		System.out.println("mouseEntered: EdgeComponentInterface("
				+ edge.getFirstVertex().getID() + ", " + edge.getSecondVertex().getID()
				+ ")");
		if (!edge.isSelected()) {
			edge.unselect();
		}

	}

	@Override
	public void mouseMoved(final MouseEvent e) {

		// TODO Auto-generated method stub

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
