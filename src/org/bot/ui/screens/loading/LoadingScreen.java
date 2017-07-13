package org.bot.ui.screens.loading;

/**
 * Created by bautistabaiocchi-lora on 7/12/17.
 */


import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

public class LoadingScreen extends Scene implements ProgressTracker {
	private static VBox layout;
	private ProgressBar bar;
	private Label status;
	private final Thread loadThread;
	private final Runnable runnable;

	public LoadingScreen(Runnable runnable) {
		super(layout = new VBox(), 400, 70);
		loadThread = new Thread(this.runnable = runnable);
		configure();
	}

	private void configure() {
		bar = new ProgressBar();
		status = new Label("Status");
		bar.setMaxWidth(Double.MAX_VALUE);
		layout.getChildren().addAll(status, bar);
		layout.setSpacing(10);
		layout.setStyle("-fx-padding: 10");
	}

	public void run() {
		((ProgressRelayer) runnable).registerProgressTracker(this);
		loadThread.start();
	}

	@Override
	public void update(final double progress, final String status) {
		bar.setProgress(progress);
		this.status.setText(status);
	}
}