package org.bot.account;

import org.bot.Engine;
import org.bot.util.directory.DirectoryManager;
import org.bot.util.directory.exceptions.InvalidDirectoryNameException;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by bautistabaiocchi-lora on 7/23/17.
 */
public class AccountManager {

	private final ArrayList<Account> accounts;

	public AccountManager() {
		this.accounts = new ArrayList<Account>();
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	private void loadAccounts() {
		try {
			for (File file : Engine.getDirectoryManager().getRootDirectory().getSubDirectory(DirectoryManager.CACHE).getFiles()) {

			}
		} catch (InvalidDirectoryNameException e) {
			e.printStackTrace();
		}
	}

	public void deleteAccount() {
		//delete existing account
	}

	public void saveAccount() {
		//save new account
	}

}
