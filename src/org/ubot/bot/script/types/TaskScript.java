package org.ubot.bot.script.types;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public abstract class TaskScript extends Script implements Comparator<Task> {

	private final List<Task> actions = new LinkedList<Task>();

	private synchronized Task get() {
		try {
			for (Task action : actions) {
				if (action != null && action.activate()) {
					return action;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public final void provide(Task... action) {
		Collections.addAll(actions, action);
		Collections.sort(actions, this);
	}

	@Override
	public final int operate() {
		final Task action = get();
		if (action != null) {
			action.execute();
			return 200;
		}
		return 0;
	}

	@Override
	public int compare(Task o1, Task o2) {
		return o1.priority() - o2.priority();
	}

}