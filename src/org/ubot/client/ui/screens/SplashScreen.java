package org.ubot.client.ui.screens;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JPanel {

	public SplashScreen(double version) {
		super(new BorderLayout());
		setPreferredSize(new Dimension(765, 503));
		setBorder(BorderFactory.createLoweredBevelBorder());
		add(new JLabel("Splash screen for: uBot V" + version), BorderLayout.CENTER);
	}
}
