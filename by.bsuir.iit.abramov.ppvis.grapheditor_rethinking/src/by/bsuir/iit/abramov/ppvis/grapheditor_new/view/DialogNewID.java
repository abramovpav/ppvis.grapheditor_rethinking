package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogNewID extends JDialog {
	public static final String	TITLE			= "Change ID";
	public static final int		defaultX		= 500;
	public static final int		defaultY		= 400;
	public static final int		defaultWidth	= 300;
	public static final int		defaultHeight	= 100;
	private final JPanel		contentPane;
	private JTextField			textField;
	private final Desktop		desktop;
	private boolean				status			= false;

	public DialogNewID(final Desktop desktop, final int mode, final String text) {

		this.desktop = desktop;
		System.out.println("DialogNewID()");
		setTitle(DialogNewID.TITLE);
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(DialogNewID.defaultX, DialogNewID.defaultY, DialogNewID.defaultWidth,
				DialogNewID.defaultHeight);
		contentPane = new JPanel();
		setContentPane(contentPane);
		setLayout(new BorderLayout());
		initialize(mode, text);
	}

	public final String getText() {

		return textField.getText();

	}

	private void initialize(final int mode, final String text) {

		textField = new JTextField(text);
		contentPane.add(textField, BorderLayout.CENTER);
		final JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		contentPane.add(panel, BorderLayout.SOUTH);
		JButton button = new JButton("OK");
		panel.add(button);
		final DialogNewID obj = this;
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				status = true;
				obj.setVisible(false);
				obj.dispose();

			}

		});
		button = new JButton("Cancel");
		panel.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				status = false;
				obj.setVisible(false);
				obj.dispose();

			}

		});

	}

	public boolean isOK() {

		return status;
	}
}
