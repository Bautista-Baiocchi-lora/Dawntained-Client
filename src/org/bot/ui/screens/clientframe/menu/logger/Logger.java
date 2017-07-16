package org.bot.ui.screens.clientframe.menu.logger;


import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.util.Date;

/**
 * Created by bautistabaiocchi-lora on 7/16/17.
 */

public class Logger extends JTextArea {

	private static Logger instance;
	private static JTextArea logArea;
	private final Color color = new Color(92, 98, 106);


	public Logger() {
		super(8, 5);
		instance = this;
		logArea = this;
		setEditable(false);
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		setLineWrap(true);
		final DefaultCaret caret = (DefaultCaret) getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		logArea.setBackground(color);
		logArea.setForeground(Color.white);
		log("Logger started.");
	}

	private static void write(final String str, final LogType type, final String... flags) {
		if (instance == null) {
			return;
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(new Date() + ": ");
		for (String flag : flags) {
			stringBuilder.append(" [" + flag + "]");
		}
		stringBuilder.append(str);
		stringBuilder.append("\n");
		System.out.println(stringBuilder.toString());
		logArea.append(stringBuilder.toString());
	}

	public static void log(final String str) {
		write(str, LogType.NA);
	}


	public static void log(final String str, final LogType type) {
		write(str, type);
	}

	public static void writeException(final String str) {
		write(str, LogType.NA, "EXCEPTION");
	}

	public static void writeException(final String str, final LogType type) {
		write(str, type, "EXCEPTION");
	}

	public static void writeWarning(final String str) {
		write(str, LogType.NA, "WARNING");
	}

	public static void writeWarning(final String str, final LogType type) {
		write(str, type, "WARNING");
	}


}