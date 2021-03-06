package org.bot.ui.screens.clientframe.menu.buttonpanel;

import org.bot.util.Utilities;

import javax.swing.*;
import java.awt.*;

public class Buttons extends JButton {

	private static final long serialVersionUID = 8254130076836375897L;
	private Image buttonIcon;
	private Image buttonRollOverIcon;
	private Image buttonDisabledIcon;
	private Image buttonHoveredIcon;

	public Buttons(String imageName) {
		setButtonIcon(imageName);
		setIcon(new ImageIcon(buttonIcon));
		setMinimumSize(new Dimension(24, 24));
		setBorder(null);
		setBorderPainted(false);
		setFocusPainted(false);
		setOpaque(false);
		setContentAreaFilled(false);
	}

	public void setButtonIcon(String imageName) {
		buttonIcon = Utilities.getLocalImage("/resources/" + imageName);
		setIcon(new ImageIcon(buttonIcon));
	}

	public void setButtonHoverIcon(String imageName) {
		buttonHoveredIcon = Utilities.getLocalImage("/resources/" + imageName);
		setRolloverIcon(new ImageIcon(buttonHoveredIcon));
	}

	public void setButtonDisabledIcon(String imageName) {
		buttonDisabledIcon = Utilities.getLocalImage("/resources/" + imageName);
		setDisabledIcon(new ImageIcon(buttonDisabledIcon));
	}

	public void setButtonRollOverIcon(String imageName) {
		buttonRollOverIcon = Utilities.getLocalImage("/resources/" + imageName);
		setRolloverIcon(new ImageIcon(buttonRollOverIcon));
	}

}