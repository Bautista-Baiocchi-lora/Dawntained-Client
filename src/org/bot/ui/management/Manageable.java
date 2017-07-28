package org.bot.ui.management;

public interface Manageable {

	void requestAction(InterfaceActionRequest request);

	void registerManager(Manager manager);

}
