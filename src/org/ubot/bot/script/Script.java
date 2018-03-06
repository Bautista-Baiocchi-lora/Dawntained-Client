package org.ubot.bot.script;

public abstract class Script {

	private final long startTime = System.currentTimeMillis();

	public abstract boolean onStart();

	public abstract void onStop();

	public abstract void onBreak();

	protected abstract int operate();

	public final long getRuntime() {
		return System.currentTimeMillis() - startTime;
	}
}