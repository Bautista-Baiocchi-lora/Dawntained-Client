package org.bot.ui.management;

public interface Manageable {

    public void requestAction(InterfaceActionRequest request);

    public void registerManager(Manager manager);

}
