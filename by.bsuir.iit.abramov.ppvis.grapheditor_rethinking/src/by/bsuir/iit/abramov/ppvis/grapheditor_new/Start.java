package by.bsuir.iit.abramov.ppvis.grapheditor_new;

import java.awt.EventQueue;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.BigDaddy;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Model;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.ModelInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.Window;

public class Start {
	/**
	 * @param args
	 */

	public static void main(final String[] args) {

		/*
		 * Map<String, Vertex> IDs = new HashMap<String, Vertex>(); IDs.put("0",
		 * new Vertex("id", new Point(0,0))); IDs.put("1", new Vertex("id", new
		 * Point(0,0))); IDs.put("2", new Vertex("id2", new Point(0,0)));
		 * IDs.put("3", new Vertex("id3", new Point(0,0))); IDs.put("4", new
		 * Vertex("id4", new Point(0,0))); IDs.put("5", new Vertex("id4", new
		 * Point(0,0))); IDs.put("6", new Vertex("id4", new Point(0,0)));
		 * IDs.put("7", new Vertex("id4", new Point(0,0))); IDs.put("8", new
		 * Vertex("id4", new Point(0,0)));
		 */

		final DaddyInterface daddy = new BigDaddy();

		final ModelInterface model = new Model();
		model.registerObserver(daddy);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				try {
					final Window frame = new Window();
					frame.registerObserver(daddy);
					frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
