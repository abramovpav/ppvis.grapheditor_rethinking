package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class Algorithm extends Thread {
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

	public static final Color			RED			= Color.RED;
	public static final Color			GREEN		= Color.GREEN;
	public static final Color			YELLOW		= Color.YELLOW;
	private final Map<String, Vertex>	vertices;
	private final List<Edge>			edges;
	private static final int			maxPath		= 99;
	private final Graph					graph;
	private static final int			pauseTime	= 1000;
	private static final int			pauseTime2	= 300;
	private static final int			pauseTime3	= 100;

	public Algorithm(final Graph graph, final Map<String, Vertex> vertices) {

		this.graph = graph;
		this.vertices = vertices;
		edges = graph.getEdges();
	}

	private void alogrithmFloydUorshell(final Map<Integer, String> mapIDIndex,
			final Map<String, Integer> mapIndexes, int i, final int[][] matrix) {

		Vertex vertex1, vertex2, vertex3;
		for (int k = 0; k < mapIndexes.size(); ++k) {
			vertex1 = vertices.get(mapIDIndex.get(k));
			for (i = 0; i < mapIndexes.size(); ++i) {
				vertex2 = vertices.get(mapIDIndex.get(i));
				for (int j = 0; j < mapIndexes.size(); ++j) {
					vertex3 = vertices.get(mapIDIndex.get(j));
					vertex3.select(Algorithm.RED);
					vertex1.select(Algorithm.GREEN);
					vertex2.select(Algorithm.YELLOW);
					matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
					pause(3);
					vertex3.unselect();
				}
				vertex2.unselect();
			}
			vertex1.unselect();
		}
	}

	private void blockInterface() {

		graph.blockInterface();
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
			graph.showError("Graph: can't find top lines");
		}
	}

	private void calculateLowestPointOfHighLine(final List<HighLinePoint> lowestPoints,
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

	double determinant(final double a1, final double a2, final double b1, final double b2) {

		return a1 * b2 - a2 * b1;
	}

	private void fillMatrix(final Map<String, Integer> indexes, final int[][] matrix) {

		final Iterator<Edge> iterator = edges.iterator();
		while (iterator.hasNext()) {
			final Edge edge = iterator.next();
			if (validEdgeIDs(indexes, edge)) {
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
					matrix[i][j] = Algorithm.maxPath;
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
		final List<HighLinePoint> center = new ArrayList<HighLinePoint>();
		for (int j = 0; j < lowestPoints.size(); ++j) {
			final HighLinePoint point = lowestPoints.get(j);
			if (point.y == minDistance) {
				center.add(point);
			}
		}
		return center;
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

	private String findVertexByIndex(final int vertexIndex,
			final Map<String, Integer> mapIndexes) {

		for (final String id : mapIndexes.keySet()) {
			final int index = mapIndexes.get(id);
			if (index == vertexIndex) {
				return id;
			}
		}
		return null;
	}

	private int[][] generateMatrix(final Map<String, Integer> indexes) {

		final int[][] matrix = new int[indexes.size()][indexes.size()];
		preprocessing(indexes, matrix);
		fillMatrix(indexes, matrix);
		finalProcessing(indexes, matrix);
		return matrix;
	}

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

	public void make() {

		blockInterface();
		final Map<String, Integer> mapIndexes = new HashMap<String, Integer>();
		final Map<Integer, String> mapIDIndex = new HashMap<Integer, String>();
		int i = 0;
		for (final String id : vertices.keySet()) {
			mapIndexes.put(id, i);
			mapIDIndex.put(i, id);
			i++;
		}
		final int[][] matrix = generateMatrix(mapIndexes);

		alogrithmFloydUorshell(mapIDIndex, mapIndexes, i, matrix);

		final List<HighLinePoint> lowestPoints = new ArrayList<HighLinePoint>();

		final Iterator<Edge> iterator = edges.iterator();
		int firstIndex = -1, secondIndex = -1;
		boolean mode = false;;
		double k1, k2, b1, b2;
		k1 = k2 = b1 = b2 = 0;
		double distance1j, distance2j, x, weight, y = 0;
		while (iterator.hasNext()) {
			final List<HighLinePoint> highLinePoints = new ArrayList<HighLinePoint>();
			final Edge edge = iterator.next();
			if (validEdgeIDs(mapIndexes, edge)) {
				graph.selectEdge(edge.getFirstID(), edge.getSecondID());
				final List<Line> lines = new ArrayList<Line>();

				for (int j = 0; j < mapIndexes.size(); ++j) {
					Vertex vertex = null;
					Vertex vertex1, vertex2;
					firstIndex = mapIndexes.get(edge.getFirstID());
					vertex1 = vertices.get(edge.getFirstID());
					vertex1.select(Algorithm.GREEN);
					secondIndex = mapIndexes.get(edge.getSecondID());
					vertex2 = vertices.get(edge.getSecondID());
					vertex2.select(Algorithm.GREEN);

					distance1j = matrix[firstIndex][j];
					distance2j = matrix[secondIndex][j];
					vertex = vertices.get(mapIDIndex.get(j));
					vertex.select(Algorithm.RED);
					pause(2);

					weight = edge.getWeight();

					x = (weight + distance2j - distance1j) / (2 * weight);
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
					lines.add(new Line(y, x, edge, mode, k1, b1, k2, b2));
					if (vertex != null) {
						vertex.unselect();
					}
					vertex1.unselect();
					vertex2.unselect();
				}

				// ¬Œ“ “”“ Õ¿ƒŒ Õ¿…“» “Œ◊ ”.
				// ¬ lines ”–¿¬Õ≈Õ»ﬂ ¬—≈’ √–¿‘» Œ¬

				final int topIndex = findTopPoint(lines);

				// œ–≈Œ¡¿«”≈Ã —œ»—Œ  √–¿‘» Œ¬ ¬ —œ»—Œ  À»Õ»…
				final List<LineFullForm> fullLines = new ArrayList<LineFullForm>();
				conversionFromLineToLineFullForm(lines, fullLines);

				final Point index = findTopLinesInListOfLineFullForms(fullLines, topIndex);
				calculateHighLine(highLinePoints, lines, fullLines, index);
				calculateLowestPointOfHighLine(lowestPoints, highLinePoints);

				graph.unselectEdge(edge.getFirstID(), edge.getSecondID());
			}
		}

		final List<HighLinePoint> center = findCenter(lowestPoints);
		System.out.println("Center:");

		showResults(center);
		graph.unlockInterface();
	}

	private void pause(final int mode) {

		try {
			switch (mode) {
				case 1:
					Thread.sleep(Algorithm.pauseTime);
				break;
				case 2:
					Thread.sleep(Algorithm.pauseTime2);
				break;
				case 3:
					Thread.sleep(Algorithm.pauseTime3);
			}

		} catch (final InterruptedException e) {
		}
	}

	private void preprocessing(final Map<String, Integer> indexes, final int[][] matrix) {

		for (int i = 0; i < indexes.size(); ++i) {
			for (int j = 0; j < indexes.size(); ++j) {
				matrix[i][j] = -1;
			}
		}
	}

	@Override
	public void run() {

		make();
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
			final double x = highestPoint.getX();
			final double y = highestPoint.getY();
			highLinePoints.add(new HighLinePoint(x, y,
					lines.get(highestPoint.getLine().indexInListOfLine), line.edge));
			searchNextPointOfHighLineL(highestPoint.getLine(), fullLines, lines,
					highLinePoints, left, x/* - 0.0001 */);
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

	private void showResults(final List<HighLinePoint> center) throws HeadlessException {

		HighLinePoint point = center.get(0);
		Vertex vertex;
		for (int j = 0; j < center.size(); ++j) {
			point = center.get(j);
			graph.selectEdge(point.edge.getFirstID(), point.edge.getSecondID());
			vertex = vertices.get(point.edge.getFirstID());
			vertex.select(Algorithm.RED);
			JOptionPane.showMessageDialog(null,
					point.x + " from " + point.edge.getFirstID() + " |maxDistance = "
							+ point.y);
			System.out.println(">> " + point.x + " " + point.y + " "
					+ point.edge.getFirstID() + " " + point.edge.getSecondID());
			graph.unselectEdge(point.edge.getFirstID(), point.edge.getSecondID());
			vertex.unselect();
		}
	}

	private boolean validEdgeIDs(final Map<String, Integer> mapIndexes, final Edge edge) {

		return mapIndexes.containsKey(edge.getFirstID())
				&& mapIndexes.containsKey(edge.getSecondID());
	}
}
