package org.ubot.bot;

import org.ubot.client.account.Account;

public class BotModel {

	private final boolean developer;
	private final String username, server;
	private final Account account;

	private BotModel(Builder builder) {
		account = builder.account;
		developer = builder.developer;
		username = builder.username;
		server = builder.server;
	}

	public String getGameAccountName() {
		return account.getUsername();
	}

	public String getuBotName() {
		return username;
	}

	public String getServer() {
		return server;
	}

	public static class Builder {

		private static boolean developer;
		private static Account account;
		private static String username, server;

		public Builder(String server) {
			this.server = server;
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
