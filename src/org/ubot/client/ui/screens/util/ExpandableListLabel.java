package org.ubot.client.ui.screens.util;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.ubot.client.Client;

/**
 * Created by bautistabaiocchi-lora on 7/26/17.
 */
public class ExpandableListLabel<T extends Object> extends Label {

	private final ExpandableTab<T> tab;

	public ExpandableListLabel(ExpandableTab<T> tab) {
		super(tab.getLabelTitle());
		this.tab = tab;
	}

	protected ExpandableTab<T> getTab() {
		return tab;
	}

}
