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
	private JMenuItem interfaceExplorer = new JMenuItem("Interface Explorer");
	private JCheckBoxMenuItem showLogger = new JCheckBoxMenuItem("Show Logger");
	private JMenuItem exit = new JMenuItem("Exit");
	private ArrayList<BotTab> tabs;
	private BotTab currentTab;

	public BotToolBar(Client client) {
		this.client = client;
		tabs = new ArrayList<>();
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

		interfaceExplorer.addActionListener(e -> client.openInterfaceExplorer());
		interfaceExplorer.setEnabled(false);

		debugs.setEnabled(false);
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
		newTabButton.addActionListener(e -> client.openNewBot());

		configureComponents();
	}

	public void updateTabs(ArrayList<Bot> bots, Bot focus) {
		tabs.clear();
		for (Bot bot : bots) {
			final BotTab tab = new BotTab(bot);
			tabs.add(tab);
			if (focus == null && currentTab == null) {
				currentTab = tab;
			} else if (bot.equals(focus)) {
				currentTab = tab;
			}
			tab.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					currentTab = tab;
					client.displayScreen(currentTab.getBot().getView());
					debugs.setEnabled(currentTab.getBot().canDebug());
				}
			});
			tab.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(final MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e)) {
						tab.showOptionsMenu(e);
					}
				}
			});
		}
		configureComponents();
	}

	public void disableDebugging() {
		this.debugs.setEnabled(false);
	}

	private void addTabs() {
		for (BotTab tab : tabs) {
			add(tab);
			addSeparator();
		}
	}

	public BotTab getCurrentTab() {
		return currentTab;
	}

	private void addDebugComponents() {
		this.debugs.removeAll();
		if (currentTab != null) {
			if (currentTab.getBot().canDebug()) {
				for (JCheckBoxMenuItem debugItem : currentTab.getBot().getScreenOverlays()) {
					this.debugs.add(debugItem);
				}
				this.debugs.addSeparator();
				this.debugs.add(interfaceExplorer);
				this.debugs.setEnabled(true);
				return;
			}
			this.debugs.setEnabled(false);
		}
	}

	private void configureComponents() {
		removeAll();
		addTabs();
		add(newTabButton);
		add(Box.createHorizontalGlue());
		add(startScript);
		add(pauseScript);
		add(stopScript);
		add(theaterMode);
		addDebugComponents();
		add(settingsButton);
		client.refreshInterface();
	}

	public final class BotTab extends JButton {

		private final Bot bot;
		private final JPopupMenu optionsMenu;
		private final JMenuItem rename, close;

		public BotTab(Bot bot) {
			super(bot.getBotName());
			this.bot = bot;
			this.optionsMenu = new JPopupMenu("Options");
			this.optionsMenu.add(rename = new JMenuItem("Rename"));
			this.rename.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					String newName = JOptionPane.showInputDialog(null, "Name?");
					renameBot(newName.isEmpty() ? bot.getBotName() : newName);
				}
			});
			this.optionsMenu.add(close = new JMenuItem("Close"));
			this.close.addActionListener(e -> client.closeBot(bot));
		}

		public void renameBot(String name) {
			this.bot.setName(name);
			this.setText(name);
			revalidate();
		}

		public void showOptionsMenu(MouseEvent e) {
			optionsMenu.show(e.getComponent(), e.getX(), e.getY());
		}

		public Bot getBot() {
			return bot;
		}
	}

}
