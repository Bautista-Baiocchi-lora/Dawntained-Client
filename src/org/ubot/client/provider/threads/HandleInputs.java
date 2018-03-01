package org.ubot.client.provider.threads;

import org.ubot.bot.BotModel;
import org.ubot.bot.ui.logger.LogType;
import org.ubot.bot.ui.logger.Logger;
import org.ubot.util.Condition;

import java.applet.Applet;

/**
 * Created by Ethan on 7/10/2017.
 */
public class HandleInputs implements Runnable {

	private Applet applet;

	@Override
	public void run() {
		Logger.log("Attempting to set mouse & keyboard.", LogType.DEBUG);

		Logger.log("Mouse & Keyboard set.", LogType.DEBUG);
	}

}