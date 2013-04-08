package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DesktopObserver;

public class Graph implements GraphInterface, Serializable {
	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1L;
	private final int						ID;
	private Map<String, Vertex>				vertices;
	private List<Edge>						edges;
	private transient List<DesktopObserver>	observers;
	private final int						maxIntID			= 99999;
	private static final int				maxPath				= 99;

	public Graph(final int ID) {

		super();
		this.ID = ID;
		initialize();
	}

	@Override
	public boolean addEdge(final int weight, final String firstID, final String secondID) {

		if (!checkID(firstID) || !checkID(secondID)) {
			return false;
		}
		edges.add(new Edge(weight, firstID, secondID));
		return true;
	}

	@Override
	public Vertex addVertex(final Point coordinates) {

		final String ID = generateVertexID();
		final Vertex vertex = new Vertex(this, ID, coordinates);
		vertices.put(ID, vertex);
		return vertex;
	}

	@Override
	public Vertex addVertex(final String ID, final Point coordinates) {

		String tmpID = ID;
		if (checkID(tmpID)) {
			tmpID = generateVertexID();
		}
		final Vertex vertex = new Vertex(this, tmpID, coordinates);
		vertices.put(tmpID, vertex);
		return vertex;
	}
	
	@Override
	public boolean checkID(final String id) {

		return vertices.containsKey(id);
	}

	@Override
	public void deleteEdge(final String firstID, final String secondID) {

		if (!checkID(firstID) || !checkID(secondID)) {
			return;
		}
		final int index = findEdge(firstID, secondID);
		if (index != -1) {
			edges.remove(index);
		}
	}

	@Override
	public void deleteEdge(final Vertex beginVertex, final Vertex endVertex) {

		deleteEdge(beginVertex.getID(), endVertex.getID());
	}

	@Override
	public void deleteVertex(final String ID) {

		if (vertices.containsKey(ID)) {
			vertices.remove(ID);
		}
	}

	@Override
	public void deleteVertex(final Vertex vertex) {

		deleteVertex(vertex.getID());
	}

	@Override
	public void devastation() {

		vertices.clear();
		edges.clear();
		observers.clear();
	}

	@Override
	public void doAlgorithm() {

		final Map<String, Integer> mapIndexes = new HashMap<String, Integer>();
		int i = 0;
		for (final String id : vertices.keySet()) {
			mapIndexes.put(id, i);
			i++;
		}
		int[][] matrix = generateMatrix(mapIndexes);
		
		alogrithmFloydUorshell(mapIndexes, i, matrix);
		printMatrix(mapIndexes, matrix);
		
		List<Trio> distance = new ArrayList<Graph.Trio>(); 
		
		final Iterator<Edge> iterator = edges.iterator();
		int firstIndex = -1, secondIndex = -1;
		double d1j, d2j, x, weight, y;
		while (iterator.hasNext()) {
			final Edge edge = iterator.next();
			if (mapIndexes.containsKey(edge.getFirstID())
					&& mapIndexes.containsKey(edge.getSecondID())) {
				List<Trio> ys = new ArrayList<Graph.Trio>();
				for (int j = 0; j < mapIndexes.size(); ++j)
				{
					firstIndex = mapIndexes.get(edge.getFirstID());
					secondIndex = mapIndexes.get(edge.getSecondID());
					System.out.println("edge: " + firstIndex + " " + secondIndex);
					d1j = matrix[firstIndex][j];
					d2j = matrix[secondIndex][j];
					
					weight = edge.getWeight();
					System.out.println("edge " + weight +" |ist: " + d1j + " " + d2j);
					x = (weight+ d2j - d1j) / (2 * weight);
					System.out.println("x = " + x);
					if (x < -0.05) {
						y = d2j + weight;
						x = 0;
						weight *= -1;
					}
					else {
						y = d1j;					
						if (Math.abs(x) < 0.05) {
							x = 0;
							y = d1j;
						}
						else
							if (x > 1) {
								x = 1;
								y = d1j;
							}
					}
					y += x * weight;
					System.out.println("y = " + y);
					ys.add(new Trio(y, x, edge));
				}
				Trio min = ys.get(0);
				for (Trio trio : ys) {
					if (trio.first < min.first)
						min.first = trio.first;
				}
				
				distance.add(min);
			}
		}
		for (Trio trio : distance) {
			System.out.println(trio.first + " " + trio.second + " " + trio.third.getFirstID());
		}
		
	}
	
	class Trio {
		public Trio(final double first, final double second, final Edge third) {
			this.first = first;
			this.second = second;
			this.third = third;
		}
		public double first;
		public double second;
		public Edge third;
	}

	private void alogrithmFloydUorshell(final Map<String, Integer> mapIndexes, int i,
			int[][] matrix) {

		for (int k = 0; k < mapIndexes.size(); ++k)
		{
			for (i = 0; i < mapIndexes.size(); ++i)
				for (int j = 0; j < mapIndexes.size(); ++j)
				      matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
		}
	}
	
