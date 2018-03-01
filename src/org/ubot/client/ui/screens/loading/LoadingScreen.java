package org.ubot.client.ui.screens.loading;

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
import org.ubot.bot.Bot;
import org.ubot.bot.BotModel;
import org.ubot.client.Client;
import org.ubot.client.account.Account;

public class LoadingScreen extends Scene {

	private static VBox layout;
	private final Thread loadThread;
	private final Client client;
	private final Task<?> task;
	private ProgressBar bar;
	private Label status;

	public LoadingScreen(Client client, Task<?> task) {
		super(layout = new VBox(), 400, 70);
		this.client = client;
		this.loadThread = new Thread(this.task = task);
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

	public void run(Account account, boolean developer) {
		loadThread.setDaemon(true);
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(final WorkerStateEvent event) {
				if (developer) {
					//TODO: dev stuff
				}
				client.openBot(((BotModel.Builder) event.getSource().getValue()).setAccount(account));
			}
		});
		loadThread.start();
	}
}