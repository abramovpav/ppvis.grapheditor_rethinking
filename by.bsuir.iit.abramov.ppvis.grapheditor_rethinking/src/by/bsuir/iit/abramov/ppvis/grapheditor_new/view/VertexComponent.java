package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.VertexListener;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.VertexObserver;

public class VertexComponent extends JComponent implements VertexComponentInterface {
	private final List<EdgeComponentInterface>	edgeObservers;
	public static final int						DEFAULT_SIZE	= 30;
	private static int							size			= VertexComponent.DEFAULT_SIZE;
	private static int							halfSize		= VertexComponent.size / 2;
	private static int							n				= 2;
	private static int							d				= 3;

	public static int getBoundsSize() {

		return VertexComponent.size;
	}

	private static void setSize(final int size) {

		VertexComponent.size = size;
		VertexComponent.halfSize = size / 2;
	}

	private final String						key				= "vertex";
	private Color								color			= Color.BLACK;
	public static final Color					RED				= Color.RED;
	public static final Color					DEFAULT_COLOR	= Color.BLACK;
	public static final Color					SELECT_COLOR	= Color.GREEN;
	public static final Color					ACTIVE_COLOR	= Color.ORANGE;
	public static final Color					WHITE			= Color.WHITE;
	public static final Color					TEXT_COLOR		= Color.BLUE;
	private int									mouseX			= 0;
	private int									mouseY			= 0;
	private Boolean								selected		= false;
	private final List<VertexObserver>			observers;
	private final DesktopInterface				desktop;
	private final List<EdgeComponentInterface>	lines;
	private String								ID;

	public VertexComponent(final String ID, final int x, final int y,
			final DesktopInterface desktop) {

		System.out.println("VertexComponent(): id = \"" + ID + "\", x = " + x + ", y = "
				+ y + ", desktop(" + desktop.getID() + ")");
		setBounds(x - VertexComponent.halfSize, y - VertexComponent.halfSize,
				VertexComponent.size * 3, VertexComponent.size + 3);
		this.ID = checkID(ID);
		this.desktop = desktop;
		lines = new ArrayList<EdgeComponentInterface>();
		observers = new ArrayList<VertexObserver>();
		edgeObservers = new ArrayList<EdgeComponentInterface>();
		final VertexListener listener = new VertexListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}

	@Override
	public void addEdge() {

		final EdgeComponentInterface edge = new EdgeComponent(this);
		registerEdge(edge);
		desktop.paintEdge(edge);

	}

	private final String checkID(final String ID) {

		int endIndex = 5;
		if (ID.length() < endIndex) {
			endIndex = ID.length();
		}
		return ID.substring(0, endIndex);
	}

	@Override
	public boolean contains(final int x, final int y) {

		return Math.sqrt(Math.pow(x - VertexComponent.halfSize, 2)
				+ Math.pow(y - VertexComponent.halfSize, 2)) < VertexComponent.halfSize;
	}

	@Override
	public void deleteEdge(final EdgeComponentInterface edge) {

		if (edgeObservers.contains(edge)) {
			edgeObservers.remove(edge);
		}

	}

	@Override
	public void deleteEdges() {

		final Iterator<EdgeComponentInterface> iterator = edgeObservers.iterator();
		while (iterator.hasNext()) {
			final EdgeComponentInterface edge = iterator.next();
			edge.getOtherVertex(this).deleteEdge(edge);
			desktop.deleteEdge(edge);
			iterator.remove();
		}

	}

	// set

	@Override
	public boolean existPaintEdge() {

		return desktop.isPaintEdge();
	}

	@Override
	public void finishPaintEdge() {

		desktop.finishPaintEdge(this);

	}

	@Override
	public Point getCoordinates() {

		return new Point(getX(), getY());
	}

	@Override
	public final DesktopInterface getDesktop() {

		return desktop;
	}

	@Override
	public int getDesktopID() {

		return desktop.getID();
	}

	@Override
	public int getEditMode() {

		return desktop.getEditMode();
	}

	@Override
	public String getID() {

		return ID;
	}

	@Override
	public int getMouseX() {

		return mouseX;
	}

	@Override
	public int getMouseY() {

		return mouseY;
	}

	// other

	@Override
	public boolean isSelected() {

		return selected;
	}

	@Override
	public void lightSelect() {

		System.out.println("VertexComponent(" + desktop.getID() + ", " + ID
				+ ") - lightSelect()");
		setColor(VertexComponent.ACTIVE_COLOR);
		repaint();
	}

