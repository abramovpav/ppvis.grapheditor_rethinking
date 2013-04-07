package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.io.Serializable;

public class Edge implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private String				firstID;
	private String				secondID;
	private int					weight				= 0;

	public Edge(final int weight, final String firstID, final String secondID) {

		this.weight = weight;
		this.firstID = firstID;
		this.secondID = secondID;
	}

	public final String getFirstID() {

		return firstID;
	}

	public final String getSecondID() {

		return secondID;
	}

	public int getWeight() {

		return weight;
	}

	public void setFirstID(final String ID) {

		firstID = ID;
	}

	public void setSecondID(final String ID) {

		secondID = ID;
	}

	public void setWeight(final int weight) {

		this.weight = weight;
	}
}
