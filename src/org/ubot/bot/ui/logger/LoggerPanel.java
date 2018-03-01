package org.ubot.bot.ui.logger;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bautistabaiocchi-lora on 7/16/17.
 */

public class LoggerPanel extends JPanel {
	private final JScrollPane scrollPane;

	public LoggerPanel() {
		scrollPane = new JScrollPane(new Logger(), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
	}


}