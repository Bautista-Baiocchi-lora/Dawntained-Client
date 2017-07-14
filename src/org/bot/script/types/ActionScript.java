package org.bot.script.types;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ethan on 7/14/2017.
 */
public abstract class ActionScript extends LoopScript implements Comparator<Action> {

    private final List<Action> actions = new LinkedList<>();

    private synchronized Action get() {
        try {
            for (Action a : actions) {
                if (a != null && a.activate()) {
                    return a;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void provide(Action... action) {
        Collections.addAll(actions, action);
        Collections.sort(actions, this);
    }

    @Override
    public void onStart() {
        start();
    }

    @Override
    public int operate() {
        try {
            final Action action = get();
            if (action != null) {
                action.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 200;
    }

    @Override
    public void onStop() {
        actions.clear();
    }

    @Override
    public int compare(Action o1, Action o2) {
        return o1.priority() - o2.priority();
    }

    public abstract void start();
}