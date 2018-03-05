package org.ubot.client.ui;

import org.ubot.client.Client;
import org.ubot.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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

			}
		});

		updateComponents(null);
	}

	public void updateComponents(List<JComponent> components) {
		removeAll();
		if (components != null) {
			for (JComponent c : components) {
				add(c);
			}
		}
		add(newTabButton);
		add(Box.createHorizontalGlue());
		add(theaterMode);
		add(settingsButton);
		revalidate();
	}

}
