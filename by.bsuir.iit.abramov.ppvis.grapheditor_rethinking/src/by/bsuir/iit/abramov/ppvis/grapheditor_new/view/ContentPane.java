package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;

public class ContentPane extends JPanel implements ContentPaneInterface {
	private static final int		defaultWidth	= 800;
	private static final int		defaultHeigth	= 600;
	private static final String		defaultTabName	= "New Tab";
	private List<DaddyInterface>	daddies;
	private ToolPanel				toolPanel;
	private TabbedPane				tabbedPane;

	public ContentPane() {

		super();
		System.out.println("ContentPane()");
		initialize();
	}

	public void addTab(final JComponent component) {

		if (component != null) {
			tabbedPane.addTab(ContentPane.defaultTabName, null, component, null);
		}
	}

	@Override
	public void deleteSelectedItems() {

		tabbedPane.deleteSelectedItems();

	}

	@Override
	public void doAlgorithm() {

		notifyDaddy(tabbedPane.getSelectedIndex());

	}

	public int getNextTabID() {

		return tabbedPane.getTabCount();
	}

	private void initialize() {

		setLayout(new BorderLayout(0, 0));
		setBounds(0, 0, ContentPane.defaultWidth, ContentPane.defaultHeigth);
		toolPanel = new ToolPanel(this);
		tabbedPane = new TabbedPane(this);
		daddies = new ArrayList<DaddyInterface>();
	}

	@Override
	public void newTab(final DesktopInterface desktop) {

		final Iterator<DaddyInterface> iterator = daddies.iterator();
		while (iterator.hasNext()) {
			iterator.next().newTab(desktop);
		}
	}

	@Override
	public void notifyDaddy(final int index) {

		final Iterator<DaddyInterface> iterator = daddies.iterator();
		while (iterator.hasNext()) {
			iterator.next().doAlgorithm(index);
		}

	}

	@Override
	public void registerObserver(final DaddyInterface daddy) {

		daddies.add(daddy);
	}

	@Override
	public void removeObserver(final DaddyInterface daddy) {

		daddies.remove(daddy);

	}

	@Override
	public void setEditMode(final int mode) {

		tabbedPane.setEditMode(mode);

	}

	@Override
	public void unselectAll() {

		tabbedPane.unselectAll();

	}
}
