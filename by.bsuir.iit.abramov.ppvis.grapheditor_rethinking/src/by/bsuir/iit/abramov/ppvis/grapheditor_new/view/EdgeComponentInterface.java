package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

public interface EdgeComponentInterface {
	public void deleteFromVertices();

	public VertexComponentInterface getFirstVertex();

	public VertexComponentInterface getOtherVertex(VertexComponentInterface vertex);

	public VertexComponentInterface getSecondVertex();

	public int getWeight();

	public boolean isSelected();

	public void lightSelect();

	public void select();

	public void setSecondTempVertex(int x, int y);

	public void setSecondVertex(VertexComponentInterface vertex);

	public void setStroke(int stroke);

	public void setWeight(int weight);

	public void unselect();

	public void unselectAll();

	public void unselectSpecial();

	public void update();
}
