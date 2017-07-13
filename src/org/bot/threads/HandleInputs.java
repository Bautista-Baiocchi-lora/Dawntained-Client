package org.bot.threads;

import org.bot.Engine;
import org.bot.component.inputs.InternalKeyboard;
import org.bot.component.inputs.InternalMouse;
import org.bot.util.Condition;

import java.applet.Applet;

/**
 * Created by Ethan on 7/10/2017.
 */
public class HandleInputs implements Runnable {
	Applet applet;

	@Override
	public void run() {
		System.out.println("Attempting to set mouse & keyboard.");

		while (Engine.getGameCanvas() == null) {
			Condition.sleep(5);
		}
		Condition.wait(new Condition.Check() {
			public boolean poll() {
				applet = (Applet) Engine.getGameComponent();
				return applet.getComponents().length != 0;
			}
		}, 100, 20);

		Engine.setMouse(new InternalMouse());
		Engine.setKeyboard(new InternalKeyboard());
		System.out.println("Mouse & Keyboard set.");

	}

}