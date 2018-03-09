package org.ubot.client.ui.screens;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JPanel {
	private final Font font = new Font("Calibri", Font.PLAIN, 18);
	private final double version;
	private final double testVersion = 0;
	private final String name;

	public SplashScreen(double version, String name) {
		super(new BorderLayout());
		this.version = version;
		this.name = name;
		setPreferredSize(new Dimension(765, 503));
		setBorder(BorderFactory.createLoweredBevelBorder());
	}

	@Override
	public void paintComponent(Graphics graphics) {
		final Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setFont(font);
		if (testVersion > version) {
			g.setColor(Color.WHITE.brighter());
			drawCenteredString(g, "If you're seeing this, your client is outdated! Please download the latest version from our site!", this.getBounds(), font);
		} else {
			g.setColor(Color.WHITE.brighter());
			drawCenteredString(g, "Thank you for choosing uBot, " + name + ".", this.getBounds(), font, 23);
		}
		repaint(600);
	}

	private void drawCenteredString(Graphics2D g, String text, Rectangle rect, Font font, int y) {
		FontMetrics metrics = g.getFontMetrics(font);
		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		g.setFont(font);
		g.drawString(text, x, y);
	}

	private void drawCenteredString(Graphics2D g, String text, Rectangle rect, Font font) {
		FontMetrics metrics = g.getFontMetrics(font);
		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(font);
		g.drawString(text, x, y);
	}
}