	private int[][] generateMatrix(final Map<String, Integer> indexes) {

		final int[][] matrix = new int[indexes.size()][indexes.size()];
		
		preprocessing(indexes, matrix);
		fillMatrix(indexes, matrix);
		finalProcessing(indexes, matrix);
		
		printMatrix(indexes, matrix);
		
		return matrix;
	}

	private void fillMatrix(final Map<String, Integer> indexes, final int[][] matrix) {

		final Iterator<Edge> iterator = edges.iterator();
		while (iterator.hasNext()) {
			final Edge edge = iterator.next();
			if (indexes.containsKey(edge.getFirstID())
					&& indexes.containsKey(edge.getSecondID())) {
				matrix[indexes.get(edge.getFirstID())][indexes.get(edge.getSecondID())] = edge
						.getWeight();
				matrix[indexes.get(edge.getSecondID())][indexes.get(edge.getFirstID())] = edge
						.getWeight();
			}
		}
	}

	private void printMatrix(final Map<String, Integer> indexes, final int[][] matrix) {

		for (int i = 0; i < indexes.size(); ++i) {
			for (int j = 0; j < indexes.size(); ++j) {
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
	}

	private void finalProcessing(final Map<String, Integer> indexes, final int[][] matrix) {

		for (int i = 0; i < indexes.size(); ++i) {
			for (int j = 0; j < indexes.size(); ++j) {
				if (matrix[i][j] == -1) {
					matrix[i][j] = Graph.maxPath;
				}
				if (i == j)
					matrix[i][j] = 0;
			}
		}
	}

	private void preprocessing(final Map<String, Integer> indexes, final int[][] matrix) {

		for (int i = 0; i < indexes.size(); ++i) {
			for (int j = 0; j < indexes.size(); ++j) {
				matrix[i][j] = -1;
			}
		}
	}


	private int findEdge(final String firstID, final String secondID) {

		for (int i = 0; i < edges.size(); ++i) {
			if (isCorrectEdge(firstID, secondID, i)) {
				return i;
			}
		}
		return -1;
	}

	private List<Integer> findEdgesOneID(final String ID) {

		List<Integer> mas = new ArrayList<Integer>();
		for (int i = 0; i < edges.size(); ++i) {
			final Edge edge = edges.get(i);
			if (edge.getFirstID().equalsIgnoreCase(ID) || edge.getSecondID().equalsIgnoreCase(ID)) {
				mas.add(i);
			}
		}

		return mas;
	}

	
	@Override
	public void newVertexID(String oldID, String newID) {

		if (vertices.containsKey(oldID)) {
			Vertex vertex = vertices.get(oldID);
			vertices.remove(oldID);
			vertices.put(newID, vertex);
		}
	}

	private String generateVertexID() {

		final Random random = new Random();
		int num = random.nextInt(maxIntID);
		while (vertices.containsKey(Integer.toString(num = random.nextInt(maxIntID)))) {
		}
		return Integer.toString(num);
	}

	@Override
	public List<Edge> getEdges() {

		return edges;
	}

	public int getID() {

		return ID;
	}

	@Override
	public Map<String, Vertex> getVertices() {

		return vertices;
	}

	private void initialize() {

		observers = new ArrayList<DesktopObserver>();
		vertices = new HashMap<String, Vertex>();
		edges = new ArrayList<Edge>();
	}

	private boolean isCorrectEdge(final String firstID, final String secondID,
			final int index) {

		final String first = edges.get(index).getFirstID();
		final String second = edges.get(index).getSecondID();
		if ((firstID.equalsIgnoreCase(first) && secondID.equalsIgnoreCase(second))
				|| (firstID.equalsIgnoreCase(second) && secondID.equalsIgnoreCase(first))) {
			return true;
		}
		return false;
	}

	@Override
	public void newWeight(final String firstID, final String secondID, final int weight) {

		final int index = findEdge(firstID, secondID);
		if (index == -1) {
			showError("in newWeight(" + firstID + ", "
					+ secondID + ", " + weight + ") edge not found");
			return;
		}
		final Edge edge = edges.get(index);
		edge.setWeight(weight);

	}

	@Override
	public void registerObserver(final DesktopObserver observer) {

		observers.add(observer);
		observer.setGraph(this);

	}

	@Override
	public void removeObserver(final DesktopObserver observer) {

		observers.remove(observer);
	}

	@Override
	public void vertexNewID(final String oldID, final String newID) {

		final List<Integer> indexes = findEdgesOneID(oldID);
		for (int index : indexes) {
			final Edge edge = edges.get(index);
			if (edge.getFirstID().equalsIgnoreCase(oldID)) {
				edge.setFirstID(newID);
			}
			if (edge.getSecondID().equalsIgnoreCase(oldID)) {
				edge.setSecondID(newID);
			}
		}
	}
	
	public void showError(final String text) {
		final Iterator<DesktopObserver> iterator = observers.iterator();
		while(iterator.hasNext()) {
			iterator.next().showError(text);
		}
	}
}
