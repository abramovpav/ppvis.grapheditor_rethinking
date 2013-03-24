package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.MenuBar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.util.MenuContent;

public class Menu extends JMenuBar {
	private Map<MenuContent, JMenu> mnButtons;
	private Map<String, JMenuItem> mnItems;
	public Menu() {
		super();
		System.out.println("Menu()");
		initialize();
	}
	private void initialize()
	{		
		mnButtons = new HashMap<MenuContent, JMenu>();
		mnItems = new HashMap<String, JMenuItem>();
		for(MenuContent menu : MenuContent.values()) {
			JMenu mnButton = new JMenu(menu.getSection());
			mnButtons.put(menu, mnButton);
			add(mnButton);
			for (int j = 0; j < menu.getItems().length; ++j) {
				JMenuItem mnItem = new JMenuItem(menu.getItems()[j]);
				mnItems.put(menu.getItems()[j], mnItem);
				mnButton.add(mnItem);
			}
		}
	}
}
