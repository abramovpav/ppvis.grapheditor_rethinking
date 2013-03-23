package by.bsuir.iit.abramov.ppvis.grapheditor_new;

import java.awt.EventQueue;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Graph;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.GraphInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Vertex;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.Window;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphInterface graph = new Graph(0);
		graph.addVertex("0", new Point(0,0));
		if (graph.addVertex("0", new Point(0,0)) == false)
			System.out.println("already exist");
		graph.addVertex("2", new Point(0,0));
		graph.addVertex("3", new Point(0,0));
		List<Vertex> list = graph.getVertices();
		System.out.println(list.size());
		Iterator it = list.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
		System.out.println("hello");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
