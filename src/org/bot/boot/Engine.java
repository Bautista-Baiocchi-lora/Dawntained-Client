package org.bot.boot;

import org.bot.loader.GameLoader;
import org.bot.ui.login.LoginFrame;
import org.bot.util.Condition;

import javax.swing.*;

public class Engine {

    private static Engine instance = new Engine();
    private GameLoader gameLoader;
    private JFrame gameJFrame;
    private boolean debugMouse;
    private String username = "";
    private String password = "";
    private boolean dev = false;

    private Engine() {
        LoginFrame login = new LoginFrame();
        new Thread(new Runnable() {
            @Override
            public void run() {
                login.setVisible(true);
            }
        }).start();
        Condition.wait(new Condition.Check() {
            public boolean poll() {
                return login.isVisible();
            }
        }, 100, 20);
        while (login.isVisible()) {
            Condition.sleep(350);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameLoader = new GameLoader();
            }
        });
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

    public GameLoader getGameLoader() {
        return gameLoader;
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
