package org.bot.boot;

import org.bot.loader.GameLoader;

import javax.swing.*;

public class Engine {

    private static Engine instance;
    private GameLoader gameLoader;
    private JFrame gameJFrame;
    private boolean debugMouse;
    private String username = "";
    private String password = "";
    private boolean dev = false;

    public static Engine getInstance() {
        return instance == null ? instance = new Engine() : instance;
    }

    public boolean isDebugMouse() {
        return debugMouse;
    }

    public void setDebugMouse(boolean debugMouse) {
        this.debugMouse = debugMouse;
    }

    public GameLoader getGameLoader() {
        return gameLoader;
    }

    public void setGameLoader(GameLoader gameLoader) {
        this.gameLoader = gameLoader;
    }

    public JFrame getGameJFrame() {
        return gameJFrame;
    }

    public void setGameJFrame(JFrame gameJFrame) {
        this.gameJFrame = gameJFrame;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDev() {
        return dev;
    }

    public void setDev(boolean dev) {
        this.dev = dev;
    }
}
