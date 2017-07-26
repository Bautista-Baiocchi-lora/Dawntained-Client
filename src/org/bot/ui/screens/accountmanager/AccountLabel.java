package org.bot.ui.screens.accountmanager;

import javafx.scene.control.Label;
import org.bot.account.Account;
import org.bot.ui.BotUI;

/**
 * Created by bautistabaiocchi-lora on 7/23/17.
 */
public class AccountLabel extends Label {

	private final AccountInformationTab informationTab;

	public AccountLabel(Account account) {
		super(account.getUsername());
		this.informationTab = new AccountInformationTab(account);
		informationTab.registerManager(BotUI.getInstance());
	}

	public AccountInformationTab getInformationTab() {
		return informationTab;
	}


}
