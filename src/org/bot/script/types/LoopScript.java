package org.bot.script.types;


/**
 * Created by bautistabaiocchi-lora on 7/30/2017.
 */
public abstract class LoopScript extends Script {

	public abstract int loop();

	@Override
	public final int operate() {
		return loop();
	}
}