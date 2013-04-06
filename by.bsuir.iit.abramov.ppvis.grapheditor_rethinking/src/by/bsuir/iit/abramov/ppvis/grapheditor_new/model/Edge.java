package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

public class Edge {
	private final String	firstID;
	private final String	secondID;

	public Edge(final String firstID, final String secondID) {

		this.firstID = firstID;
		this.secondID = secondID;
	}

	public String getFirstID() {

		return firstID;
	}

	public String getSecondID() {

		return secondID;
	}
}
