package org.ubot.bot.ui;

import org.ubot.bot.BotModel;
import org.ubot.bot.ui.buttonpanel.Buttons;
import org.ubot.bot.ui.logger.LogType;
import org.ubot.bot.ui.logger.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by bautistabaiocchi-lora on 7/28/17.
 */
public class SettingsMenu extends JMenuBar implements ActionListener {

	private final BotModel model;
	private final JMenu debugMenu;
	private final JCheckBoxMenuItem mouse, npcs, players, text, inventory, objects;

	public SettingsMenu(BotModel model) {
		this.model = model;

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

		objects = new JCheckBoxMenuItem("Objects");
		objects.addActionListener(this);

		debugMenu.add(npcs);
		debugMenu.add(players);
		debugMenu.add(objects);
		debugMenu.add(text);
		debugMenu.add(inventory);
		debugMenu.add(mouse);

		add(debugMenu);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		switch (e.getActionCommand()) {
			case "Mouse":
				model.toggleMouseDebug();
				Logger.log("Mouse debugging toggled.", LogType.DEBUG);
				break;
			case "NPCs":
				model.toggleNPCDebug();
				Logger.log("NPC debugging toggled.", LogType.DEBUG);
				break;
			case "Players":
				model.togglePlayersDebug();
				Logger.log("Player debugging toggled.", LogType.DEBUG);
				break;
			case "Inventory":
				model.toggleInventoryDebug();
				Logger.log("Inventory debugging toggled.", LogType.DEBUG);
				break;
			case "Game":
				model.toggleGameDebug();
				Logger.log("Game info debugging toggled.", LogType.DEBUG);
				break;
			case "Objects":
				model.toggleObjectDebug();
				Logger.log("Objects debugging toggled.", LogType.DEBUG);
				break;
		}
	}

}
