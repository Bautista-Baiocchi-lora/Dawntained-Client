package org.ubot.client.ui.logger;

import javax.swing.*;
import java.awt.*;

public class LoggerPanel extends JPanel {

	public LoggerPanel(Logger logger) {
		final JScrollPane scrollPane = new JScrollPane(logger, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(0, 150));
		add(scrollPane, BorderLayout.CENTER);
	}
}
