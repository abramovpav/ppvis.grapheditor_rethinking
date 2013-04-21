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

class DoublePoint {
	public double	x;
	public double	y;
}

public class Graph implements GraphInterface, Serializable {

	class HighLinePoint {
		public double	x;
		public double	y;
		public Line		line;
		public Edge		edge;

		public HighLinePoint(final double x, final double y, final Line line,
				final Edge edge) {

			this.x = x;
			this.y = y;
			this.line = line;
			this.edge = edge;
		}
	}

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

	static final double						EPS					= 1e-9;

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

	private void alogrithmFloydUorshell(final Map<String, Integer> mapIndexes, int i,
			final int[][] matrix) {

		for (int k = 0; k < mapIndexes.size(); ++k) {
			for (i = 0; i < mapIndexes.size(); ++i) {
				for (int j = 0; j < mapIndexes.size(); ++j) {
					matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
				}
			}
		}
	}

	private void calculateHighLine(final List<HighLinePoint> highLinePoints,
			final List<Line> lines, final List<LineFullForm> fullLines, final Point index) {

		if (index.x != -1) {
			int index1, index2;
			index1 = index.x;

			final LineFullForm fullLine1 = searchNextPointOfHighLine(highLinePoints,
					lines, fullLines, index1);

			highLinePoints.add(new HighLinePoint(
					lines.get(fullLine1.indexInListOfLine).x, lines
							.get(fullLine1.indexInListOfLine).y, lines
							.get(fullLine1.indexInListOfLine), fullLine1.edge));

			if (index.y != -1) {
				index2 = index.y;

				final LineFullForm fullLine2 = searchNextPointOfHighLine(highLinePoints,
						lines, fullLines, index2);
			}

		} else {
			showError("Graph: can't find top lines");
		}
	}

	private void calculateLowestPoint(final List<HighLinePoint> lowestPoints,
			final List<HighLinePoint> highLinePoints) {

		int min = 0;
		double minY = highLinePoints.get(0).y;
		for (int j = 0; j < highLinePoints.size(); ++j) {
			final HighLinePoint point = highLinePoints.get(j);
			if (point.y < minY) {
				min = j;
				minY = point.y;
			}
		}
		lowestPoints.add(highLinePoints.get(min));
	}

	@Override
	public boolean checkID(final String id) {

		return vertices.containsKey(id);
	}

	private boolean checkIntersectionPoint(final DoublePoint point, final double left,
			final double right) {

		return point.x <= right && point.x >= left;
	}

	private IntersectionPoint chooseHighestPoint(
			final List<IntersectionPoint> intersections) {

		if (intersections.size() == 0) {
			return null;
		}
		IntersectionPoint highestPoint = intersections.get(0);
		final Iterator<IntersectionPoint> iterator = intersections.iterator();
		while (iterator.hasNext()) {
			final IntersectionPoint point = iterator.next();
			if (point.getY() > highestPoint.getY()) {
				highestPoint = point;
			}
		}
		return highestPoint;
	}

