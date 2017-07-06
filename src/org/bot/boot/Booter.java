package org.bot.boot;

import org.bot.loader.GameLoader;
import org.bot.ui.login.LoginFrame;
import org.bot.util.Condition;

import javax.swing.*;

public class Booter {

    public static void main(String[] args) {
        Engine.getInstance();
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
                Engine.getInstance().setGameLoader(new GameLoader());
            }
        });
    }

}
