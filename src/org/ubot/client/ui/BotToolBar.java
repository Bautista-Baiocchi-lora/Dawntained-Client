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
	private JButton newTabButton = new JButton();
	private JButton settingsButton = new JButton();
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
		settings.add(exit);
		settingsButton.setIcon(Utilities.getIcon("resources/settings.png"));
		settingsButton.setContentAreaFilled(false);
		settingsButton.setRolloverEnabled(true);
		settingsButton.setRolloverIcon(Utilities.getIcon("resources/settings_hover.png"));
		settingsButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				settings.show(e.getComponent(), e.getX(), e.getY());
			}
		});
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
		exit.addActionListener(e -> System.exit(0));
		updateComponents(null);
	}

	public void updateComponents(List<JComponent> components) {
		removeAll();
		if (components != null) {
			for (JComponent c : components) {
				add(c);
			}
		}
		add(Box.createHorizontalGlue());
		add(settingsButton);
		revalidate();
	}

}
