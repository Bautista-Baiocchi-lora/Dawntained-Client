package org.bot.boot;

import org.bot.classloader.ArchiveClassLoader;
import org.bot.loader.GameLoader;

import javax.swing.*;

public class Engine {

    private static Engine instance = new Engine();
    private GameLoader game;
    private JFrame gameJFrame;
    private boolean debugMouse;

    private Engine() {
        game = new GameLoader();
    }

    public static Engine getInstance() {
        return instance;
    }

    public boolean isDebugMouse() {
        return debugMouse;
    }

    public void setDebugMouse(boolean debugMouse) {
        this.debugMouse = debugMouse;
    }

    public GameLoader getGame() {
        return game;
    }

    public JFrame getGameJFrame() {
        return gameJFrame;
    }

    public void setGameJFrame(JFrame gameJFrame) {
        this.gameJFrame = gameJFrame;
    }

    public Class<?> loadClass(final String className) {
        if (game.classLoader == null) {
            System.out.println("Error Null Class Loader");
            return null;
        }
        if (!((ArchiveClassLoader) game.classLoader).classes().containsKey(className)) {
            try {
                return game.classLoader.loadClass(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return ((ArchiveClassLoader) game.classLoader).classes().get(className);
    }
}
