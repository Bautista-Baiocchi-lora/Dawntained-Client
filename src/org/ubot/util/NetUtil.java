package org.ubot.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

public class NetUtil {

	public static URLConnection createURLConnection(String url) {
		try {
			final URL address = new URL(url);
			final URLConnection connection = address.openConnection();
			connection.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");
			connection.setConnectTimeout(5000);
			connection.setRequestProperty("Content-Type", "image/png");
			return connection;
		} catch (IOException ex) {
			System.out.println("Error creating connection!");
			ex.printStackTrace();
		}
		return null;
	}



}