package org.bot.component.inputs;

import org.bot.Engine;
import org.bot.util.Condition;
import org.bot.util.Random;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InternalMouse implements MouseListener, MouseMotionListener {

	private final MouseListener mouseListenerDispatcher;
	private final MouseMotionListener mouseMotionDispatcher;
	private Component component;
	private int clientX;
	private int clientY;
	private int clientPressX = -1;
	private int clientPressY = -1;
	private long clientPressTime = -1;
	private boolean clientPressed;

	public InternalMouse() {
		this.component = Engine.getGameCanvas();
		this.mouseListenerDispatcher = component.getMouseListeners()[0];
		this.mouseMotionDispatcher = component.getMouseMotionListeners()[0];
		component.addMouseListener(this);
		component.addMouseMotionListener(this);

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clientX = e.getX();
		clientY = e.getY();
		mouseListenerDispatcher.mouseClicked(new MouseEvent(component, MouseEvent.MOUSE_CLICKED,
				System.currentTimeMillis(), 0, clientX, clientY, 1, false, e.getButton()));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		press(e.getX(), e.getY(), e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		clientPressX = e.getX();
		clientPressY = e.getY();
		clientPressTime = System.currentTimeMillis();
		clientPressed = false;
		release(e.getX(), e.getY(), e.getButton());
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		Point p = Engine.getGameCanvas().getMousePosition();
		if (p != null && p.x > 0 && p.y > 0) {
			clientX = p.x;
			clientY = p.y;
			move(clientX, clientY);
		} else if (arg0.getX() > 0 && arg0.getY() > 0) {
			clientX = arg0.getX();
			clientY = arg0.getY();
			move(clientX, clientY);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point p = Engine.getGameCanvas().getMousePosition();
		if (p != null && p.x > 0 && p.y > 0 && Engine.getGameCanvas() != null && Engine.getGameCanvas().contains(p)) {

			clientX = p.x;
			clientY = p.y;
			move(clientX, clientY);

		} else if (e.getX() > 0 && e.getY() > 0 && Engine.getGameCanvas().contains(new Point(e.getX(), e.getY()))) {
			clientX = e.getX();
			clientY = e.getY();
		}
		move(clientX, clientY);
	}

	public int getX() {
		return clientX;
	}

	public int getY() {
		return clientY;
	}

	public int getPressX() {
		return clientPressX;
	}

	public int getPressY() {
		return clientPressY;
	}

	public long getPressTime() {
		return clientPressTime;
	}

	public boolean isPressed() {
		return clientPressed;
	}

	public Point getLocation() {
		return new Point(getX(), getY());
	}

	public void move(int x, int y) {
		clientX = x;
		clientY = y;
		mouseMotionDispatcher.mouseMoved(
				new MouseEvent(component, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, x, y, 0, false));
	}

	public void click(int x, int y, int button) {
		press(x, y, button);
		Condition.sleep(Random.nextInt(20, 100));
		release(x, y, button);
		mouseListenerDispatcher.mouseClicked(new MouseEvent(component, MouseEvent.MOUSE_CLICKED,
				System.currentTimeMillis(), 0, clientX, clientY, 1, false, button));
	}

	public void press(int x, int y, int button) {
		mouseListenerDispatcher.mousePressed(new MouseEvent(component, MouseEvent.MOUSE_PRESSED,
				System.currentTimeMillis(), 0, x, y, 1, false, button));
	}

	public void release(int x, int y, int button) {
		mouseListenerDispatcher.mouseReleased(new MouseEvent(component, MouseEvent.MOUSE_RELEASED,
				System.currentTimeMillis(), 0, x, y, 1, false, button));
	}

	public void click(boolean left) {
		click(clientX, clientY, (left ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3));
	}

	public boolean drag(int x1, int y1, int x2, int y2) {
		move(x1, y1);
		press(getX(), getY(), MouseEvent.BUTTON2);
		move(x2, y2);
		release(getX(), getY(), MouseEvent.BUTTON2);

		return getX() == x2 && getY() == y2 && !isPressed();
	}

}