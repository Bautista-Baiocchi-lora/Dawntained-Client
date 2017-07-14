package org.bot.ui.screens.serverselector;

import javafx.scene.control.Label;
import org.bot.provider.ServerProvider;
import org.bot.provider.loader.ServerLoader;
import org.bot.provider.manifest.ServerManifest;
import org.bot.ui.BotUI;

public class ServerLabel extends Label {

    private final ServerInformationTab serverTab;

    public ServerLabel(ServerLoader<?> loader, ServerManifest manifest) {
        super(manifest.serverName());
        serverTab = new ServerInformationTab(new ServerProvider(loader, manifest));
        serverTab.registerManager(BotUI.getInstance());
    }

    public ServerInformationTab getTab() {
        return serverTab;
    }

}
