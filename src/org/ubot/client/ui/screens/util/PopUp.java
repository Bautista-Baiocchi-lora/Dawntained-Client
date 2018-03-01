package org.ubot.client.ui.screens.util;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Created by bautistabaiocchi-lora on 7/27/17.
 */
public abstract class PopUp extends Stage {

	protected static VBox layout;

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

}
