package org.ubot.client.ui.serverselector.util;

import javafx.scene.control.ScrollPane;

/**
 * Created by bautistabaiocchi-lora on 7/26/17.
 */
public abstract class ExpandableTab<T extends Object> extends ScrollPane {

	protected final T object;

	public ExpandableTab(T object) {
		this.object = object;
		setMaxWidth(250);
		configure();
	}

	protected abstract String getLabelTitle();

	protected abstract void configure();

}
