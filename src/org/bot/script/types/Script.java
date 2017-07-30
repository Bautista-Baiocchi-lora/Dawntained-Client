package org.bot.script.types;

/**
 * Created by bautistabaiocchi-lora on 7/30/17.
 */
public abstract class Script {

	private final long startTime = System.currentTimeMillis();

	public abstract boolean onStart();

	public abstract void onStop();

	public abstract void onBreak();

	public abstract int operate();

	public final long getRuntime() {
		return System.currentTimeMillis() - startTime;
	}
}
