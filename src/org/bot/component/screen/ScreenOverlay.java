package org.bot.component.screen;

import org.bot.component.listeners.PaintListener;
import org.bot.util.BasicTimer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ethan on 7/7/2017.
 */
public abstract class ScreenOverlay<E> implements PaintListener {


    protected List<E> list = new ArrayList<>();
    private BasicTimer refreshRate = new BasicTimer(1000);

    public abstract E[] elements();

    public abstract boolean activate();

    public List<E> refresh() {
        if (!refreshRate.isRunning()) {
            list = Arrays.asList(elements());
        }
        return list;
    }

}