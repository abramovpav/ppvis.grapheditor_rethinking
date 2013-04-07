package by.bsuir.iit.abramov.ppvis.grapheditor_new.util;

public enum MenuContent {
	File("File", "New", "Open...", "Save as...", "Close", "Exit"), Instruments(
			"Instruments", "Vertex", "Edge", "DoAlgorithm"), INFO("Info",
			"get Information");

	private String		section;
	private String[]	items;

	private MenuContent(final String section, final String... items) {

		this.section = section;
		this.items = items;
	}

	public String[] getItems() {

		return items;
	}

	public String getSection() {

		return section;
	}
}
