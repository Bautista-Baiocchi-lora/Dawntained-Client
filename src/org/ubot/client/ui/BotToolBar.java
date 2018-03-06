package org.ubot.client.ui;

import org.ubot.client.Client;
import org.ubot.client.ui.logger.Logger;
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
	private JButton newTabButton = new JButton("+");
	private JButton settingsButton = new JButton();
	private JButton theaterMode = new JButton();
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
	private final ArrayList<BotTab> tabs = new ArrayList<>();
	private BotTab currentTab;

	public BotToolBar(Client client) {
		this.client = client;
		configure();
	}

	private final void configure() {
		setPreferredSize(new Dimension(765, 24));
		setFloatable(false);

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

		theaterMode.setIcon(Utilities.getIcon("resources/theater.png"));
		theaterMode.setContentAreaFilled(false);
		theaterMode.setRolloverEnabled(true);
		theaterMode.setBorder(null);
		theaterMode.setRolloverIcon(Utilities.getIcon("resources/theater_hover.png"));
		theaterMode.addActionListener(e -> client.showBotTheater());

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

		newTabButton.setBorder(null);
		newTabButton.setContentAreaFilled(false);
		newTabButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				client.addNewTab();
			}
		});

		updateComponents();
	}

	private void updateComponents() {
		removeAll();
		for (BotTab tab : tabs) {
			add(tab);
		}
		add(newTabButton);
		add(Box.createHorizontalGlue());
		add(theaterMode);
		add(settingsButton);
		revalidate();
	}

	public void updateCurrentTabContent(JPanel content) {
		this.currentTab.updateContent(content);
	}

	public BotTab getCurrentTab() {
		return currentTab;
	}

	public void addTab(JPanel panel) {
		final BotTab tab = new BotTab("Bot " + (tabs.size() + 1), panel);
		tab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				client.displayScreen(tab.getContent());
				currentTab = tab;
			}
		});
		this.tabs.add(tab);
		currentTab = tab;
		updateComponents();
		client.displayScreen(panel);
		Logger.log("Bot tab created.");
	}

	public final class BotTab extends JButton {

		private final String name;
		private JPanel content;

		public BotTab(String name, JPanel content) {
			super(name);
			this.content = content;
			this.name = name;
		}

		public void updateContent(JPanel content) {
			this.content = content;
		}

		@Override
		public String getName() {
			return name;
		}

		public JPanel getContent() {
			return content;
		}
	}

}
