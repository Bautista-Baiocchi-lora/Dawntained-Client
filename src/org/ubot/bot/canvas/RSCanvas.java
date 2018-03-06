package org.ubot.bot.canvas;


import org.ubot.bot.canvas.listeners.PaintListener;
import org.ubot.bot.canvas.screen.ScreenOverlay;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RSCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 765, HEIGHT = 503;
	private BufferedImage clientBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage gameBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private List<PaintListener> listeners = new ArrayList<>();

	public RSCanvas() {
		super();
	}

	public BufferedImage getGameBuffer() {
		return gameBuffer;
	}

	public Graphics getGraphics() {
		if (this.getHeight() != clientBuffer.getHeight() || this.getWidth() != clientBuffer.getWidth()) {
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
				final ScreenOverlay<?> overlay = (ScreenOverlay<?>) listener;
				if (overlay.activate()) {
					overlay.render((Graphics2D) graphics);
				}
			} else {
				listener.render((Graphics2D) graphics);
			}
		}
		graphics.dispose();

		final Graphics2D rend = (Graphics2D) super.getGraphics();
		rend.drawImage(clientBuffer, 0, 0, null);
		return gameBuffer.getGraphics();
	}

	public synchronized List<PaintListener> getPaintListeners() {
		return listeners;
	}

	public void setSetOverlays(List<ScreenOverlay> overlaysList) {
		ScreenOverlay[] overlays = overlaysList.toArray(new ScreenOverlay[overlaysList.size()]);
		synchronized (listeners) {
			Collections.addAll(listeners, overlays);
		}
	}
}