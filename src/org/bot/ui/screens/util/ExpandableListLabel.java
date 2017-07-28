package org.bot.ui.screens.util;

import javafx.scene.control.Label;
import org.bot.ui.BotUI;

/**
 * Created by bautistabaiocchi-lora on 7/26/17.
 */
public class ExpandableListLabel<T extends Object> extends Label {

	private final ExpandableInformationTab<T> informationTab;

	public ExpandableListLabel(ExpandableInformationTab<T> informationTab) {
		super(informationTab.getLabelTitle());
		this.informationTab = informationTab;
		this.informationTab.registerManager(BotUI.getInstance());
	}

	protected ExpandableInformationTab<T> getInformationTab() {
		return informationTab;
	}


}
