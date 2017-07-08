package org.bot.ui.management;

import org.bot.provider.ServerProvider;

public class InterfaceActionRequest {

	private ServerProvider provider;
	private final InterfaceAction action;

	private InterfaceActionRequest(ActionBuilder builder) {
		action = builder.action;
		provider = builder.provider;
	}

	public InterfaceAction getAction() {
		return action;
	}

	public ServerProvider getProvider() {
		return provider;
	}

	public static class ActionBuilder {
		private ServerProvider provider;
		private final InterfaceAction action;

		public ActionBuilder(InterfaceAction action) {
			this.action = action;
		}

		public ActionBuilder provider(ServerProvider provider) {
			this.provider = provider;
			return this;
		}

		public InterfaceActionRequest build() {
			return new InterfaceActionRequest(this);
		}
	}

}
