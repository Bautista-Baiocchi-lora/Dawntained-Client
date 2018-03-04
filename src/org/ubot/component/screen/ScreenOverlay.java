package org.ubot.component.screen;


import org.ubot.component.listeners.PaintListener;
import org.ubot.util.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ethan on 7/7/2017.
 */
public abstract class ScreenOverlay<E> implements PaintListener {

    protected List<E> list = new ArrayList<>();
    private Timer refreshRate = new Timer(1000);

    public abstract E[] elements();

    public abstract boolean activate();

    public List<E> refresh() {
        if (!refreshRate.isRunning()) {
            list = Arrays.asList(elements());
        }
        return list;
    }

}