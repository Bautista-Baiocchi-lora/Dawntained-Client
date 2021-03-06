package org.bot.ui.screens.clientframe.menu.buttonpanel;

import org.bot.Engine;
import org.bot.script.handler.ScriptHandler;
import org.bot.ui.screens.clientframe.menu.SettingsMenu;
import org.bot.ui.screens.scriptselector.ScriptSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 3147770371892729345L;
	private final Color color = new Color(92, 98, 106);
	private final Color colorDark = color.darker();
	private final SettingsMenu menu;
	private ScriptSelector scriptSelector;
	private Buttons statsButton, playButton, pauseButton, stopButton;

	public ButtonPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		menu = new SettingsMenu();
		add(Box.createHorizontalGlue());
		scriptSelector = new ScriptSelector();
		playButton = new Buttons("buttons/play.png");
		playButton.setButtonHoverIcon("buttons/play_hover.png");
		playButton.setToolTipText("Start Script.");
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Engine.getScriptHandler().getScriptState().equals(ScriptHandler.State.PAUSE)) {
					Engine.getScriptHandler().setScriptState(ScriptHandler.State.RUNNING);
				} else if (!Engine.getScriptHandler().getScriptState().equals(ScriptHandler.State.RUNNING) && !scriptSelector.isVisible()) {
					scriptSelector.loadScripts();
					scriptSelector.setLocationRelativeTo(scriptSelector.getOwner());
					scriptSelector.setVisible(!scriptSelector.isVisible());
				}
			}
		});
		add(playButton);

		pauseButton = new Buttons("buttons/pause.png");
		pauseButton.setButtonHoverIcon("buttons/pause_hover.png");
		pauseButton.setToolTipText("Pause Script.");
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Engine.getScriptHandler().getScriptState() != null && Engine.getScriptHandler().getScriptState().equals(ScriptHandler.State.RUNNING)) {
					Engine.getScriptHandler().pause();
				} else {
					System.out.println("There is no script currently running!");
				}
			}
		});
		add(pauseButton);

		stopButton = new Buttons("buttons/stop.png");
		stopButton.setButtonHoverIcon("buttons/stop_hover.png");
		stopButton.setToolTipText("Stop Script.");
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Engine.getScriptHandler().getScriptState() != null && !Engine.getScriptHandler().getScriptState().equals(ScriptHandler.State.STOPPED)) {
					Engine.getScriptHandler().stop();
				} else {
					System.out.println("There is no script currently running!");
				}
			}
		});
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
