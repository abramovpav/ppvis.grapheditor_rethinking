package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.InstrumentsActionListener;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.util.ExtJMenuItem;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.util.MenuContent;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.util.Support;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.ContentPane;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.view.Window;

public class Menu extends JMenuBar {
	private Map<MenuContent, JMenu>	mnButtons;
	private Map<String, JMenuItem>	mnItems;
	private final JFrame			parent;

	public Menu(final JFrame parent) {

		super();
		this.parent = parent;
		System.out.println("Menu()");
		initialize();
		setActions();
	}

	private void initialize() {

		mnButtons = new HashMap<MenuContent, JMenu>();
		mnItems = new HashMap<String, JMenuItem>();
		for (final MenuContent menu : MenuContent.values()) {
			final JMenu mnButton = new JMenu(menu.getSection());
			mnButtons.put(menu, mnButton);
			add(mnButton);
			for (int j = 0; j < menu.getItems().length; ++j) {
				final JMenuItem mnItem = new ExtJMenuItem(menu.getItems()[j], parent);
				mnItems.put(menu.getItems()[j], mnItem);
				mnButton.add(mnItem);
			}
		}
	}

	public void lock() {

		for (final JMenuItem item : mnItems.values()) {
			item.setEnabled(false);
		}
	}

	public void setActionListener(final String name, final ActionListener listener) {

		final JMenuItem mnItem = mnItems.get(name);
		if (mnItem != null) {
			mnItem.addActionListener(listener);
		}
	}

	private void setActions() {

		final JMenuItem item = mnItems.get("DoAlgorithm");
		if (item != null) {
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {

					((Window) parent).doAlgorithm();

				}

			});
		}

		setActionListener("New", new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				Support.newTab((ContentPane) parent.getContentPane());

			}

		});

		setActionListener("Vertex", new InstrumentsActionListener());

		setActionListener("Edge", new InstrumentsActionListener());

		setActionListener("Close", new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				((Window) parent).closeTab();
			}

		});

		setActionListener("Open...", new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				((Window) parent).openModel();
			}

		});

		setActionListener("Save as...", new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				((Window) parent).saveModel();
			}

		});

		setActionListener("get Information", new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				((Window) parent).showInf();
			}

		});

		setActionListener("Exit", new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				((Window) parent).setVisible(false);
				((Window) parent).dispose();
			}

		});

	}

	public void unlock() {

		for (final JMenuItem item : mnItems.values()) {
			item.setEnabled(true);
		}
	}
}
