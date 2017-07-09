package org.bot.component;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import org.bot.Engine;
import org.bot.component.listeners.PaintListener;
import org.bot.component.screen.ScreenOverlay;
import org.bot.util.Condition;

/**
 * Created by Ethan on 7/7/2017.
 */
public class RSCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 765, HEIGHT = 503;
	private BufferedImage clientBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage gameBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private List<PaintListener> listeners = new ArrayList<>();
	private final int FRAMESTEP = 1000 / 60;
	private long timeTaken = 0;
	private long beginTime = 0;

	public RSCanvas() {
		super();
		Engine.setGameCanvas(this);
		List<ScreenOverlay> overlayList = Engine.getServerLoader().getOverlays();
		ScreenOverlay[] overlays = overlayList.toArray(new ScreenOverlay[overlayList.size()]);
		Collections.addAll(listeners, overlays);
	}

	public Graphics getGraphics() {
		beginTime = System.currentTimeMillis();
		if (this.getHeight() != clientBuffer.getHeight() || this.getWidth() != clientBuffer.getWidth()) {

			if (Engine.getServerManifest().type().equals(JPanel.class)) {
				Engine.getGameFrame().revalidate();
			}
			this.clientBuffer.flush();
			this.gameBuffer.flush();
			Runtime.getRuntime().gc();
			clientBuffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
			gameBuffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);

		}
		final Graphics graphics = clientBuffer.getGraphics();
		graphics.drawImage(gameBuffer, 0, 0, null);

		for (PaintListener listener : getPaintListeners()) {
			if (listener instanceof ScreenOverlay) {
				final ScreenOverlay<?> debug = (ScreenOverlay<?>) listener;
				if (debug.activate()) {
					debug.render((Graphics2D) graphics);
				}
			} else {
				listener.render((Graphics2D) graphics);
			}
		}
		graphics.dispose();

		final Graphics2D rend = (Graphics2D) super.getGraphics();
		rend.drawImage(clientBuffer, 0, 0, null);
		timeTaken = System.currentTimeMillis() - beginTime;
		Condition.sleep((int) (FRAMESTEP - timeTaken));
		return gameBuffer.getGraphics();
	}

	public synchronized java.util.List<PaintListener> getPaintListeners() {
		return listeners;
	}

}