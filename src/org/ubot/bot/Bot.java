package org.ubot.bot;

import org.ubot.bot.script.handler.ScriptHandler;
import org.ubot.bot.ui.SettingsMenu;
import org.ubot.bot.ui.buttonpanel.ButtonPanel;
import org.ubot.bot.ui.logger.LoggerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Bot extends JFrame implements WindowListener {

	private final BotModel model;
	private ButtonPanel buttonPanel;

	public Bot(BotModel.Builder modelBuilder) {
		model = modelBuilder.build(this);
		configure();
	}

	private final void configure() {
		setTitle("[" + model.getUsername() + "] " + model.getServer());
		setJMenuBar(new SettingsMenu(model));
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		buttonPanel = new ButtonPanel(this);
		getContentPane().add(buttonPanel, BorderLayout.NORTH);
		getContentPane().add(model.getComponent());
		final LoggerPanel loggerPanel = new LoggerPanel();
		loggerPanel.setPreferredSize(new Dimension(model.getComponent().getWidth(), 150));
		getContentPane().add(loggerPanel, BorderLayout.SOUTH);
		addWindowListener(this);
		setLocationRelativeTo(getParent());
		pack();
		setLocationRelativeTo(getOwner());
		setVisible(true);
	}

	public void changeScriptState(ScriptHandler.State state){
		model.changeScriptState(state);
	}

	@Override
	public void windowOpened(final WindowEvent e) {

	}

	@Override
	public void windowClosing(final WindowEvent e) {
		int result = JOptionPane.showConfirmDialog(new JLabel("", JLabel.CENTER),
				"Are you sure you wish to close uBot?");
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	@Override
	public void windowClosed(final WindowEvent e) {

	}

	@Override
	public void windowIconified(final WindowEvent e) {

	}

	@Override
	public void windowDeiconified(final WindowEvent e) {

	}

	@Override
	public void windowActivated(final WindowEvent e) {

	}

	@Override
	public void windowDeactivated(final WindowEvent e) {

	}
}
