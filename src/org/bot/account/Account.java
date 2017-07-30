package org.bot.account;

/**
 * Created by bautistabaiocchi-lora on 7/23/17.
 */
public class Account {

	private final String username;
	private final String server;
	private int sleepDuration;
	private int sleepInterval;
	private String password = "";
	private boolean breaking;

	public Account(String username, String server) {
		this.username = username;
		this.server = server;
	}

	public boolean isBreaking() {
		return breaking;
	}

	public void setBreaking(boolean breaking) {
		this.breaking = breaking;
	}

	public int getSleepInterval() {
		return sleepInterval;
	}

	public void setSleepInterval(int interval) {
		this.sleepInterval = interval;
	}

	public int getSleepDuration() {
		return sleepDuration;
	}

	public void setSleepDuration(int duration) {
		this.sleepDuration = duration;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServer() {
		return server;
	}

	@Override
	public String toString() {
		return username;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Account) {
			Account account = (Account) object;
			return account.getUsername().equals(username);
		}
		return false;
	}
}
