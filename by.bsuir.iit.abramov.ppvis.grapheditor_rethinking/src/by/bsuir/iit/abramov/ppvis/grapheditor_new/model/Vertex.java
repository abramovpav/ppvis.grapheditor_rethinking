package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Vertex implements VertexInterface{
	private List observers;
	private Point coordinates;
	private final String ID;
	private boolean selected;
	
	public Vertex(String iD, Point coordinates) {
		this.coordinates = coordinates;
		this.ID = iD;
		initialize();
	}
	
	public Vertex(String iD, int x, int y) {
		this.coordinates = new Point(x, y);
		this.ID = iD;
		initialize();
	}
	
	private void initialize() {
		this.selected = false;
		observers = new ArrayList();
	}
	
	public Point getCoordinates() {
		return coordinates;
	}
	
	public String getID()
	{
		return ID;
	}
	
	public boolean isSelected()
	{
		return this.selected;
	}
	
	public void setCoordinates(Point coordinates) {
		this.coordinates = coordinates;
	}
	
	public void setCoordinates(int x, int y) {
		this.coordinates = new Point(x, y);
	}
	
	public void setSelection(boolean selected) {
		this.selected = selected;
	}
	
}
