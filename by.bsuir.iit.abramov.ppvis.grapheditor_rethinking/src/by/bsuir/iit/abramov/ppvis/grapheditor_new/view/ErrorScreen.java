package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ErrorScreen extends JDialog {

	public static final String	TITLE			= "Error";
	public static final int		defaultX		= 500;
	public static final int		defaultY		= 400;
	public static final int		defaultWidth	= 300;
	public static final int		defaultHeight	= 100;
	private final String		text;
	private final JPanel		contentPane;

	public ErrorScreen(final String text) {

		this.text = text;
		System.out.println("ErrorScreen()");
		setTitle(ErrorScreen.TITLE);
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(ErrorScreen.defaultX, ErrorScreen.defaultY, ErrorScreen.defaultWidth,
				ErrorScreen.defaultHeight);
		contentPane = new JPanel();
		setContentPane(contentPane);
		setLayout(new BorderLayout());
		initialize();
	}

	public void initialize() {

		final JLabel label = new JLabel(text);
		contentPane.add(label, BorderLayout.CENTER);
		final JButton button = new JButton("OK");
		contentPane.add(button, BorderLayout.SOUTH);
		final ErrorScreen obj = this;
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				obj.setVisible(false);
				obj.dispose();

			}

		});
	}
}
