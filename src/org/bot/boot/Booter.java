package org.bot.boot;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.bot.loader.AloraLoader;
import org.bot.loader.ServerLoader;
import org.bot.ui.login.LoginFrame;
import org.bot.ui.menu.ButtonPanel;
import org.bot.util.Condition;

public class Booter {

	public static void main(String[] args) {

		/*
		 * try { UIManager.setLookAndFeel( new DarculaLaf()); } catch (Exception
		 * e) { e.printStackTrace(); }
		 */
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
				try {
					ServerLoader<JFrame> loader = new AloraLoader();
					Engine.getInstance().setGameLoader(loader);
					JFrame gameFrame = (JFrame) loader.getGameComponent();
					Engine.getInstance().setGameComponent(gameFrame);
					gameFrame.setTitle("Fuck alora for ip banning me");
					gameFrame.revalidate();
					ButtonPanel buttonPanel = new ButtonPanel();
					gameFrame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
					gameFrame.pack();
					gameFrame.setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}

}
