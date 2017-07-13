package org.bot.ui.screens.loading;

/**
 * Created by bautistabaiocchi-lora on 7/12/17.
 */


import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

public class LoadingScreen extends Scene {
	private static VBox layout;
	private final Thread loadThread;
	private final Task<?> task;
	private ProgressBar bar;
	private Label status;

	public LoadingScreen(Task<?> task) {
		super(layout = new VBox(), 400, 70);
		loadThread = new Thread(this.task = task);
		configure();
	}

	private void configure() {
		bar = new ProgressBar();
		bar.progressProperty().bind(task.progressProperty());
		bar.setMaxWidth(Double.MAX_VALUE);
		status = new Label("Status");
		status.textProperty().bind(task.messageProperty());
		layout.getChildren().addAll(status, bar);
		layout.setSpacing(10);
		layout.setStyle("-fx-padding: 10");
	}

	public void run() {
		loadThread.setDaemon(true);
		loadThread.start();

	}
}