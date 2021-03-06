package org.bot.ui.screens.scriptselector;

import org.bot.script.scriptdata.ScriptData;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * Created by Ethan on 7/13/2017.
 */
public class ScriptPanel extends JPanel {

	private static final long serialVersionUID = -5181188196122580695L;
	private JButton button;

	public ScriptPanel(final ScriptData scriptData) {
		setLayout(new BorderLayout());

		setToolTipText(scriptData.desc);

		button = new JButton("Start");

		add(button, BorderLayout.SOUTH);

		final JLabel scriptName = new JLabel(scriptData.name);

		scriptName.setHorizontalAlignment(JLabel.CENTER);

		add(scriptName, BorderLayout.NORTH);

		setBorder(new EtchedBorder());
	}

	public JButton getButton() {
		return button;
	}

}