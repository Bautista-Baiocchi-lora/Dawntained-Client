package org.ubot.bot.component;

import org.ubot.bot.BotModel;
import org.ubot.bot.component.listeners.PaintListener;
import org.ubot.bot.component.screen.ScreenOverlay;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 7/7/2017.
 */
public class RSCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 765, HEIGHT = 503;
	private BufferedImage clientBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage gameBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private List<PaintListener> listeners = new ArrayList<>();
	private BotModel botModel = null;

	public RSCanvas(BotModel botModel) {
		this.botModel = botModel;
		botModel.setGameCanvas(this);
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
		return gameBuffer.getGraphics();
	}

	public synchronized java.util.List<PaintListener> getPaintListeners() {
		return listeners;
	}

}