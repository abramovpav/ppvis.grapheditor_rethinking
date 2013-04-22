package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.Daddy;

public class Window extends JFrame implements KeyListener {
	public static final int		defaultX		= 300;
	public static final int		defaultY		= 100;
	public static final int		defaultWidth	= 800;
	public static final int		defaultHeight	= 600;
	public static final String	TITLE			= "GraphEditor";

	private final ContentPane	contentPane;
	private final Menu			menu;

	/**
	 * Create the frame.
	 */
	public Window() {

		System.out.println("Window()");
		setTitle(Window.TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Window.defaultX, Window.defaultY, Window.defaultWidth,
				Window.defaultHeight);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		contentPane = new ContentPane(this);
		setContentPane(contentPane);
		menu = new Menu(this);
		setJMenuBar(menu);
		addKeyListener(this);
	}

	public void changeTitle(final String title) {

		contentPane.changeTitle(title);
	}

	public void closeTab() {

		contentPane.closeTab();
	}

	private void deleteSelectedItems() {

		contentPane.deleteSelectedItems();
	}

	public void doAlgorithm() {

		contentPane.doAlgorithm();
	}

	@Override
	public final ContentPane getContentPane() {

		return contentPane;
	}

	@Override
	public void keyPressed(final KeyEvent e) {

		// 27 - escape
		// 127 - delete

		System.out.println("Window: keyPressed(" + e.getKeyCode() + ")");
		if (e.getKeyCode() == 27) { // Key "Escape"
			unselectAll();
		}
		if (e.getKeyCode() == 127) { // Key "Delete"
			deleteSelectedItems();
		}
		if (e.getKeyCode() == 73) { // Key "I"
			openDialogNewID();
		}
	}

	@Override
	public void keyReleased(final KeyEvent e) {/* Unreleased */

	}

	@Override
	public void keyTyped(final KeyEvent e) {/* Unreleased */

	}

	public void lockMenu() {

		menu.lock();
	}

	private void openDialogNewID() {

		contentPane.openDialogNewID();
	}

	public void openFile() {

		final JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			final File file = fileChooser.getSelectedFile();
			System.out.println(file);
		}
	}

	public void openModel() {

		contentPane.openModel();
	}

	public void registerObserver(final Daddy daddy) {

		contentPane.registerObserver(daddy);
	}

	public void saveModel() {

		contentPane.saveModel();
	}

	public void showInf() {

		contentPane.showInf();
	}

	public void unlockMenu() {

		menu.unlock();
	}

	private void unselectAll() {

		contentPane.unselectAll();
	}

}
