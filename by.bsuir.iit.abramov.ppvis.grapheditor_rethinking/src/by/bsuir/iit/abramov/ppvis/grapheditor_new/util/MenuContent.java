package by.bsuir.iit.abramov.ppvis.grapheditor_new.util;

public enum MenuContent {
	File("File", "New", "Open", "Save", "Save as", "Close", "Exit"),
	Edit("Edit", "SelectAll", "UnSeclectAll"),
	Instruments("Instruments", "Node", "Edge");
	
	private String section;
	private String[] items;
	private MenuContent(String section, String... items) {
		this.section = section;
		this.items = items;
	}
	
	public String getSection() {
		return this.section;
	}
	
	public String[] getItems() {
		return this.items;
	}
}
