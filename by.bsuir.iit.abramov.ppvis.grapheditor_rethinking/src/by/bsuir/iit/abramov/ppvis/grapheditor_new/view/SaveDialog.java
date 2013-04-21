package by.bsuir.iit.abramov.ppvis.grapheditor_new.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SaveDialog extends JDialog {
	public static final String	TITLE			= "Saving state";
	public static final int		defaultX		= 500;
	public static final int		defaultY		= 400;
	public static final int		defaultWidth	= 300;
	public static final int		defaultHeight	= 100;
	private JPanel				contentPane		= null;
	private DesktopInterface	desktop			= null;
	private int					status			= -1;

	public SaveDialog(final DesktopInterface desktop) {

		this.desktop = desktop;
		System.out.println("SaveDialog()");
		setTitle(SaveDialog.TITLE);
		setBounds(SaveDialog.defaultX, SaveDialog.defaultY, SaveDialog.defaultWidth,
				SaveDialog.defaultHeight);
		contentPane = new JPanel();
		setContentPane(contentPane);
		setLayout(new BorderLayout());
		initialize();
	}

	private void initialize() {

		final JLabel label = new JLabel("Save desktop?");
		contentPane.add(label, BorderLayout.CENTER);
		JButton button = new JButton("OK");
		final JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.add(button);
		final SaveDialog obj = this;
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				status = 1;
				obj.setVisible(false);
				obj.dispose();

			}
		});

		button = new JButton("No");
		panel.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				status = 0;
				obj.setVisible(false);
				obj.dispose();

			}
		});

		button = new JButton("Cancel");
		panel.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				status = -1;
				obj.setVisible(false);
				obj.dispose();

			}
		});
	}

	public int isOK() {

		return status;
	}
}
