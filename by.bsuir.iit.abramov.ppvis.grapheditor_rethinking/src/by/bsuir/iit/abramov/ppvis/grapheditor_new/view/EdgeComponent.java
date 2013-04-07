package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class EdgeComponent extends JComponent implements EdgeComponentInterface {

	private final VertexComponentInterface	begVertex;
	private VertexComponentInterface		endVertex;
	private int								begX;
	private int								begY;
	private int								endX;
	private int								endY;
	private static final Color				DEFAULT_COLOR	= Color.BLACK;
	private static final Color				SELECT_COLOR	= Color.GREEN;
	private static final Color				ACTIVE_COLOR	= Color.ORANGE;
	private Color							color			= EdgeComponent.DEFAULT_COLOR;
	private static final int				DEFAULT_STROKE	= VertexComponent
																	.getBoundsSize() / 4;
	private int								stroke			= EdgeComponent.DEFAULT_STROKE;
	private boolean							selected		= false;
	private int								weight			= 0;

	public EdgeComponent(final VertexComponentInterface begVertex) {

		this.begVertex = begVertex;
		begX = begVertex.getX();
		begY = begVertex.getY();
	}

	// (c)alexandr.jylilov
	@Override
	public boolean contains(final int x, final int y) {

		double a, b, c, distPointToEdge, r1, r2, edgeLength;
		int x1, x2, y1, y2, x0, y0;
		final int p = VertexComponent.getBoundsSize() / 2;

		x1 = begX + p;
		x2 = endX + p;
		y1 = begY + p;
		y2 = endY + p;

		a = y1 - y2;
		b = x2 - x1;
		c = x1 * y2 - x2 * y1;
		x0 = x + getX();
		y0 = y + getY();
		distPointToEdge = Math.abs((a * x0 + b * y0 + c) / Math.sqrt(a * a + b * b));
		edgeLength = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow(y1 - y2, 2));
		r1 = Math.sqrt(Math.pow((x1 - x0), 2) + Math.pow(y1 - y0, 2));
		r2 = Math.sqrt(Math.pow((x2 - x0), 2) + Math.pow(y2 - y0, 2));
		r1 = Math.sqrt(Math.pow(r1, 2) - Math.pow(distPointToEdge, 2));
		r2 = Math.sqrt(Math.pow(r2, 2) - Math.pow(distPointToEdge, 2));

		return distPointToEdge <= 1.25 * stroke && r1 <= edgeLength && r2 <= edgeLength;
	}

	@Override
	public void deleteFromVertices() {

		if (begVertex != null) {
			begVertex.removeEdge(this);
		}
		if (endVertex != null) {
			endVertex.removeEdge(this);
		}

	}

	@Override
	public VertexComponentInterface getFirstVertex() {

		return begVertex;
	}

	@Override
	public final VertexComponentInterface getOtherVertex(
			final VertexComponentInterface vertex) {

		if (begVertex == vertex) {
			return endVertex;
		} else {
			return begVertex;
		}
	}

	@Override
	public VertexComponentInterface getSecondVertex() {

		return endVertex;
	}

	@Override
	public final int getWeight() {

		return weight;
	}

	private boolean isFirstVertex(final String key) {

		return key == "begin";
	}

	private boolean isSecondVertex(final String key) {

		return key == "end";
	}

	@Override
	public boolean isSelected() {

		return selected;
	}

	@Override
	public void lightSelect() {

		System.out.println("EdgeComponent(" + begVertex.getID() + ", "
				+ endVertex.getID() + ") - lightSelect()");
		setColor(EdgeComponent.ACTIVE_COLOR);
		repaint();
	}

	@Override
	public void paintComponent(final Graphics g) {

		System.out.println("EdgeComponent - paintComponent()");
		final Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(stroke));
		g2d.setColor(color);
		final int begX = begVertex.getX(), begY = begVertex.getY();
		if (endVertex != null) {
			endX = endVertex.getX();
			endY = endVertex.getY();
		}
		final int k = VertexComponent.getBoundsSize() / 2;
		final int width = Math.abs(begX - endX), height = Math.abs(begY - endY);

		int x1 = k, y1 = k, x2 = width, y2 = height;
		if (begX > endX) {
			x1 += width;
			x2 -= width;
		}
		if (begY > endY) {
			y1 += height;
			y2 -= height;
		}
		g2d.drawLine(x1, y1, x2 + k, y2 + k);
	}

	@Override
	public void select() {

		System.out.println("VertexComponent() - select()");
		selected = true;
		setColor(EdgeComponent.SELECT_COLOR);
		// desktop.setComponentZOrder(this, Desktop.TOP_LAYER);

		begVertex.getDesktop().addSelectedEdge(this);
		repaint();
	}

	private void setColor(final Color color) {

		this.color = color;
	}

	@Override
	public void setSecondTempVertex(final int x, final int y) {

		endX = x;
		endY = y;
		updateBounds();
	}

	@Override
	public void setSecondVertex(final VertexComponentInterface vertex) {

		endVertex = vertex;
		endX = vertex.getX();
		endY = vertex.getY();
		updateBounds();

	}

	@Override
	public void setStroke(final int stroke) {

		this.stroke = stroke;

	}

	@Override
	public void setWeight(final int weight) {

		this.weight = weight;

	}

	@Override
	public void unselect() {

		unselectFoundation();
		begVertex.getDesktop().unselectEdge(this);
		repaint();
	}

	@Override
	public void unselectAll() {

		begVertex.getDesktop().unselectAll();

	}

	private void unselectFoundation() {

		System.out.println("EdgeComponent(" + begVertex.getID() + ", "
				+ endVertex.getID() + ") - unselect()");
		selected = false;
		setColor(EdgeComponent.DEFAULT_COLOR);
	}

	@Override
	public void unselectSpecial() {

		unselectFoundation();
		repaint();
	}

	@Override
	public void update() {

		System.out.println("EdgeComponent() - update()");
		begX = begVertex.getX();
		begY = begVertex.getY();
		endX = endVertex.getX();
		endY = endVertex.getY();
		updateBounds();

	}

	private void updateBounds() {

		System.out.println("EdgeComponent() - updateBounds()");
		final int k = VertexComponent.getBoundsSize();
		setBounds(Math.min(begX, endX), Math.min(begY, endY),
				Math.max(begX, endX) - Math.min(begX, endX) + k, Math.max(begY, endY)
						- Math.min(begY, endY) + k);
	}

}
