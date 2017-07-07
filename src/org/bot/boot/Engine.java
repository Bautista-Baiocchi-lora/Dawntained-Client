package org.bot.boot;

import org.bot.server.ServerLoader;
import org.bot.server.ServerManifest;
import org.bot.server.ServerProvider;
import org.bot.util.directory.DirectoryManager;

import java.awt.*;

public class Engine {

    private static Engine instance;
    private String username;
    private boolean developer;
    private ServerLoader<?> serverLoader;
    private Component gameComponent;
    private boolean debugMouse;
    private DirectoryManager directoryManager;
    private ServerManifest serverManifest;

    public static Engine getInstance() {
        return instance == null ? instance = new Engine() : instance;
    }

    public boolean getDeveloper() {
        return developer;
    }

    public void setDeveloper(boolean developer) {
        this.developer = developer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isDebugMouse() {
        return debugMouse;
    }

    public void setDebugMouse(boolean debugMouse) {
        this.debugMouse = debugMouse;
    }

    public ServerLoader<?> getServerLoader() {
        return serverLoader;
    }

    public ServerManifest getServerManifest() {
        return serverManifest;
    }

    public void setServerProvider(ServerProvider provider) {
        this.serverLoader = provider.getLoader();
        this.serverManifest = provider.getManifest();
    }

    public DirectoryManager getDirectoryManager() {
        return directoryManager;
    }

    public void setDirectoryManager(DirectoryManager manager) {
        this.directoryManager = manager;
    }

    public Component getGameComponent() {
        return gameComponent;
    }

    public void setGameComponent(Component gameComponent) {
        this.gameComponent = gameComponent;
    }

}
