package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.util.MenuContent;

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
				final JMenuItem mnItem = new JMenuItem(menu.getItems()[j]);
				mnItems.put(menu.getItems()[j], mnItem);
				mnButton.add(mnItem);
			}
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
	}
}
