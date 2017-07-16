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
import org.bot.Engine;
import org.bot.ui.management.InterfaceAction;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manageable;
import org.bot.ui.management.Manager;

import java.util.ArrayList;

public class LoadingScreen extends Scene implements Manageable {

	private static VBox layout;
	private final ArrayList<Manager> managers = new ArrayList<Manager>();
	private final Thread loadThread;
	private final Task<?> task;
	private ProgressBar bar;
	private Label status;
	private boolean success;

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
		if (!Engine.isDeveloper()) {
			task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(final WorkerStateEvent event) {
					requestAction(new InterfaceActionRequest.ActionBuilder(InterfaceAction.TERMINATE_UI).build());
				}
			});
		}
		loadThread.start();
	}

	@Override
	public void requestAction(final InterfaceActionRequest request) {
		for (Manager manager : managers) {
			manager.processActionRequest(request);
		}
	}

	@Override
	public void registerManager(final Manager manager) {
		managers.add(manager);
	}
}