package org.ubot.launcher;

import javafx.application.Application;
import javafx.stage.Stage;
import org.ubot.bot.ui.logger.LogType;
import org.ubot.bot.ui.logger.Logger;
import org.ubot.util.ConfigManager;
import org.ubot.util.directory.DirectoryManager;

import javax.swing.*;

public class Launcher extends Application {

	private Stage stage;

	public Launcher() {
		DirectoryManager.init();
		ConfigManager.init();
		Logger.log("Launcher Loaded.", LogType.CLIENT);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		primaryStage.setTitle("uBot Launcher");
		primaryStage.setScene(new PortalScreen());
		primaryStage.show();
	}
}
