package org.ubot.client;

import org.ubot.bot.BotModel;
import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.ui.BotConfigurationScreen;
import org.ubot.util.directory.DirectoryManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

public class Client extends JFrame implements WindowListener {

	private static final double VERSION = 0.1;
	private final ClientModel model;
	private JPanel currentScreen;

	public Client(String username, String accountKey, String permissionKey) {
		super("[" + username + "] uBot v" + VERSION);
		this.model = new ClientModel(this, username, accountKey, permissionKey);
		DirectoryManager.init();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
		setPreferredSize(new Dimension(765, 503));
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setLocationRelativeTo(getParent());
		pack();
		setLocationRelativeTo(getOwner());
		displayScreen(new BotConfigurationScreen(this, model.getServerProviders()));
		setVisible(true);
		System.out.println("Client launched.");
	}

	public static void main(String[] args) {
		new Client(args[0], args[1], args[2]);
	}

	public void loadServer(ServerLoader loader) {
		model.loadServer(loader);
	}

	public void createBot(BotModel.Builder builder) {
		model.createBot(builder);
	}

	protected void displayScreen(JPanel screen) {
		if (currentScreen != null) {
			remove(currentScreen);
		}
		currentScreen = screen;
		add(currentScreen);
		revalidate();
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
