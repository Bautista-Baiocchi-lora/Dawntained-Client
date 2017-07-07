package org.bot.boot;

import org.bot.server.ServerLoader;
import org.bot.ui.login.LoginFrame;
import org.bot.ui.menu.ButtonPanel;
import org.bot.ui.serverselector.ServerSelector;
import org.bot.util.Condition;

import javax.swing.*;
import java.awt.*;

public class Booter {

    private static JFrame login, serverSelector;

    public static void main(String[] args) {

		/*
         * try { UIManager.setLookAndFeel( new DarculaLaf()); } catch (Exception
		 * e) { e.printStackTrace(); }
		 */
        login = new LoginFrame();
        launchFrame(login);
        serverSelector = new ServerSelector();
        launchFrame(serverSelector);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (Engine.getInstance().getServerManifest().type().equals(JFrame.class)) {
                    ServerLoader<JFrame> loader = (ServerLoader<JFrame>) Engine.getInstance().getServerLoader();
                    JFrame gameFrame = (JFrame) loader.getGameComponent();
                    Engine.getInstance().setGameComponent(gameFrame);
                    gameFrame.setTitle("Fuck alora for ip banning me");
                    gameFrame.revalidate();
                    ButtonPanel buttonPanel = new ButtonPanel();
                    gameFrame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
                    gameFrame.pack();
                    gameFrame.setVisible(true);
                }
            }
        });

    }

    private static void launchFrame(JFrame frame) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
            }
        }).start();
        Condition.wait(new Condition.Check() {
            public boolean poll() {
                return frame.isVisible();
            }
        }, 100, 20);
        while (frame.isVisible()) {
            Condition.sleep(350);
        }
        if (frame.isActive()) {
            frame.dispose();
        }
    }

}
