package by.bsuir.iit.abramov.ppvis.grapheditor_new.controller;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Model;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.DesktopInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.Window;

public class Daddy {
	private Model						model;
	private final List<DesktopObserver>	graphs;
	private Window						window;

	public Daddy() {

		graphs = new ArrayList<DesktopObserver>();
	}

	public void changeTitle(final String title) {

		window.changeTitle(title);

	}

	public void doAlgorithm(final int index) {

		model.doAlgorithm(index);

	}

	public Model getModel() {

		return model;
	}

	public void newTab(final DesktopInterface desktop) {

		final DesktopObserver desktopObserver = new DesktopObserver();
		(model.newGraph(desktop.getID())).registerObserver(desktopObserver);
		desktop.registerObserver(desktopObserver);
		graphs.add(desktopObserver);
	}

	public void removeDesktop(final int ID) {

		model.removeGraph(ID);
	}

	public void saveModel(final int index) {

		model.save(index);

	}

	public void setModel(final Model model) {

		this.model = model;

	}

	public void setWindow(final Window window) {

		this.window = window;
	}

	public void showInf() {

		model.showInf();
	}

}
