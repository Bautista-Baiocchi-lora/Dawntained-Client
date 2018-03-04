package org.ubot.bot;

import org.ubot.client.account.Account;
import org.ubot.util.reflection.ReflectionEngine;

import javax.swing.*;
import java.applet.Applet;

public class BotModel {

	private final boolean developer;
	private final String username, server;
	private final Account account;
	private final Applet applet;
	private final JPanel panel;
	private final ReflectionEngine reflectionEngine;

	private BotModel(Builder builder) {
		account = builder.account;
		developer = builder.developer;
		username = builder.username;
		server = builder.server;
		applet = builder.applet;
		reflectionEngine = builder.reflectionEngine;
		panel = builder.panel;
	}

	protected JPanel getPanel() {
		return panel;
	}

	public String getBotName() {
		return username;
	}

	public String getServer() {
		return server;
	}

	public static class Builder {

		private static boolean developer;
		private static Account account;
		private static String username, server;
		private static Applet applet;
		private static JPanel panel;
		private static ReflectionEngine reflectionEngine;

		public Builder(String server) {
			this.server = server;
		}

		public Builder panel(JPanel panel) {
			this.panel = panel;
			return this;
		}

		public Builder reflectionEngine(ReflectionEngine reflectionEngine) {
			this.reflectionEngine = reflectionEngine;
			return this;
		}

		public Builder applet(Applet applet) {
			this.applet = applet;
			return this;
		}

		public Builder developer(boolean developer) {
			this.developer = developer;
			return this;
		}

		public Builder account(Account account) {
			this.account = account;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		protected BotModel build() {
			return new BotModel(this);
		}

		public Bot buildBot() {
			return new Bot(this);
		}

	}
}
