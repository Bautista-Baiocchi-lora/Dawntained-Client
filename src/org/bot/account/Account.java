package org.bot.account;

import java.util.ArrayList;

/**
 * Created by bautistabaiocchi-lora on 7/23/17.
 */
public class Account {

	private String username;
	private String password;
	private ArrayList<String> servers;

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		this.servers = new ArrayList<String>();
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

	public void addServer(String server) {
		servers.add(server);
	}

	public ArrayList<String> getServers() {
		return servers;
	}
}