	private void conversionFromLineToLineFullForm(final List<Line> oldLines,
			final List<LineFullForm> newLines) {

		final Iterator<Line> iterator = oldLines.iterator();
		while (iterator.hasNext()) {
			final Line line = iterator.next();
			LineFullForm fullLine, fullLine2;
			if (!line.isMode()) {
				fullLine = new LineFullForm(line.getK1(), -1, line.getB1(), 0, 1,
						oldLines.indexOf(line), line.edge);
				newLines.add(fullLine);
			} else {
				fullLine = new LineFullForm(line.getK1(), -1, line.getB1(), 0, line.x,
						oldLines.indexOf(line), line.edge);
				fullLine2 = new LineFullForm(line.getK2(), -1, line.getB2(), line.x, 1,
						oldLines.indexOf(line), line.edge);
				newLines.add(fullLine);
				newLines.add(fullLine2);
			}
		}
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

	double determinant(final double a1, final double a2, final double b1, final double b2) {

		return a1 * b2 - a2 * b1;
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
		final int[][] matrix = generateMatrix(mapIndexes);

		alogrithmFloydUorshell(mapIndexes, i, matrix);
		printMatrix(mapIndexes, matrix);

		final List<HighLinePoint> lowestPoints = new ArrayList<Graph.HighLinePoint>();

		final Iterator<Edge> iterator = edges.iterator();
		int firstIndex = -1, secondIndex = -1;
		boolean mode = false;;
		double k1, k2, b1, b2;
		k1 = k2 = b1 = b2 = 0;
		double distance1j, distance2j, x, weight, y = 0d;
		while (iterator.hasNext()) {
			final List<HighLinePoint> highLinePoints = new ArrayList<Graph.HighLinePoint>();
			final Edge edge = iterator.next();
			if (mapIndexes.containsKey(edge.getFirstID())
					&& mapIndexes.containsKey(edge.getSecondID())) {
				final List<Line> lines = new ArrayList<Line>();
				for (int j = 0; j < mapIndexes.size(); ++j) {
					firstIndex = mapIndexes.get(edge.getFirstID());
					secondIndex = mapIndexes.get(edge.getSecondID());
					System.out.println("edge: " + firstIndex + " " + secondIndex);
					distance1j = matrix[firstIndex][j];
					distance2j = matrix[secondIndex][j];
					weight = edge.getWeight();
					System.out.println("edge " + weight + " |dist: " + distance1j + " "
							+ distance2j);

					x = (weight + distance2j - distance1j) / (2 * weight);
					System.out.println("x = " + x);
					if (x < -0.05) {
						y = distance2j + weight;
						x = 0;
						weight *= -1;

						mode = false;
						k1 = weight;
						b1 = y;
						k2 = b2 = 0;
					} else {
						if (Math.abs(x) < 0.05) {
							x = 0;
							y = distance1j;

							mode = false;
							k1 = weight * -1;
							b1 = distance2j + weight;
							k2 = b2 = 0;
						} else if (x > 1 || Math.abs(1 - x) < 0.05) {
							x = 1;
							y = distance1j;
							mode = false;
							k1 = weight;
							b1 = distance1j;
							k2 = b2 = 0;
						} else if (x < 1 && x > 0) {
							y = distance1j;
							mode = true;
							k1 = weight;
							b1 = y;
							k2 = weight * -1;
							b2 = weight + distance2j;
						}
					}
					y += x * weight;
					System.out.println("y = " + y);
					System.out.println(mode);
					System.out.println(k1 + "*x + " + b1);
					System.out.println(k2 + "*x + " + b2);
					lines.add(new Line(y, x, edge, mode, k1, b1, k2, b2));
				}

				// ¬Œ“ “”“ Õ¿ƒŒ Õ¿…“» “Œ◊ ”.
				// ¬ lines ”–¿¬Õ≈Õ»ﬂ ¬—≈’ √–¿‘» Œ¬

				final int topIndex = findTopPoint(lines);
				// Line topLine = lines.get(topIndex);

				// œ–≈Œ¡¿«”≈Ã —œ»—Œ  √–¿‘» Œ¬ ¬ —œ»—Œ  À»Õ»…
				final List<LineFullForm> fullLines = new ArrayList<LineFullForm>();
				conversionFromLineToLineFullForm(lines, fullLines);

				final Point index = findTopLinesInListOfLineFullForms(fullLines, topIndex);
				calculateHighLine(highLinePoints, lines, fullLines, index);
				calculateLowestPoint(lowestPoints, highLinePoints);

				System.out.println("***NEXT EDGE***");
			}
		}

		final List<HighLinePoint> center = findCenter(lowestPoints);
		System.out.println("Center:");
		for (int j = 0; j < center.size(); ++j) {
			final HighLinePoint point = center.get(j);
			System.out.println(">> " + point.x + " " + point.y + " "
					+ point.edge.getFirstID() + " " + point.edge.getSecondID());
		}
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

	private void finalProcessing(final Map<String, Integer> indexes, final int[][] matrix) {

		for (int i = 0; i < indexes.size(); ++i) {
			for (int j = 0; j < indexes.size(); ++j) {
				if (matrix[i][j] == -1) {
					matrix[i][j] = Graph.maxPath;
				}
				if (i == j) {
					matrix[i][j] = 0;
				}
			}
		}
	}

	private void findAllIntersectionsWithLine(final LineFullForm mainLine,
			final List<LineFullForm> fullLines,
			final List<IntersectionPoint> intersections, final double start,
			final double end) {

		final DoublePoint intersectionPoint = new DoublePoint();
		for (int i = 0; i < fullLines.size(); ++i) {
			if (i != mainLine.indexInListOfLine) {
				final LineFullForm line = fullLines.get(i);
				if (isIntersect(mainLine, line, intersectionPoint)
						&& checkIntersectionPoint(intersectionPoint, start, end)
						&& checkIntersectionPoint(intersectionPoint, line.left,
								line.right)) {
					intersections.add(new IntersectionPoint(line, intersectionPoint.x,
							intersectionPoint.y));
				}
			}
		}
	}

	private List<HighLinePoint> findCenter(final List<HighLinePoint> lowestPoints) {

		double minDistance = lowestPoints.get(0).y;
		for (int j = 0; j < lowestPoints.size(); ++j) {
			final HighLinePoint point = lowestPoints.get(j);
			if (minDistance > point.y) {
				minDistance = point.y;
			}
		}
		final List<HighLinePoint> center = new ArrayList<Graph.HighLinePoint>();
		for (int j = 0; j < lowestPoints.size(); ++j) {
			final HighLinePoint point = lowestPoints.get(j);
			if (point.y == minDistance) {
				center.add(point);
			}
		}
		return center;
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

		final List<Integer> mas = new ArrayList<Integer>();
		for (int i = 0; i < edges.size(); ++i) {
			final Edge edge = edges.get(i);
			if (edge.getFirstID().equalsIgnoreCase(ID)
					|| edge.getSecondID().equalsIgnoreCase(ID)) {
				mas.add(i);
			}
		}

		return mas;
	}

	private Point findTopLinesInListOfLineFullForms(final List<LineFullForm> fullLines,
			final int topIndex) {

		final Point index = new Point(-1, -1);
		boolean xIsEmpty = true;
		for (int i = 0; i < fullLines.size(); ++i) {

			final LineFullForm line = fullLines.get(i);
			if (line.indexInListOfLine == topIndex) {
				if (xIsEmpty) {
					xIsEmpty = false;
					index.x = i;
				} else {
					index.y = i;
				}
			}
		}
		return index;
	}

	private int findTopPoint(final List<Line> lines) {

		int max = 0;
		double maxY = lines.get(0).y;
		double y1 = 0, y2 = 0;
		for (int index = 0; index < lines.size(); ++index) {
			if (lines.get(index).isMode()) {
				if (lines.get(index).y > lines.get(max).y) {
					max = index;
					maxY = lines.get(index).y;
				}
			} else {
				y1 = lines.get(index).getValue(0);
				y2 = lines.get(index).getValue(1);
				if (y1 < y2) {
					y1 = y2;
				}
				if (maxY < y1) {
					max = index;
					maxY = y1;
				}
			}
		}
		return max;
	}

	private int[][] generateMatrix(final Map<String, Integer> indexes) {

		final int[][] matrix = new int[indexes.size()][indexes.size()];

		preprocessing(indexes, matrix);
		fillMatrix(indexes, matrix);
		finalProcessing(indexes, matrix);

		printMatrix(indexes, matrix);

		return matrix;
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

	/*
	 * private void analysChart(LineFullForm mainLine, List<Line> lines,
	 * List<HighLinePoint> highLinePoints, int index, double start, double end)
	 * {
	 * 
	 * List<Intersection> intersections = new ArrayList<Intersection>();
	 * findAllIntersectionPointsWithLine(mainLine, index, lines, intersections,
	 * start, end); Intersection intersection =
	 * findHighestIntersectionPointOnLine(intersections); if (intersection !=
	 * null) { highLinePoints.add(new HighLinePoint(intersection.x,
	 * intersection.y, intersection.line)); if (!intersection.line.isMode()) {
	 * LineFullForm lineFullForm = new LineFullForm(intersection.line.getK1(),
	 * -1, intersection.line.getB1()); analysChart(lineFullForm, lines,
	 * highLinePoints, lines.indexOf(intersection.line), 0, 1); } else {
	 * LineFullForm lineFullForm = new LineFullForm(intersection.line.getK1(),
	 * -1, intersection.line.getB1()); analysChart(lineFullForm, lines,
	 * highLinePoints, lines.indexOf(intersection.line), 0,
	 * intersection.line.x); lineFullForm = new
	 * LineFullForm(intersection.line.getK2(), -1, intersection.line.getB2());
	 * analysChart(lineFullForm, lines, highLinePoints,
	 * lines.indexOf(intersection.line), intersection.line.x, 1); } } else {
	 * //≈—À» Õ≈“ œ≈–≈—≈◊≈Õ»…, “Œ Õ”∆ÕŒ œ–Œ¬≈–»“‹ œ≈–≈—≈ ¿≈“ À» ›“¿ À»Õ»ﬂ Œ—» X
	 * = 0 //» X = 1, » «¿œ»—¿“‹ ›“» “Œ◊ » ¬ highLinePoints } }
	 */
	/*
	 * private Intersection
	 * findHighestIntersectionPointOnLine(List<Intersection> intersections) { if
	 * (intersections.size() == 0) { return null; } Intersection intersection =
	 * intersections.get(0); for (int i = 0; i < intersections.size(); ++i) { if
	 * (intersection.y < intersections.get(i).y) { intersection =
	 * intersections.get(i); } } return intersection; }
	 */
	/*
	 * private void findAllIntersectionPointsWithLine(LineFullForm mainLine, int
	 * index, List<Line> lines, List<Intersection> intersections, double start,
	 * double end) { DoublePoint resPoint = new DoublePoint(); for (int i = 0; i
	 * < lines.size(); ++i) { if (i != index) { Line line = lines.get(i);
	 * LineFullForm currentLinePart1 = new LineFullForm(line.getK1(), -1,
	 * line.getB1()); LineFullForm currentLinePart2 = new
	 * LineFullForm(line.getK2(), -1, line.getB2());
	 * 
	 * if (isIntersect(mainLine, currentLinePart1, resPoint)) {
	 * checkIntersectionPointLocation(resPoint, line, intersections, start,
	 * line.x); } if (line.isMode()) { if (isIntersect(mainLine,
	 * currentLinePart2, resPoint)) { checkIntersectionPointLocation(resPoint,
	 * line, intersections, line.x, end); } } } } }
	 */
	/*
	 * private void checkIntersectionPointLocation(DoublePoint point, Line line,
	 * List<Intersection> intersections, double start, double end) { if (point.x
	 * > start && point.x < end) { intersections.add(new Intersection(line,
	 * point.x, point.y)); } }
	 */
	/*
	 * //Ì‡ıÓ‰ËÚ ‚ÒÂ ÔÂÂÒÂ˜ÂÌËˇ „‡ÙËÍ‡ ÔÓ‰ ËÌ‰ÂÍÒÓÏ index ‚ lines ÒÓ ‚ÒÂÏË
	 * ÓÒÚ‡Î¸Ì˚ÏË //„‡ÙËÍ‡ÏË ËÁ lines Ë Á‡ÔËÒ˚‚‡ÂÚ Ëı ‚ intersection private
	 * void findIntersections(List<Line> lines, List<Intersection>
	 * intersections, int index) { Line mainLine = lines.get(index);
	 * LineFullForm mainLinePart1FullForm = new LineFullForm(); LineFullForm
	 * mainLinePart2FullForm = new LineFullForm(); mainLinePart1FullForm.b = -1;
	 * mainLinePart1FullForm.a = mainLine.getK1(); mainLinePart1FullForm.c =
	 * mainLine.getB1(); mainLinePart2FullForm.b = -1; mainLinePart2FullForm.a =
	 * mainLine.getK2(); mainLinePart2FullForm.c = mainLine.getB2(); DoublePoint
	 * intersectionPoint = new DoublePoint(); for (int i = 0; i < lines.size();
	 * ++i) { if (i != index) { Line currentLine = lines.get(i);
	 * 
	 * 
	 * LineFullForm currentLinePart1FullForm = new LineFullForm();
	 * currentLinePart1FullForm.b = -1; currentLinePart1FullForm.a =
	 * currentLine.getK1(); currentLinePart1FullForm.c = currentLine.getB1();
	 * 
	 * LineFullForm currentLinePart2FullForm = new LineFullForm();
	 * currentLinePart2FullForm.b = -1; currentLinePart2FullForm.a =
	 * currentLine.getK2(); currentLinePart2FullForm.c = currentLine.getB2();
	 * //Ó·Â ÔˇÏ˚Â if (!mainLine.isMode() && !currentLine.isMode()) { if
	 * (isIntersect(mainLinePart1FullForm, currentLinePart1FullForm,
	 * intersectionPoint)) { analisIntersectionPoint(intersections,
	 * intersectionPoint, currentLine, 0, 1); } } else //ÂÒÎË ÚÓÎ¸ÍÓ Ó‰Ì‡ ËÁ ÌËı
	 * ÌÂ ÔˇÏ‡ˇ if (!(mainLine.isMode() && currentLine.isMode())) { if
	 * (mainLine.isMode()) { if (isIntersect(mainLinePart1FullForm,
	 * currentLinePart1FullForm, intersectionPoint))
	 * analisIntersectionPoint(intersections, intersectionPoint, currentLine, 0,
	 * mainLine.x); if (isIntersect(mainLinePart2FullForm,
	 * currentLinePart1FullForm, intersectionPoint))
	 * analisIntersectionPoint(intersections, intersectionPoint, currentLine,
	 * mainLine.x, 1); } else { if (isIntersect(mainLinePart1FullForm,
	 * currentLinePart1FullForm, intersectionPoint))
	 * analisIntersectionPoint(intersections, intersectionPoint, currentLine, 0,
	 * currentLine.x); if (isIntersect(mainLinePart1FullForm,
	 * currentLinePart2FullForm, intersectionPoint))
	 * analisIntersectionPoint(intersections, intersectionPoint, currentLine,
	 * currentLine.x, 1); } } else { //ÂÒÎË Û Ó·ÂËı ÏÓ‰ true LineFullForm
	 * line1p1, line1p2, line2p1, line2p2; double x1,x2; if (mainLine.x <
	 * currentLine.x) { line1p1 = mainLinePart1FullForm; line1p2 =
	 * mainLinePart2FullForm; x1 = mainLine.x;
	 * 
	 * line2p1 = currentLinePart1FullForm; line2p2 = currentLinePart2FullForm;
	 * x2 = currentLine.x; } else { line1p1 = currentLinePart1FullForm; line1p2
	 * = currentLinePart2FullForm; x1 = currentLine.x;
	 * 
	 * line2p1 = mainLinePart1FullForm; line2p2 = mainLinePart2FullForm; x2 =
	 * mainLine.x; }
	 * 
	 * if (isIntersect(line1p1, line2p1, intersectionPoint))
	 * analisIntersectionPoint(intersections, intersectionPoint, currentLine, 0,
	 * x1); if (isIntersect(line1p2, line2p1, intersectionPoint))
	 * analisIntersectionPoint(intersections, intersectionPoint, currentLine,
	 * x1, x2); if (isIntersect(line1p2, line2p2, intersectionPoint))
	 * analisIntersectionPoint(intersections, intersectionPoint, currentLine,
	 * x2, 1); if (isIntersect(line1p1, line2p1, intersectionPoint))
	 * analisIntersectionPoint(intersections, intersectionPoint, currentLine, 0,
	 * x1); } } } }
	 */
	/*
	 * private void analisIntersectionPoint(List<Intersection> intersections,
	 * DoublePoint intersectionPoint, Line currentLine, double a, double b) {
	 * 
	 * if (intersectionPoint.x >= a && intersectionPoint.x <= b) {
	 * intersections.add(new Intersection(currentLine, intersectionPoint.x,
	 * intersectionPoint.y)); } }
	 */
	boolean isIntersect(final LineFullForm line1, final LineFullForm line2,
			final DoublePoint resPoint) {

		if (isLineParallel(line1, line2)) {
			return false;
		} else {
			return isLineIntersect(line1, line2, resPoint);
		}

	}

	boolean isLineEquivalent(final LineFullForm line1, final LineFullForm line2) {

		return Math.abs(determinant(line1.a, line1.b, line2.a, line2.b)) < Graph.EPS
				&& Math.abs(determinant(line1.a, line1.c, line2.a, line2.c)) < Graph.EPS
				&& Math.abs(determinant(line1.b, line1.c, line2.b, line2.c)) < Graph.EPS;
	}

	private boolean isLineIntersect(final LineFullForm line1, final LineFullForm line2,
			final DoublePoint resPoint) {

		final double zn = determinant(line1.a, line1.b, line2.a, line2.b);
		if (Math.abs(zn) < Graph.EPS) {
			return false;
		}
		resPoint.x = -determinant(line1.c, line1.b, line2.c, line2.b) / zn;
		resPoint.y = -determinant(line1.a, line1.c, line2.a, line2.c) / zn;
		return true;
	}

	boolean isLineParallel(final LineFullForm line1, final LineFullForm line2) {

		return Math.abs(determinant(line1.a, line1.b, line2.a, line2.b)) < Graph.EPS;
	}

	@Override
	public void newVertexID(final String oldID, final String newID) {

		if (vertices.containsKey(oldID)) {
			final Vertex vertex = vertices.get(oldID);
			vertices.remove(oldID);
			vertices.put(newID, vertex);
		}
	}

	@Override
	public void newWeight(final String firstID, final String secondID, final int weight) {

		final int index = findEdge(firstID, secondID);
		if (index == -1) {
			showError("in newWeight(" + firstID + ", " + secondID + ", " + weight
					+ ") edge not found");
			return;
		}
		final Edge edge = edges.get(index);
		edge.setWeight(weight);

	}

	private void preprocessing(final Map<String, Integer> indexes, final int[][] matrix) {

		for (int i = 0; i < indexes.size(); ++i) {
			for (int j = 0; j < indexes.size(); ++j) {
				matrix[i][j] = -1;
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

	@Override
	public void registerObserver(final DesktopObserver observer) {

		observers.add(observer);
		observer.setGraph(this);

	}

	@Override
	public void removeObserver(final DesktopObserver observer) {

		observers.remove(observer);
	}

	private LineFullForm searchNextPointOfHighLine(
			final List<HighLinePoint> highLinePoints, final List<Line> lines,
			final List<LineFullForm> fullLines, final int index1) {

		final LineFullForm fullLine1 = fullLines.get(index1);

		if (fullLine1.left == 0 && fullLine1.right == 1) {

			if (lines.get(fullLine1.indexInListOfLine).x == 1) {
				searchNextPointOfHighLineL(fullLine1, fullLines, lines, highLinePoints,
						0, 1);
			} else {
				searchNextPointOfHighLineR(fullLine1, fullLines, lines, highLinePoints,
						0, 1);
			}

		} else if (fullLine1.left != 0) {
			searchNextPointOfHighLineR(fullLine1, fullLines, lines, highLinePoints,
					fullLine1.left, 1);
		} else if (fullLine1.right != 1) {
			searchNextPointOfHighLineL(fullLine1, fullLines, lines, highLinePoints, 0,
					fullLine1.right);
		}
		return fullLine1;
	}

	private void searchNextPointOfHighLineL(final LineFullForm line,
			final List<LineFullForm> fullLines, final List<Line> lines,
			final List<HighLinePoint> highLinePoints, final double left,
			final double right) {

		final List<IntersectionPoint> intersections = new ArrayList<IntersectionPoint>();
		findAllIntersectionsWithLine(line, fullLines, intersections, left, right - 0.0001);
		final IntersectionPoint highestPoint = chooseHighestPoint(intersections);
		if (highestPoint != null) {
			highLinePoints.add(new HighLinePoint(highestPoint.getX(),
					highestPoint.getY(),
					lines.get(highestPoint.getLine().indexInListOfLine), line.edge));
			searchNextPointOfHighLineL(highestPoint.getLine(), fullLines, lines,
					highLinePoints, left, highestPoint.getX()/* - 0.0001 */);
		} else {
			// Ò ÔˇÏÓÈ x = 0
			final LineFullForm leftBorder = new LineFullForm(1, 0, 0, 0, 0, -1, line.edge);
			final DoublePoint point = new DoublePoint();
			if (isIntersect(line, leftBorder, point)) {
				highLinePoints.add(new HighLinePoint(point.x, point.y, null, line.edge));
			}
		}
	}

	private void searchNextPointOfHighLineR(final LineFullForm line,
			final List<LineFullForm> fullLines, final List<Line> lines,
			final List<HighLinePoint> highLinePoints, final double left,
			final double right) {

		final List<IntersectionPoint> intersections = new ArrayList<IntersectionPoint>();
		findAllIntersectionsWithLine(line, fullLines, intersections, left + 0.0001, right);
		final IntersectionPoint highestPoint = chooseHighestPoint(intersections);
		if (highestPoint != null) {
			highLinePoints.add(new HighLinePoint(highestPoint.getX(),
					highestPoint.getY(),
					lines.get(highestPoint.getLine().indexInListOfLine), line.edge));
			searchNextPointOfHighLineR(highestPoint.getLine(), fullLines, lines,
					highLinePoints, highestPoint.getX()/* + 0.0001 */, right);
		} else {
			// Ò ÔˇÏÓÈ x = 1
			final LineFullForm rightBorder = new LineFullForm(1, 0, -1, 1, 1, -1,
					line.edge);
			final DoublePoint point = new DoublePoint();
			if (isIntersect(line, rightBorder, point)) {
				highLinePoints.add(new HighLinePoint(point.x, point.y, null, line.edge));
			}

		}
	}

	public void showError(final String text) {

		final Iterator<DesktopObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().showError(text);
		}
	}

	@Override
	public void vertexNewID(final String oldID, final String newID) {

		final List<Integer> indexes = findEdgesOneID(oldID);
		for (final int index : indexes) {
			final Edge edge = edges.get(index);
			if (edge.getFirstID().equalsIgnoreCase(oldID)) {
				edge.setFirstID(newID);
			}
			if (edge.getSecondID().equalsIgnoreCase(oldID)) {
				edge.setSecondID(newID);
			}
		}
	}
}

class Intersection {
	Line	line;
	double	x;
	double	y;

	public Intersection(final Line line, final double x, final double y) {

		this.line = line;
		this.x = x;
		this.y = y;
	}

	public Line getLine() {

		return line;
	}

	public double getX() {

		return x;
	}

	public double getY() {

		return y;
	}

}

class IntersectionPoint {
	private final double		x;
	private final double		y;
	private final LineFullForm	line;

	public IntersectionPoint(final LineFullForm line, final double x, final double y) {

		this.line = line;
		this.x = x;
		this.y = y;
	}

	public LineFullForm getLine() {

		return line;
	}

	public double getX() {

		return x;
	}

	public double getY() {

		return y;
	}
}

class Line {
	private final boolean	mode;
	private final double	k1;
	private final double	k2;
	private final double	b1;
	private final double	b2;
	public double			y;
	public double			x;
	public Edge				edge;

	public Line(final double y, final double x, final Edge edge, final boolean mode,
			final double k1, final double b1, final double k2, final double b2) {

		this.y = y;
		this.x = x;
		this.edge = edge;
		this.mode = mode;
		this.k1 = k1;
		this.b1 = b1;
		this.k2 = k2;
		this.b2 = b2;
	}

	public double getB1() {

		return b1;
	}

	public double getB2() {

		return b2;
	}

	public double getK1() {

		return k1;
	}

	public double getK2() {

		return k2;
	}

	public double getValue(final double x) {

		double y = 0;
		if (!mode) {
			y = k1 * x + b1;
		} else {
			if (x < this.x) {
				y = k1 * x + b1;
			} else {
				y = k2 * x + b2;;
			}
		}
		return y;
	}

	public boolean isMode() {

		return mode;
	}
}

class LineFullForm {
	public final double	a;
	public final double	b;
	public final double	c;
	public final double	right;
	public final double	left;
	public final int	indexInListOfLine;
	public final Edge	edge;

	public LineFullForm(final double a, final double b, final double c,
			final double left, final double right, final int index, final Edge edge) {

		this.a = a;
		this.b = b;
		this.c = c;
		this.left = left;
		this.right = right;
		indexInListOfLine = index;
		this.edge = edge;
	}
}
