package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.DaddyInterface;

public class Window extends JFrame implements KeyListener {
	public static final int		defaultX		= 300;
	public static final int		defaultY		= 100;
	public static final int		defaultWidth	= 800;
	public static final int		defaultHeight	= 600;
	public static final String	TITLE			= "GraphEditor";

	private final ContentPane	contentPane;

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
		contentPane = new ContentPane();
		setContentPane(contentPane);
		final Menu menu = new Menu(this);
		setJMenuBar(menu);
		addKeyListener(this);
	}

	private void deleteSelectedItems() {

		contentPane.deleteSelectedItems();
	}

	public void doAlgorithm() {

		contentPane.doAlgorithm();
	}

	@Override
	public void keyPressed(final KeyEvent e) {

		// 27 - escape
		// 127 - delete

		System.out.println("Window: keyPressed(" + e.getKeyCode() + ")");
		if (e.getKeyCode() == 27) // Key "Escape"
		{
			unselectAll();
		}
		if (e.getKeyCode() == 127) // Key "Delete"
		{
			deleteSelectedItems();
		}
	}

	@Override
	public void keyReleased(final KeyEvent e) {

		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(final KeyEvent e) {

	}

	public void registerObserver(final DaddyInterface daddy) {

		contentPane.registerObserver(daddy);
	}

	private void unselectAll() {

		contentPane.unselectAll();
	}

}
