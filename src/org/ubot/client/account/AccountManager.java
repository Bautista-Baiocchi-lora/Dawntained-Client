package org.ubot.client.account;

import org.ubot.bot.ui.logger.LogType;
import org.ubot.bot.ui.logger.Logger;
import org.ubot.util.directory.Directory;
import org.ubot.util.directory.DirectoryManager;
import org.ubot.util.directory.exceptions.InvalidDirectoryNameException;
import org.ubot.util.directory.exceptions.InvalidFileNameException;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by bautistabaiocchi-lora on 7/23/17.
 */
public class AccountManager {

	private final ArrayList<Account> accounts;
	private final String username, accountKey;
	private Directory directory;

	public AccountManager(String username, String accountKey) {
		this.username = username;
		this.accountKey = accountKey;
		try {
			directory = DirectoryManager.getInstance().getRootDirectory().getSubDirectory(DirectoryManager.CACHE).getSubDirectory(DirectoryManager.ACCOUNTS);
		} catch (InvalidDirectoryNameException e) {
			e.printStackTrace();
		}
		this.accounts = loadAccounts();
		Logger.log("Account Manager started.", LogType.CLIENT);
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	private final ArrayList<Account> loadAccounts() {
		final ArrayList<Account> accounts = new ArrayList<>();
		for (File file : directory.getFiles()) {
			Properties property = new Properties();
			try (FileInputStream inputStream = new FileInputStream(file)) {
				property.load(inputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (!property.getProperty("owner").equals(username)) {
				continue;
			}
			final Account account = new Account(property.getProperty("username"), property.getProperty("server"));
			account.setPassword(property.getProperty("password"));
			account.setBreaking(Boolean.parseBoolean(property.getProperty("breaking")));
			account.setSleepDuration(Integer.parseInt(property.getProperty("sleepduration")));
			account.setSleepInterval(Integer.parseInt(property.getProperty("sleepinterval")));
			accounts.add(account);
		}
		Logger.log("Accounts loaded.", LogType.DEBUG);
		return accounts;
	}

	public void deleteAccount(Account account) {
		accounts.remove(account);
		try {
			directory.getFile(account.getUsername() + ".account").delete();
		} catch (InvalidFileNameException e) {
			e.printStackTrace();
		}
		Logger.log("Account deleted.", LogType.CLIENT);
	}

	public void addAccount(Account account) {
		accounts.add(account);
		saveAccount(account);
		Logger.log("Account saved.", LogType.CLIENT);
	}

	public void updateAccount(Account account) {
		saveAccount(account);
		Logger.log("Account updated.", LogType.CLIENT);
	}

	private void saveAccount(Account account) {
		Properties property = new Properties();
		property.put("owner", username);
		property.put("username", account.getUsername());
		property.put("password", account.getPassword());
		property.put("server", account.getServer());
		property.put("breaking", String.valueOf(account.isBreaking()));
		property.put("sleepduration", String.valueOf(account.getSleepDuration()));
		property.put("sleepinterval", String.valueOf(account.getSleepInterval()));
		File accountFile = new File(DirectoryManager.ACCOUNTS_PATH + File.separator + account.getUsername() + ".account");
		try {
			if (!accountFile.exists()) {
				if (accountFile.createNewFile()) {
					Logger.log("Created " + account.getUsername() + " account file.", LogType.DEBUG);
				}
			}
			try (FileOutputStream outputStream = new FileOutputStream(accountFile)) {
				property.store(outputStream, null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
