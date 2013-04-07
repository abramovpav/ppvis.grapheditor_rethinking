package by.bsuir.iit.abramov.ppvis.grapheditor_new.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.Daddy;

public class Model {
	private final List<Graph>	graphs;
	private final List<Daddy>	daddies;

	public Model() {

		super();
		graphs = new ArrayList<Graph>();
		daddies = new ArrayList<Daddy>();
	}

	public void doAlgorithm(final int index) {

		final Iterator<Graph> iterator = graphs.iterator();
		while (iterator.hasNext()) {
			final Graph graph = iterator.next();
			if (graph.getID() == index) {
				graph.doAlgorithm();
			}
		}
	}

	public Graph newGraph(final int ID) {

		final Graph graph = new Graph(ID);
		graphs.add(graph);
		return graph;

	}

	public void notifyDaddy(final String title) {

		final Iterator<Daddy> iterator = daddies.iterator();
		while (iterator.hasNext()) {
			iterator.next().changeTitle(title);
		}

	}

	public void registerObserver(final Daddy daddy) {

		daddies.add(daddy);
		daddy.setModel(this);
	}

	public void removeGraph(final int ID) {
		Iterator<Graph> iterator = graphs.iterator();
		while(iterator.hasNext()) {
			Graph graph = iterator.next(); 
			if (graph.getID() == ID)
			{
				graph.devastation();
				iterator.remove();
			}
		}
	}

	public void removeObserver(final Daddy daddy) {

		daddies.remove(daddy);

	}

	public void save(final int index) {

		System.out.println("Model - save(" + index + ")");
		final JFileChooser fn = new JFileChooser();
		final int ret = fn.showSaveDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			final File file = fn.getSelectedFile();
			try {
				final ObjectOutputStream ostream = new ObjectOutputStream(
						new FileOutputStream(file));
				ostream.writeObject(graphs.get(index));
				ostream.close();
				notifyDaddy(file.getName());
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void showInf() {

		final Iterator<Graph> iterator = graphs.iterator();
		while (iterator.hasNext()) {
			final Graph graph = iterator.next();
			System.out.println(graph.getID());
			System.out.println(graph.getVertices().size());
			System.out.println(graph.getEdges().size());

		}
	}
}
