package org.ubot.client.ui;

import org.ubot.bot.Bot;
import org.ubot.client.Client;
import org.ubot.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Ethan on 3/4/2018.
 */
public class BotToolBar extends JToolBar {

	private Client client;
	private JButton newTabButton = new JButton();
	private JButton settingsButton = new JButton();
	private JButton theaterMode = new JButton();
	private JButton startScript = new JButton();
	private JButton pauseScript = new JButton();
	private JButton stopScript = new JButton();
	private JPopupMenu settings = new JPopupMenu("Settings");
	private JMenu debugs = new JMenu("Debugs");
	private JMenuItem debugInventory = new JMenuItem("Inventory");
	private JMenuItem debugNPCS = new JMenuItem("NPCs");
	private JMenuItem debugObjects = new JMenuItem("Objects");
	private JMenuItem debugPlayers = new JMenuItem("Players");
	private JMenuItem debugGameInfo = new JMenuItem("Information");
	private JMenuItem interfaceExplorer = new JMenuItem("Interface Explorer");
	private JCheckBoxMenuItem showLogger = new JCheckBoxMenuItem("Show Logger");
	private JMenuItem exit = new JMenuItem("Exit");

	public BotToolBar(Client client) {
		this.client = client;
		configure();
	}

	private final void configure() {
		setPreferredSize(new Dimension(765, 24));
		setFloatable(false);

		startScript.setIcon(Utilities.getIcon("resources/buttons/play.png"));
		startScript.setContentAreaFilled(false);
		startScript.setRolloverEnabled(true);
		startScript.setBorder(null);
		startScript.setRolloverIcon(Utilities.getIcon("resources/buttons/play_hover.png"));
		startScript.addActionListener(e -> client.openScriptSelector());

		stopScript.setIcon(Utilities.getIcon("resources/buttons/stop.png"));
		stopScript.setContentAreaFilled(false);
		stopScript.setRolloverEnabled(true);
		stopScript.setBorder(null);
		stopScript.setRolloverIcon(Utilities.getIcon("resources/buttons/stop_hover.png"));
		stopScript.addActionListener(e -> client.openScriptSelector());

		pauseScript.setIcon(Utilities.getIcon("resources/buttons/pause.png"));
		pauseScript.setContentAreaFilled(false);
		pauseScript.setRolloverEnabled(true);
		pauseScript.setBorder(null);
		pauseScript.setRolloverIcon(Utilities.getIcon("resources/buttons/pause_hover.png"));
		pauseScript.addActionListener(e -> client.openScriptSelector());

		theaterMode.setIcon(Utilities.getIcon("resources/theater_mode.png"));
		theaterMode.setContentAreaFilled(false);
		theaterMode.setRolloverEnabled(true);
		theaterMode.setBorder(null);
		theaterMode.setRolloverIcon(Utilities.getIcon("resources/theater_mode_hover.png"));
		theaterMode.addActionListener(e -> client.toggleBotTheater());

		debugs.add(debugNPCS);
		debugs.add(debugPlayers);
		debugs.add(debugObjects);
		debugs.add(debugGameInfo);
		debugs.add(debugInventory);
		debugs.add(interfaceExplorer);

		settings.add(debugs);
		settings.addSeparator();
		settings.add(showLogger);
		showLogger.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (showLogger.isSelected()) {
					client.showLogger();
				} else {
					client.hideLogger();
				}
			}
		});
		settings.add(exit);
		exit.addActionListener(e -> System.exit(0));

		settingsButton.setIcon(Utilities.getIcon("resources/buttons/settings.png"));
		settingsButton.setContentAreaFilled(false);
		settingsButton.setRolloverEnabled(true);
		settingsButton.setBorder(null);
		settingsButton.setRolloverIcon(Utilities.getIcon("resources/buttons/settings_hover.png"));
		settingsButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				settings.show(e.getComponent(), e.getX(), e.getY());
			}
		});

		newTabButton.setIcon(Utilities.getIcon("resources/icon_plus_small.png"));
		newTabButton.setContentAreaFilled(false);
		newTabButton.setRolloverEnabled(true);
		newTabButton.setBorder(null);
		newTabButton.setRolloverIcon(Utilities.getIcon("resources/icon_plus_small_highlighted.png"));
		newTabButton.addActionListener(e -> client.tabOpenRequest());

		addComponents();
	}

	public void updateTabs(ArrayList<Bot> bots) {
		removeAll();
		for (Bot bot : bots) {
			final BotTab tab = new BotTab(bot);
			tab.addActionListener(e -> client.displayScreen(tab.getBot()));
			add(tab);
			addSeparator();
		}
		addComponents();
	}


	private void addComponents() {
		add(newTabButton);
		add(Box.createHorizontalGlue());
		add(startScript);
		add(pauseScript);
		add(stopScript);
		add(theaterMode);
		add(settingsButton);
		client.refreshInterface();
	}

	public final class BotTab extends JButton {

		private final Bot bot;

		public BotTab(Bot bot) {
			super(bot.getName());
			this.bot = bot;
		}

		public Bot getBot() {
			return bot;
		}
	}

}
