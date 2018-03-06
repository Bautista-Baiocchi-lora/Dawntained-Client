package org.ubot.client;

import org.ubot.bot.BotModel;
import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.ui.BotToolBar;
import org.ubot.client.ui.logger.Logger;
import org.ubot.client.ui.logger.LoggerPanel;
import org.ubot.client.ui.screens.BotConfigurationScreen;
import org.ubot.util.directory.DirectoryManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

public class Client extends JFrame implements WindowListener {

	private static final double VERSION = 0.1;
	private final ClientModel model;
	private final LoggerPanel loggerPanel;
	private final BotToolBar toolBar;

	public Client(String username, String accountKey, String permissionKey) {
		super("[" + username + "] uBot v" + VERSION);
		this.model = new ClientModel(this, username, accountKey, permissionKey);
		loggerPanel = new LoggerPanel(new Logger());
		toolBar = new BotToolBar(this);
		DirectoryManager.init();
		add(toolBar, BorderLayout.NORTH);
		toolBar.addTab(new BotConfigurationScreen(this, model.getServerProviders()));
		setResizable(false);
		//getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
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

	public void showLogger() {
		add(loggerPanel, BorderLayout.SOUTH);
		refreshInterface();
	}

	private void refreshInterface() {
		pack();
		revalidate();
	}

	public void addNewTab() {
		toolBar.addTab(new BotConfigurationScreen(this, model.getServerProviders()));
	}

	public void showBotTheater() {
		displayScreen(model.getUpdatedBotTheater());
	}

	public void loadServer(ServerLoader loader) {
		model.loadServer(loader);
	}

	public void createBot(BotModel.Builder builder) {
		model.createBot(builder);
	}

	public void displayScreen(JPanel screen) {
		remove(toolBar.getCurrentTab().getContent());
		toolBar.updateCurrentTabContent(screen);
		add(screen, BorderLayout.CENTER);
		refreshInterface();
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