	@Override
	public void moveSelectedVertices(final int x, final int y) {

		desktop.moveSelectedVertices(x, y);

	}

	@Override
	public void moveVertex(final int x, final int y) {

		System.out.println("VertexComponent(" + desktop.getID() + ", " + ID
				+ ") - moveVertex(" + x + ", " + y + ")");
		setLocation(getX() + x, getY() + y);
		notifyObserversNewLocation(getX() + x, getY() + y);
	}

	@Override
	public void notifyNewID() {

		final Iterator<VertexObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update(ID);
		}

	}

	@Override
	public void notifyObserversNewLocation(final int x, final int y) {

		final Iterator<VertexObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().newLocation(x, y);
		}
		final Iterator<EdgeComponentInterface> iter = edgeObservers.iterator();
		while (iter.hasNext()) {
			iter.next().update();
		}

	}

	@Override
	public void paintComponent(final Graphics gr) {

		System.out.println("VertexComponent(" + desktop.getID() + ", " + ID
				+ ") - paintComponent()");
		final int diam = VertexComponent.size;
		final int r2 = diam / 2;
		final int r1 = (VertexComponent.n * r2) / VertexComponent.d;
		final Graphics2D g2d = (Graphics2D) gr;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(color);
		g2d.fillOval(0, 0, diam, diam);
		g2d.setColor(VertexComponent.WHITE);
		g2d.fillOval((VertexComponent.size - 2 * r1) / 2,
				(VertexComponent.size - 2 * r1) / 2, r1 * 2, r1 * 2);
		final char[] text = ID.toCharArray();
		g2d.setColor(VertexComponent.TEXT_COLOR);
		g2d.drawChars(text, 0, text.length, VertexComponent.size, VertexComponent.size);
	}

	@Override
	public void registerEdge(final EdgeComponentInterface edge) {

		edgeObservers.add(edge);
	}

	@Override
	public void registerObserver(final VertexObserver observer) {

		observers.add(observer);
		observer.setVertex(this);

	}

	@Override
	public void removeEdge(final EdgeComponentInterface edge) {

		edgeObservers.remove(edge);

	}

	@Override
	public void removeObserver(final VertexObserver observer) {

		observers.remove(observer);

	}

	@Override
	public void removeObservers() {

		final Iterator<VertexObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().delVertexComponent();
			iterator.remove();
		}

	}

	@Override
	public void select() {

		System.out.println("VertexComponent(" + desktop.getID() + ", " + ID
				+ ") - select()");
		selected = true;
		setColor(VertexComponent.SELECT_COLOR);
		desktop.setComponentZOrder(this, Desktop.TOP_LAYER);
		desktop.addSelectedVertex(this);
		repaint();
	}

	@Override
	public void selectRed() {

		System.out.println("VertexComponent(" + desktop.getID() + ", " + ID
				+ ") - selectRed()");
		selected = true;
		setColor(VertexComponent.RED);
		desktop.setComponentZOrder(this, Desktop.TOP_LAYER);
		desktop.addSelectedVertex(this);
		repaint();
	}

	private void setColor(final Color color) {

		this.color = color;
	}

	@Override
	public void setID(final String ID) {

		this.ID = checkID(ID);
		notifyNewID();
		repaint();

	}

	@Override
	public void setMouseX(final int mouseX) {

		this.mouseX = mouseX;
	}

	@Override
	public void setMouseY(final int mouseY) {

		this.mouseY = mouseY;
	}

	public void setProportion(final int arg0, final int arg1) {

		VertexComponent.n = arg0;
		VertexComponent.d = arg1;
	}

	@Override
	public void setTempVertexPaintEdge(final int x, final int y) {

		desktop.setTempVertexPaintEdge(x, y);

	}

	@Override
	public void unselect() {

		unselectFoundation();
		desktop.unselectVertex(this);
		repaint();
	}

	@Override
	public void unselectAll() {

		desktop.unselectAll();

	}

	private void unselectFoundation() {

		System.out.println("VertexComponent(" + desktop.getID() + ", " + ID
				+ ") - unselect():");
		selected = false;
		setColor(VertexComponent.DEFAULT_COLOR);
	}

	@Override
	public void unselectSpecial() {

		unselectFoundation();
		repaint();
	}

	@Override
	public void updateEdges() {

		final Iterator<EdgeComponentInterface> iterator = edgeObservers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update();
		}

	}
}
