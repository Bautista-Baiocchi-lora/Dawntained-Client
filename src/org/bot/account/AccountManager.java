package org.bot.account;

import org.bot.Engine;
import org.bot.ui.screens.clientframe.menu.logger.LogType;
import org.bot.ui.screens.clientframe.menu.logger.Logger;
import org.bot.util.directory.Directory;
import org.bot.util.directory.DirectoryManager;
import org.bot.util.directory.exceptions.InvalidDirectoryNameException;
import org.bot.util.directory.exceptions.InvalidFileNameException;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by bautistabaiocchi-lora on 7/23/17.
 */
public class AccountManager {

	private final ArrayList<Account> accounts;
	private Directory directory;

	public AccountManager() {
		this.accounts = new ArrayList<Account>();
		try {
			directory = Engine.getDirectoryManager().getRootDirectory().getSubDirectory(DirectoryManager.CACHE).getSubDirectory(DirectoryManager.ACCOUNTS);
		} catch (InvalidDirectoryNameException e) {
			e.printStackTrace();
		}
		loadAccounts();
		Logger.log("Account Manager started.", LogType.CLIENT);
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	private void loadAccounts() {
		for (File file : directory.getFiles()) {
			Properties property = new Properties();
			try (FileInputStream inputStream = new FileInputStream(file)) {
				property.load(inputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (!property.getProperty("owner").equals(Engine.getUsername())) {
				continue;
			}
			final Account account = new Account(property.getProperty("username"), property.getProperty("password"));
			for (String server : property.getProperty("servers").split(",")) {
				account.addServer(server);
			}
			accounts.add(account);
		}
		Logger.log("Accounts loaded.", LogType.DEBUG);
	}

	public void deleteAccount(Account account) {
		accounts.remove(account);
		try {
			directory.getFile(account.getUsername() + ".account").delete();
		} catch (InvalidFileNameException e) {
			e.printStackTrace();
		}
	}

	public void addAccount(Account account) {
		accounts.add(account);
		saveAccount(account);
	}

	public void updateAccount(Account account){
		saveAccount(account);
	}

	private void saveAccount(Account account) {
		Properties property = new Properties();
		property.put("owner", Engine.getUsername());
		property.put("username", account.getUsername());
		property.put("password", account.getPassword());
		StringBuilder servers = new StringBuilder();
		for (String server : account.getServers()) {
			servers.append(server + ",");
		}
		property.put("servers", servers.toString());
		try (FileOutputStream outputStream = new FileOutputStream(directory.getPath())) {
			property.store(outputStream, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
