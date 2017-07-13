package org.bot.ui.screens.loading;

/**
 * Created by bautistabaiocchi-lora on 7/12/17.
 */
public interface ProgressRelayer {

	void registerProgressTracker(ProgressTracker tracker);

	void update();
}
