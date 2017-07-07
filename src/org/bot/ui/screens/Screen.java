package org.bot.ui.screens;

import org.bot.ui.InterfaceManager;

import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class Screen extends Scene {

	protected final InterfaceManager manager;

	public Screen(Parent root, InterfaceManager manager) {
		super(root);
		this.manager = manager;
	}

}
