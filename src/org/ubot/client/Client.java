package org.ubot.client;

import org.ubot.bot.Bot;
import org.ubot.client.ui.BotToolBar;
import org.ubot.client.ui.logger.Logger;
import org.ubot.client.ui.logger.LoggerPanel;
import org.ubot.client.ui.screens.theater.BotTheaterScreen;
import org.ubot.util.directory.DirectoryManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

public class Client extends JFrame implements WindowListener {

	private static final double VERSION = 0.1;
	private final ClientModel model;
	private final LoggerPanel loggerPanel;
	private final BotToolBar toolBar;
	private JPanel currentScreen;

	public Client(String username, String accountKey, String permissionKey) {
		super("[" + username + "] uBot v" + VERSION);
		DirectoryManager.init();
		this.model = new ClientModel(this, username, accountKey, permissionKey);
		loggerPanel = new LoggerPanel(new Logger());
		toolBar = new BotToolBar(this);
		toolBar.updateTabs(model.getBots());
		add(toolBar, BorderLayout.NORTH);
		setResizable(false);
		//getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		tabOpenRequest();
		pack();
		setLocationRelativeTo(getParent());
		setLocationRelativeTo(getOwner());
		setVisible(true);
		Logger.log("Client launched.");
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
		new Client(args[0], args[1], args[2]);
	}

	public void hideLogger() {
		remove(loggerPanel);
		refreshInterface();
	}

	public void toggleBotTheater() {
		if (currentScreen instanceof BotTheaterScreen) {
			displayScreen(model.getBots().get(0));
			return;
		}
		displayScreen(model.getBotTheaterScreen());
	}

	public void showLogger() {
		add(loggerPanel, BorderLayout.SOUTH);
		refreshInterface();
	}

	public void refreshInterface() {
		pack();
		revalidate();
	}

	public void tabOpenRequest() {
		final Bot bot = model.createBot();
		toolBar.updateTabs(model.getBots());
		displayScreen(bot);
	}

	public void displayScreen(JPanel screen) {
		if (currentScreen == null) {
			currentScreen = screen;
		} else {
			remove(currentScreen);
		}
		add(currentScreen = screen, BorderLayout.CENTER);
		refreshInterface();
	}

	public void displayScreen(Bot bot) {
		displayScreen(bot.getView());
	}

	@Override
	public void windowOpened(final java.awt.event.WindowEvent e) {

	}

	@Override
	public void windowClosing(final java.awt.event.WindowEvent e) {
		int result = JOptionPane.showConfirmDialog(new JLabel("", JLabel.CENTER),
				"Are you sure you wish to close uBot?");
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	@Override
	public void windowClosed(final java.awt.event.WindowEvent e) {

	}

	@Override
	public void windowIconified(final java.awt.event.WindowEvent e) {

	}

	@Override
	public void windowDeiconified(final java.awt.event.WindowEvent e) {

	}

	@Override
	public void windowActivated(final java.awt.event.WindowEvent e) {

	}

	@Override
	public void windowDeactivated(final java.awt.event.WindowEvent e) {

	}
}
