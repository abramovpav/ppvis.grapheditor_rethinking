package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.ExtJButtonListener;
import by.bsuir.iit.abramov.ppvis.grapheditor_new.controller.NewTabListener;

public class ToolPanel extends JPanel {
	private final JPanel					parent;
	public static final String				TAB		= "New Tab";
	public static final String				VERTEX	= "Vertex";
	public static final String				EDGE	= "Edge";
	private final Map<String, ExtJButton>	buttons;

	public ToolPanel(final JPanel parent) {

		super();
		buttons = new HashMap<String, ExtJButton>();
		System.out.println("ToolPanel()");
		this.parent = parent;
		initialize();
		createComponents();
	}

	public void blockInterface() {

		for (final ExtJButton button : buttons.values()) {
			button.setEnabled(false);
		}
	}

	private void createComponents() {

		final JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		add(toolBar);
		// buttons
		ExtJButton button = new ExtJButton(ToolPanel.TAB, this);
		buttons.put(ToolPanel.TAB, button);
		toolBar.add(button);
		button.addActionListener(new NewTabListener());
		button = new ExtJButton(ToolPanel.VERTEX, this);
		buttons.put(ToolPanel.VERTEX, button);
		final ExtJButtonListener listener = new ExtJButtonListener();
		button.addActionListener(listener);
		toolBar.add(button);
		button = new ExtJButton(ToolPanel.EDGE, this);
		buttons.put(ToolPanel.EDGE, button);
		button.addActionListener(listener);
		toolBar.add(button);
	}

	@Override
	public final JPanel getParent() {

		return parent;
	}

	private void initialize() {

		parent.add(this, BorderLayout.EAST);
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		setLayout(new BorderLayout(0, 0));
	}

	public void unlockInterface() {

		for (final ExtJButton button : buttons.values()) {
			button.setEnabled(false);
		}
	}
}
