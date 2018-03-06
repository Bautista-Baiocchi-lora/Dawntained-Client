package org.ubot.bot.script;

public abstract class LoopScript extends Script {

	public abstract int loop();

	@Override
	public final int operate() {
		return loop();
	}
}
