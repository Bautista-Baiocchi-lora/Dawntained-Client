package org.ubot.bot.script.types;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ethan on 7/14/2017.
 */
public abstract class ActionScript extends Script implements Comparator<Action> {

	private final List<Action> actions = new LinkedList<Action>();

	private synchronized Action get() {
		try {
			for (Action action : actions) {
				if (action != null && action.activate()) {
					return action;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public final void provide(Action... action) {
		Collections.addAll(actions, action);
		Collections.sort(actions, this);
	}

	@Override
	public final int operate() {
		final Action action = get();
		if (action != null) {
			action.execute();
			return 200;
		}
		return 0;
	}

	@Override
	public int compare(Action o1, Action o2) {
		return o1.priority() - o2.priority();
	}

}