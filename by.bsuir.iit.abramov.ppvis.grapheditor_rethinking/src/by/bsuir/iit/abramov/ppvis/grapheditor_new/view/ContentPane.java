package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.Daddy;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.model.Graph;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.util.Support;

public class ContentPane extends JPanel {
	private static final int	defaultWidth	= 800;
	private static final int	defaultHeigth	= 600;
	private static final String	defaultTabName	= "New Tab";
	private List<Daddy>			daddies;
	private ToolPanel			toolPanel;
	private TabbedPane			tabbedPane;

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

	public void addTab(final JComponent component, final String title) {

		if (component != null) {
			tabbedPane.addTab(title, null, component, null);
		}
	}

	public void changeTitle(final String title) {

		tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), title);
		((ExtJScrollPane) tabbedPane.getSelectedComponent()).getDesktop().save();
	}

	public void closeTab() {

		if (tabbedPane.getTabCount() != 0) {
			final DesktopInterface desktop = ((ExtJScrollPane) tabbedPane
					.getSelectedComponent()).getDesktop();
			if (desktop.isSaved()) {
				removeTab(desktop);
			} else {
				switch (saveDialog(desktop)) {
					case 1:
						saveModel();
					case 0:
						removeTab(desktop);
					break;
					default:
				}
			}
		}
	}

	public void deleteSelectedItems() {

		tabbedPane.deleteSelectedItems();

	}

	public void doAlgorithm() {

		if (tabbedPane.getTabCount() != 0) {
			notifyDaddy(tabbedPane.getSelectedIndex());
		}

	}

	public int getNextTabID() {

		return tabbedPane.getTabCount();
	}

	private void initialize() {

		setLayout(new BorderLayout(0, 0));
		setBounds(0, 0, ContentPane.defaultWidth, ContentPane.defaultHeigth);
		toolPanel = new ToolPanel(this);
		tabbedPane = new TabbedPane(this);
		daddies = new ArrayList<Daddy>();
	}

	public void newTab(final DesktopInterface desktop) {

		final Iterator<Daddy> iterator = daddies.iterator();
		while (iterator.hasNext()) {
			iterator.next().newTab(desktop);
		}
	}

	public void notifyDaddy(final int index) {

		final Iterator<Daddy> iterator = daddies.iterator();
		while (iterator.hasNext()) {
			iterator.next().doAlgorithm(index);
		}
	}

	public void openDialogNewID() {

		tabbedPane.openDialogNewID();

	}

	public void openModel() {

		System.out.println("BigDaddy - openModel()");

		final JFileChooser fileChooser = new JFileChooser();
		final int res = fileChooser.showOpenDialog(null);
		if (res == JFileChooser.APPROVE_OPTION) {
			final File file = fileChooser.getSelectedFile();

			try {
				final ObjectInputStream istream = new ObjectInputStream(
						new FileInputStream(file));

				final Graph graph = (Graph) istream.readObject();
				istream.close();
				Support.newTab(this, graph, file.getName());

			} catch (final ClassNotFoundException error) {
				error.printStackTrace();
			} catch (final FileNotFoundException error) {
				error.printStackTrace();
			} catch (final IOException error) {
				error.printStackTrace();
			}
		}
	}

	public void registerObserver(final Daddy daddy) {

		daddies.add(daddy);
	}

	public void removeDesktop(final int ID) {

		final Iterator<Daddy> iterator = daddies.iterator();
		while (iterator.hasNext()) {
			iterator.next().removeDesktop(ID);
		}

	}

	public void removeObserver(final Daddy daddy) {

		daddies.remove(daddy);

	}

	private void removeTab(final DesktopInterface desktop) {

		final List<VertexComponentInterface> vertices = desktop.getVertices();
		Iterator<VertexComponentInterface> iterator = vertices.iterator();
		while (iterator.hasNext()) {
			final VertexComponentInterface vertex = iterator.next();
			vertex.deleteEdges();
		}
		iterator = vertices.iterator();
		while (iterator.hasNext()) {
			final VertexComponentInterface vertex = iterator.next();
			desktop.removeVertex(vertex);
			desktop.notifyObserversDeleteVertex(vertex);
			iterator.remove();
		}
		desktop.removeObservers();
		removeDesktop(desktop.getID());
		tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
	}

	private int saveDialog(final DesktopInterface desktop) {

		final SaveDialog saveDialog = new SaveDialog(desktop);
		saveDialog.setModal(true);
		saveDialog.setVisible(true);
		return saveDialog.isOK();
	}

	public void saveModel() {

		if (tabbedPane.getTabCount() != 0) {
			final Iterator<Daddy> iterator = daddies.iterator();
			while (iterator.hasNext()) {
				iterator.next().saveModel(tabbedPane.getSelectedIndex());
			}
		}
	}

	public void setEditMode(final int mode) {

		if (tabbedPane.getTabCount() != 0) {
			tabbedPane.setEditMode(mode);
		}

	}

	public void showInf() {

		if (tabbedPane.getTabCount() != 0) {
			final Iterator<Daddy> iterator = daddies.iterator();
			while (iterator.hasNext()) {
				iterator.next().showInf();
			}
		}
	}

	public void unselectAll() {

		tabbedPane.unselectAll();

	}
}
