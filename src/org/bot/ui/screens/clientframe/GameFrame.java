package org.bot.ui.screens.clientframe;

import org.bot.Engine;
import org.bot.account.AccountManager;
import org.bot.ui.screens.clientframe.menu.buttonpanel.ButtonPanel;
import org.bot.ui.screens.clientframe.menu.logger.Logger;
import org.bot.ui.screens.clientframe.menu.logger.LoggerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Ethan on 7/8/2017.
 */
public class GameFrame extends JFrame implements WindowListener {

	private ButtonPanel buttonPanel;

	public GameFrame(Component component) {
		Engine.setAccountManager(new AccountManager());
		setTitle(Engine.getInterfaceTitle());
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		buttonPanel = new ButtonPanel();
		getContentPane().add(buttonPanel, BorderLayout.NORTH);
		getContentPane().add(component);
		getContentPane().add(new LoggerPanel(new Logger()), BorderLayout.SOUTH);
		addWindowListener(this);
		setLocationRelativeTo(getParent());
		pack();
		setLocationRelativeTo(getOwner());
		confirmOnClose();
		setVisible(true);
	}


	public void confirmOnClose() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(new JLabel("", JLabel.CENTER),
						"Are you sure you wish to close uBot?");
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}
}
