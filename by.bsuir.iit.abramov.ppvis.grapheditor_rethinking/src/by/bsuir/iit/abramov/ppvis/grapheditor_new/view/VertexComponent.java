package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.VertexListener;

public class VertexComponent extends JComponent implements VertexComponentInterface {
	private static int size = 20;
	private static int halfSize = size / 2;
	private static int n = 2;
	private static int d = 3;
	private Color color = Color.BLACK;
	public static final Color RED = Color.RED;
	public static final Color DEFAULT_COLOR = Color.BLACK;
	public static final Color SELECT_COLOR = Color.GREEN;
	public static final Color ACTIVE_COLOR = Color.ORANGE;
	public static final Color WHITE = Color.WHITE;
	//private List<
	private List<EdgeComponentInterface> lines;
	private int mouseX = 0;
	private int mouseY = 0;
	private Boolean selected = false;
	private DesktopInterface desktop;
	private int editMode = 0;
	private final String ID;
	
	
	public VertexComponent(String ID, int x, int y, DesktopInterface desktop)
	{
		System.out.println("VertexComponent(): id = \"" + ID + "\", x = " + x + ", y = " + y + ", desktop(" + desktop.getID() + ")");
		setBounds(x - halfSize , y - halfSize, size, size);
		this.ID = ID;
		this.desktop = desktop;
		lines = new ArrayList<EdgeComponentInterface>();
		VertexListener listener = new VertexListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}
	
	//add
	/*
	public void addLine(PLine line)
	{
		lines.add(line);
	}*/
	
	//get
	/*
	public void deleteLine(PLine line)
	{
		lines.remove(line);
	}
	
	public void deleteLines()
	{
		Rectangle rec;
		Iterator<PLine> iterator = lines.iterator();
		while(iterator.hasNext())
		{
			PLine next = iterator.next();
			rec = next.getBounds();
			DesktopInterface.removeLine(next);
			next.deleteOtherNode(this);
			iterator.remove();
			DesktopInterface.repaint(rec);
		}
	}*/
	
	@Override
	public String getID() {
		return this.ID;
	}
	
	
	public static int getBoundsSize()
	{
		return size;
	}
	
	@Override
	public int getMouseX()
	{
		return mouseX;
	}
	
	@Override
	public int getMouseY()
	{
		return mouseY;
	}
	
	public int getEditMode()
	{
		return editMode;
	}
	
	@Override
	public Point getCoordinates() {
		return new Point(getX(), getY());
	}
	
	//set
	
	@Override
	public void select()
	{
		System.out.println("VertexComponent(" + desktop.getID() + ", " + ID  + ") - select()");
		selected = true;
		setColor(SELECT_COLOR);
		desktop.setComponentZOrder(this, Desktop.TOP_LAYER);
		desktop.addSelectedVertex(this);
		repaint();
	}
	
	public void selectRed() 
	{
		System.out.println("VertexComponent(" + desktop.getID() + ", " + ID  + ") - selectRed()");
		selected = true;
		setColor(RED);
		desktop.setComponentZOrder(this, Desktop.TOP_LAYER);
		repaint();
	}
	
	@Override
	public void unselect()
	{
		System.out.println("VertexComponent(" + desktop.getID() + ", " + ID  + ") - unselect():");
		selected = false;
		setColor(DEFAULT_COLOR);
		repaint();
	 }
	
	private void setColor(Color color)
	{
		this.color = color;
	}
	
	public void setProportion(int arg0, int arg1)
	{
		n = arg0;
		d = arg1;
	}
	@Override
	public void setMouseX(int mouseX)
	{
		this.mouseX = mouseX;
	}
	@Override
	public void setMouseY(int mouseY)
	{
		this.mouseY = mouseY;
	}
	
	private static void setSize(int size) {
		VertexComponent.size = size;
		VertexComponent.halfSize = size / 2; 
	}
	
	//other
	
	@Override
	public boolean isSelected()
	{
		return selected;
	}
	
	@Override
	public void paintComponent(Graphics gr)
	{
		System.out.println("VertexComponent(" + desktop.getID() + ", " + ID  + ") - paintComponent()");
		int diam = getWidth();
		int r2 = diam / 2;
		int r1 = (n * r2)/d;
		Graphics2D g2d = (Graphics2D)gr;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(color);
		g2d.fillOval(0, 0, diam, diam);
		g2d.setColor(WHITE);
		g2d.fillOval((getWidth() - 2 * r1) / 2 , (getHeight() - 2 * r1) / 2, r1 * 2, r1 * 2);
	}
	/*
	public void linesUpdateBounds()
	{
		for (PLine line : lines)
		{
			line.updateBounds();
		}
	}*/
	
	@Override
	public boolean contains(int x, int y) {
		return Math.sqrt(Math.pow(x - halfSize, 2) + Math.pow(y - halfSize, 2)) < halfSize;	 
	}

	@Override
	public int getDesktopID() {
		return desktop.getID();
	}

}
