package org.ubot.bot.component.screen;


import org.ubot.bot.component.listeners.PaintListener;
import org.ubot.util.Timer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ethan on 7/7/2017.
 */
public abstract class ScreenOverlay<E> extends JCheckBoxMenuItem implements PaintListener {

	protected List<E> elements = new ArrayList<>();
	private Timer refreshRate = new Timer(1000);
	private boolean show;

	public ScreenOverlay(String name) {
		super(name);
		this.addActionListener(e -> show = isSelected());
	}

	public boolean activate() {
		return show;
	}

	public abstract E[] elements();

	public List<E> refresh() {
		if (!refreshRate.isRunning()) {
			elements = Arrays.asList(elements());
		}
		return elements;
	}

}