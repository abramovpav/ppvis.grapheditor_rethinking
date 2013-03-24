package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument.Content;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.ModelInterface;

public class ContentPane extends JPanel implements ContentPaneInterface{
	private static final int defaultWidth = 800;
	private static final int defaultHeigth = 600;
	private static final String defaultTabName = "New Tab";
	private List<DaddyInterface> daddies;
	private ToolPanel toolPanel;
	private TabbedPane tabbedPane;
	public ContentPane() {
		super();
		System.out.println("ContentPane()");
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0,0));
		setBounds(0,0, ContentPane.defaultWidth, ContentPane.defaultHeigth);
		this.toolPanel = new ToolPanel(this);
		this.tabbedPane = new TabbedPane(this);
		daddies = new ArrayList<DaddyInterface>();
	}
	
	public void addTab(JComponent component) {
		if (component != null)
			tabbedPane.addTab(ContentPane.defaultTabName, null, component, null);
	}
	
	public int getNextTabID() {
		return tabbedPane.getTabCount();
	}
	
	@Override
	public void registerObserver(DaddyInterface daddy) {
		daddies.add(daddy);
	}

	@Override
	public void removeObserver(DaddyInterface daddy) {
		daddies.remove(daddy);

	}

	@Override
	public void notifyDaddy() {
		Iterator<DaddyInterface> iterator = daddies.iterator();
		while(iterator.hasNext()) {
			iterator.next().windowUpdate();
		}
		
	}

	@Override
	public void newTab(DesktopInterface desktop) {
		Iterator<DaddyInterface> iterator = daddies.iterator();
		while(iterator.hasNext()) {
			iterator.next().newTab(desktop);
		}		
	}
}
