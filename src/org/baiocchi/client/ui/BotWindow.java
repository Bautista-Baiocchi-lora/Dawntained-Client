package org.baiocchi.client.ui;

import java.applet.Applet;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BotWindow extends JFrame {

	private static final long serialVersionUID = -7683123166175999740L;
	private Applet applet;
	private JPanel gamePanel;

	public BotWindow() {
		super("Dawntained Bot Client");
		setResizable(false);
		setSize(762, 533);
		getContentPane().setLayout(new BorderLayout());
	}

	public void addApplet(Applet applet) {
		if (applet == null) {
			System.out.println("Could not return applet!");
			return;
		}
		this.applet = applet;
		this.applet.init();
		this.applet.start();
		this.applet.setLocation(0, 0);
		gamePanel = new JPanel();
		gamePanel.add(this.applet, BorderLayout.CENTER);
		gamePanel.setSize(getSize());
		getContentPane().add(gamePanel, BorderLayout.CENTER);
		gamePanel.repaint();
		gamePanel.revalidate();
	}
}
