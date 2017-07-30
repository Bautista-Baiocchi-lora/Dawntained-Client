package org.bot.ui.screens.clientframe.menu;

import org.bot.Engine;
import org.bot.ui.screens.clientframe.menu.accountmanager.AccountManagerUI;
import org.bot.ui.screens.clientframe.menu.logger.LogType;
import org.bot.ui.screens.clientframe.menu.logger.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by bautistabaiocchi-lora on 7/28/17.
 */
public class SettingsMenu extends JPopupMenu implements ActionListener {


	private final JMenu debugMenu;
	private final JMenuItem accountManagerMenu;
	private final JCheckBoxMenuItem mouse, npcs, players, text, inventory;

	public SettingsMenu() {
		accountManagerMenu = new JMenuItem("Account Manager");
		accountManagerMenu.addActionListener(this);

		debugMenu = new JMenu("Debugging");

		mouse = new JCheckBoxMenuItem("Mouse");
		mouse.addActionListener(this);

		npcs = new JCheckBoxMenuItem("NPCs");
		npcs.addActionListener(this);

		text = new JCheckBoxMenuItem("Game");
		text.addActionListener(this);

		players = new JCheckBoxMenuItem("Players");
		players.addActionListener(this);

		inventory = new JCheckBoxMenuItem("Inventory");
		inventory.addActionListener(this);

		debugMenu.add(npcs);
		debugMenu.add(players);
		debugMenu.add(text);
		debugMenu.add(inventory);
		debugMenu.add(mouse);

		add(accountManagerMenu);
		add(debugMenu);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		switch (e.getActionCommand()) {
			case "Account Manager":
				final AccountManagerUI accountManagerUI = new AccountManagerUI();
				accountManagerUI.setLocationRelativeTo(this.getRootPane());
				break;
			case "Mouse":
				Engine.getServerProvider().debugMouse(!Engine.getServerProvider().isDebugMouse());
				Logger.log("Mouse debugging toggled.", LogType.DEBUG);
				break;
			case "NPCs":
				Engine.getServerProvider().debugNPCs(!Engine.getServerProvider().isDebugNPCs());
				Logger.log("NPC debugging toggled.", LogType.DEBUG);
				break;
			case "Players":
				Engine.getServerProvider().debugPlayers(!Engine.getServerProvider().isDebugPlayers());
				Logger.log("Player debugging toggled.", LogType.DEBUG);
				break;
			case "Inventory":
				Engine.getServerProvider().debugInventory(!Engine.getServerProvider().isDebugInventory());
				Logger.log("Inventory debugging toggled.", LogType.DEBUG);
				break;
			case "Game":
				Engine.getServerProvider().debugGameInfo(!Engine.getServerProvider().isDebugGameInfo());
				Logger.log("Game info debugging toggled.", LogType.DEBUG);
				break;
		}
	}

}
