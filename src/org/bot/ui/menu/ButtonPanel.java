package org.bot.ui.menu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 3147770371892729345L;
	private final Color color = new Color(92, 98, 106);
	private final Color colorDark = color.darker();

	private Buttons statsButton, playButton, pauseButton, stopButton;
	private ePopUpMenu menu;

	public ButtonPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		menu = new ePopUpMenu();
		add(Box.createHorizontalGlue());
		playButton = new Buttons("play.png");
		playButton.setButtonHoverIcon("play_hover.png");
		playButton.setToolTipText("Start Script.");

		add(playButton);

		pauseButton = new Buttons("pause.png");
		pauseButton.setButtonHoverIcon("pause_hover.png");
		pauseButton.setToolTipText("Pause Script.");

		add(pauseButton);

		stopButton = new Buttons("stop.png");
		stopButton.setButtonHoverIcon("stop_hover.png");
		stopButton.setToolTipText("Stop Script.");

		add(stopButton);

		statsButton = new Buttons("buttons/settings.png");
		statsButton.setButtonHoverIcon("buttons/settings_hover.png");
		statsButton.setToolTipText("Debug Settings");
		statsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JButton button = (JButton) e.getSource();
				menu.show(ButtonPanel.this, button.getX(), button.getY());
			}
		});

		add(statsButton);

	}

	@Override
	public void paintComponent(Graphics g) {
		final Graphics2D graphics2D = (Graphics2D) g;
		final GradientPaint gradient = new GradientPaint(getX(), getY(), colorDark, getWidth(), getY(), color);
		graphics2D.setPaint(gradient);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
