package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

public class Edge {
	private String firstID;
	private String secondID;
	public Edge(String firstID, String secondID) {
		this.firstID = firstID;
		this.secondID = secondID;
	}
	
	public String getFirstID() {
		return this.firstID;
	}
	
	public String getSecondID() {
		return this.secondID;
	}
}
