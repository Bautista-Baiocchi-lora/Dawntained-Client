package org.bot.script.types;

/**
 * Created by Ethan on 7/14/2017.
 */
public abstract class Action {


	public abstract boolean activate();

	public abstract void execute();

	public int priority() {
		return 0;
	}

}