package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;



import java.awt.Point;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.GraphInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.EdgeComponentInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.VertexComponentInterface;

public class DesktopObserver implements DesktopObserverInterface {
	private GraphInterface graph;
	private DesktopInterface desktop;
	
	@Override
	public void update(List<VertexComponentInterface> vertices,
			List<VertexComponentInterface> selectedVertices, List<EdgeComponentInterface> edges,
			List<EdgeComponentInterface> selectedEdges) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(List<VertexComponentInterface> vertices, List<EdgeComponentInterface> edges) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(VertexComponentInterface vertex) {
		//graph.addVertex(vertex.getID(), vertex.getCoordinates());
	
	}

	@Override
	public void update(EdgeComponentInterface edge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGraph(GraphInterface graph) {
		this.graph = graph;
		
	}

	@Override
	public void setDesktop(DesktopInterface desktop) {
		this.desktop = desktop;
		
	}

	@Override
	public void newVertex(int x, int y) {
		String ID = graph.addVertex(new Point(x, y));
		desktop.addNode(ID, x, y);
	}

}
