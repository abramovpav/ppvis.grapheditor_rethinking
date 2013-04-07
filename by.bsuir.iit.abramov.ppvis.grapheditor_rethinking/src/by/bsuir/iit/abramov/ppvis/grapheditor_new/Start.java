package by.bsuir.iit.abramov.ppvis.grapheditor_new;

import java.awt.EventQueue;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.Daddy;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Model;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.Window;

public class Start {
	/**
	 * @param args
	 */

	public static void main(final String[] args) {


		final Daddy daddy = new Daddy();

		final Model model = new Model();
		model.registerObserver(daddy);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				try {
					final Window frame = new Window();
					daddy.setWindow(frame);
					frame.registerObserver(daddy);
					frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
