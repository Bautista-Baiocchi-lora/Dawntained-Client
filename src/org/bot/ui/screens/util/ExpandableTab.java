package org.bot.ui.screens.util;

import javafx.scene.control.ScrollPane;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manageable;
import org.bot.ui.management.Manager;

import java.util.ArrayList;

/**
 * Created by bautistabaiocchi-lora on 7/26/17.
 */
public abstract class ExpandableTab<T extends Object> extends ScrollPane implements Manageable {

	protected final T object;
	private final ArrayList<Manager> managers = new ArrayList<Manager>();

	public ExpandableTab(T object) {
		this.object = object;
		setMaxWidth(250);
		configure();
	}

	protected abstract String getLabelTitle();

	protected abstract void configure();


	@Override
	public void requestAction(InterfaceActionRequest action) {
		for (Manager manager : managers) {
			manager.processActionRequest(action);
		}
	}

	@Override
	public void registerManager(Manager manager) {
		managers.add(manager);
	}
}
