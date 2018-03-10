package org.ubot.client.account;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ubot.client.ui.logger.Logger;
import org.ubot.util.directory.DirectoryManager;
import org.ubot.util.directory.exceptions.InvalidDirectoryNameException;
import org.ubot.util.directory.exceptions.InvalidFileNameException;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class AccountManager {

	private final ArrayList<Account> accounts;
	private final String username, accountKey;
	private File cache;

	public AccountManager(String username, String accountKey) {
		this.username = username;
		this.accountKey = accountKey;
		this.accounts = new ArrayList<>();
		try {
			cache = DirectoryManager.getInstance().getRootDirectory().getSubDirectory(DirectoryManager.CACHE).getSubDirectory(DirectoryManager.ACCOUNTS).getFile(username + ".ac");
			loadAccounts();
		} catch (InvalidDirectoryNameException e) {
			e.printStackTrace();
		} catch (InvalidFileNameException e) {
			cache = new File(DirectoryManager.ACCOUNTS_PATH + File.separator + username + ".ac");
			try {
				cache.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		Logger.log("Account Manager started.");
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void loadAccounts() {
		Properties property = new Properties();
		try (FileInputStream inputStream = new FileInputStream(cache)) {
			property.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		accounts.clear();
		for (String accountName : property.stringPropertyNames()) {
			String accountJson = property.getProperty(accountName);
			final JSONParser parser = new JSONParser();
			Account account = null;
			try {
				account = Account.wrap((JSONObject) parser.parse(accountJson));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (account == null) {
				Logger.logException("Account Save Corrrupted: " + username);
				continue;
			}
			accounts.add(account);
		}
		Logger.log("Accounts loaded.");
	}

	public void deleteAccount(Account account) {
		accounts.remove(account);
		loadAccounts();
		Logger.log("Account deleted.");
	}

	public void addAccount(Account account) {
		accounts.add(account);
		saveAccounts();
		Logger.log("Account saved.");
	}

	private void saveAccounts() {
		Properties property = new Properties();
		for (Account account : accounts) {
			property.put(account.getUsername(), account.toJSON().toJSONString());
		}
		try {
			if (!cache.exists()) {
				if (cache.createNewFile()) {
					Logger.log("Accounts save created.");
				}
			}
			try (FileOutputStream outputStream = new FileOutputStream(cache, false)) {
				property.store(outputStream, null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}