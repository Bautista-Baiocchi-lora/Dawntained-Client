package org.bot.ui.screens.util;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manageable;
import org.bot.ui.management.Manager;

import java.util.ArrayList;

/**
 * Created by bautistabaiocchi-lora on 7/27/17.
 */
public abstract class PopUp extends Stage implements Manageable {

	protected static VBox layout;
	private final ArrayList<Manager> managers = new ArrayList<Manager>();

	public PopUp(String title) {
		super();
		this.setTitle(title);
		this.setMinWidth(250);
		layout.setSpacing(10);
		layout.setStyle("-fx-padding: 10;");
		Scene scene = new Scene(layout = new VBox());
		this.setScene(scene);
		configure();
		this.showAndWait();
	}

	protected abstract void configure();


	@Override
	public void requestAction(InterfaceActionRequest action) {
		for (Manager manager : managers) {
			manager.processActionRequest(action);
		}
	}

	@Override
	public void registerManager(Manager manager) {
		managers.add(manager);
	}

}
