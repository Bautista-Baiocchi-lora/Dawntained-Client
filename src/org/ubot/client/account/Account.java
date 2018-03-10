package org.ubot.client.account;

import org.json.simple.JSONObject;

public class Account {

	private String username;
	private String server;
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

	public void setUsername(String username) {
		this.username = username;
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

	public void setServer(String server) {
		this.server = server;
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", username);
		jsonObject.put("password", password);
		jsonObject.put("servers", server);
		jsonObject.put("duration", sleepDuration);
		jsonObject.put("interval", sleepInterval);
		jsonObject.put("breaking", breaking);
		return jsonObject;
	}

	public static Account wrap(JSONObject accountJson) {
		final Account account = new Account((String) accountJson.get("username"), (String) accountJson.get("server"));
		account.setBreaking(Boolean.parseBoolean((String) accountJson.get("breaking")));
		account.setPassword((String) accountJson.get("password"));
		account.setSleepDuration((int) (Long.valueOf((String) accountJson.get("duration")) / 1000));
		account.setSleepInterval((int) (Long.valueOf((String) accountJson.get("interval")) / 1000));
		return account;
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