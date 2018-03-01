package org.ubot.bot.ui.buttonpanel;

import org.ubot.bot.Bot;
import org.ubot.bot.script.handler.ScriptHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 3147770371892729345L;
	private final Color color = new Color(92, 98, 106);
	private final Color colorDark = color.darker();
	private Buttons playButton, pauseButton, stopButton;

	public ButtonPanel(Bot bot) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(Box.createHorizontalGlue());
		playButton = new Buttons("buttons/play.png");
		playButton.setButtonHoverIcon("buttons/play_hover.png");
		playButton.setToolTipText("Start Script.");
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bot.changeScriptState(ScriptHandler.State.RUN);
			}
		});
		add(playButton);

		pauseButton = new Buttons("buttons/pause.png");
		pauseButton.setButtonHoverIcon("buttons/pause_hover.png");
		pauseButton.setToolTipText("Pause Script.");
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bot.changeScriptState(ScriptHandler.State.PAUSE);
			}
		});
		add(pauseButton);

		stopButton = new Buttons("buttons/stop.png");
		stopButton.setButtonHoverIcon("buttons/stop_hover.png");
		stopButton.setToolTipText("Stop Script.");
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bot.changeScriptState(ScriptHandler.State.STOP);
			}
		});
		add(stopButton);
	}

	@Override
	public void paintComponent(Graphics g) {
		final Graphics2D graphics2D = (Graphics2D) g;
		final GradientPaint gradient = new GradientPaint(getX(), getY(), colorDark, getWidth(), getY(), color);
		graphics2D.setPaint(gradient);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
