package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.Point;

public interface VertexComponentInterface {
	public String getID();
	public Point getCoordinates();
	public int getDesktopID();
	public void select();
	public void unselect();
	public int getMouseX();
	public void setMouseX(int mouseX);
	public int getMouseY();
	public void setMouseY(int mouseY);
	public boolean isSelected();
}
