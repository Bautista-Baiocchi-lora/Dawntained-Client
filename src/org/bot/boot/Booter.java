package org.bot.boot;

import java.io.File;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.bot.loader.GameLoader;
import org.bot.ui.login.LoginFrame;
import org.bot.util.Condition;
import org.bot.util.directory.DirectoryManager;

public class Booter {

	public static void main(String[] args) {
		Engine.getInstance();
		/*
		 * try { ///ERROR for me UIManager.setLookAndFeel( new DarculaLaf()); }
		 * catch (Exception e) { e.printStackTrace(); }
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
				Engine.getInstance().setGameLoader(
						new GameLoader(DirectoryManager.SERVER_JARS_PATH + File.separator + "GamePack.jar"));
			}
		});

	}

}
