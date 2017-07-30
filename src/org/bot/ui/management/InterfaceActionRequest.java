package org.bot.ui.management;

import org.bot.account.Account;
import org.bot.provider.ServerProvider;

public class InterfaceActionRequest {

	private final InterfaceAction action;
	private Account account;
	private ServerProvider provider;
	private double stageWidth;
	private double stageHeight;

	private InterfaceActionRequest(ActionBuilder builder) {
		action = builder.action;
		provider = builder.provider;
		stageHeight = builder.stageHeight;
		stageWidth = builder.stageWidth;
		account = builder.account;
	}

	public Account getAccount() {
		return account;
	}

	public double getStageWidth() {
		return stageWidth;
	}

	public double getStageHeight() {
		return stageHeight;
	}

	public InterfaceAction getAction() {
		return action;
	}

	public ServerProvider getProvider() {
		return provider;
	}

	public static class ActionBuilder {
		private final InterfaceAction action;
		private ServerProvider provider;
		private double stageWidth;
		private double stageHeight;
		private Account account;

		public ActionBuilder(InterfaceAction action) {
			this.action = action;
		}


		public ActionBuilder account(Account account) {
			this.account = account;
			return this;
		}

		public ActionBuilder provider(ServerProvider provider) {
			this.provider = provider;
			return this;
		}

		public ActionBuilder size(double width, double height) {
			this.stageHeight = height;
			this.stageWidth = width;
			return this;
		}

		public InterfaceActionRequest build() {
			return new InterfaceActionRequest(this);
		}
	}

}
