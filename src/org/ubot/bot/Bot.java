package org.ubot.bot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Bot extends JFrame implements WindowListener {

	private static BotModel model;

	public Bot(BotModel.Builder builder) {
		model = builder.build();
		setTitle("[" + model.getuBotName() + "] " + model.getServer());
		//setJMenuBar(new SettingsMenu(model));
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(model.getPanel(), BorderLayout.CENTER);
		//buttonPanel = new ButtonPanel(this);
		//getContentPane().add(buttonPanel, BorderLayout.NORTH);
		//final LoggerPanel loggerPanel = new LoggerPanel();
		//loggerPanel.setPreferredSize(new Dimension(model.getComponent().getWidth(), 150));
		//getContentPane().add(loggerPanel, BorderLayout.SOUTH);
		addWindowListener(this);
		setLocationRelativeTo(getParent());
		pack();
		setLocationRelativeTo(getOwner());

		setVisible(true);
	}

	public static String getuBotName() {
		return model.getuBotName();
	}

	@Override
	public void windowOpened(final WindowEvent e) {

	}

	@Override
	public void windowClosing(final WindowEvent e) {
		int result = JOptionPane.showConfirmDialog(new JLabel("", JLabel.CENTER),
				"Are you sure you wish to close uBot?");
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	@Override
	public void windowClosed(final WindowEvent e) {

	}

	@Override
	public void windowIconified(final WindowEvent e) {

	}

	@Override
	public void windowDeiconified(final WindowEvent e) {

	}

	@Override
	public void windowActivated(final WindowEvent e) {

	}

	@Override
	public void windowDeactivated(final WindowEvent e) {

	}
}
