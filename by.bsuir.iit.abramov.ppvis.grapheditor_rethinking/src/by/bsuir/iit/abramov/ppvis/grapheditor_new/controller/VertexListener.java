package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.Desktop;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public class VertexListener implements MouseListener, MouseMotionListener {

	private final VertexListenerEdgeMode	edgeMode;
	private final VertexListenerVertexMode	vertexMode;

	public VertexListener() {

		vertexMode = new VertexListenerVertexMode();
		edgeMode = new VertexListenerEdgeMode();
	}

	private boolean isLeftButtonPressed(final MouseEvent e) {

		return (e.getButton() == MouseEvent.BUTTON1)
				|| (e.getModifiers() == 16 || e.getModifiers() == 18);
	}

	@Override
	public void mouseClicked(final MouseEvent e) {

		if (isLeftButtonPressed(e)) {
			final VertexComponentInterface vertex = (VertexComponentInterface) e
					.getSource();
			switch (vertex.getEditMode()) {
				case Desktop.VERTEX_MODE:
					vertexMode.mouseClicked(e);
				break;
				case Desktop.EDGE_MODE:
					edgeMode.mouseClicked(e);
				break;
			}
		}
	}

	@Override
	public void mouseDragged(final MouseEvent e) {

		if (isLeftButtonPressed(e)) {
			final VertexComponentInterface vertex = (VertexComponentInterface) e
					.getSource();
			switch (vertex.getEditMode()) {
				case Desktop.VERTEX_MODE:
					vertexMode.mouseDragged(e);
				break;
				case Desktop.EDGE_MODE:
					edgeMode.mouseDragged(e);
				break;
			}
		}
	}

	@Override
	public void mouseEntered(final MouseEvent e) {

		vertexMode.mouseEntered(e);
	}

	@Override
	public void mouseExited(final MouseEvent e) {

		vertexMode.mouseExited(e);
	}

	@Override
	public void mouseMoved(final MouseEvent e) {

		final VertexComponentInterface vertex = (VertexComponentInterface) e.getSource();
		switch (vertex.getEditMode()) {
			case Desktop.VERTEX_MODE:
				vertexMode.mouseMoved(e);
			break;
			case Desktop.EDGE_MODE:
				edgeMode.mouseMoved(e);
			break;
		}

	}

	@Override
	public void mousePressed(final MouseEvent e) {

		if (isLeftButtonPressed(e)) {
			final VertexComponentInterface vertex = (VertexComponentInterface) e
					.getSource();
			switch (vertex.getEditMode()) {
				case Desktop.VERTEX_MODE:
					vertexMode.mousePressed(e);
				break;
				case Desktop.EDGE_MODE:
					edgeMode.mousePressed(e);
				break;
			}
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {

		// TODO Auto-generated method stub

	}

}
