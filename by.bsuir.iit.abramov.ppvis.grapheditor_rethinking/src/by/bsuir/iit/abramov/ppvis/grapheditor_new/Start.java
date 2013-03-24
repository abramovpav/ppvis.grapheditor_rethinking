package by.bsuir.iit.abramov.ppvis.grapheditor_new;

import java.awt.EventQueue;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.BigDaddy;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Graph;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.GraphInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Model;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.ModelInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Vertex;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.Window;

public class Start {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		final DaddyInterface daddy = new BigDaddy();
		
		final ModelInterface model = new Model();
		model.registerObserver(daddy);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.registerObserver(daddy);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
