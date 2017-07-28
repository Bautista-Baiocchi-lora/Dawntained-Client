package org.bot.ui.screens.util;

import javafx.scene.control.Label;
import org.bot.ui.BotUI;

/**
 * Created by bautistabaiocchi-lora on 7/26/17.
 */
public class ExpandableListLabel<T extends Object> extends Label {

	private final ExpandableTab<T> tab;

	public ExpandableListLabel(ExpandableTab<T> tab) {
		super(tab.getLabelTitle());
		this.tab = tab;
		this.tab.registerManager(BotUI.getInstance());
	}

	protected ExpandableTab<T> getTab() {
		return tab;
	}


}
