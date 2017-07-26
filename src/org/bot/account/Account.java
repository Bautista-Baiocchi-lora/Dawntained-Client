package org.bot.account;

/**
 * Created by bautistabaiocchi-lora on 7/23/17.
 */
public class Account {

	private String username;
	private String password;

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
