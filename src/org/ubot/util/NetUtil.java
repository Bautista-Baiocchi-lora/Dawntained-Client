package org.ubot.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

public class NetUtil {

	public static HttpURLConnection getConnection(String url) throws IOException {
		final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; …) Gecko/20100101 Firefox/57.0";
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestProperty("User-Agent", USER_AGENT);
		return connection;
	}

	public static String getResponse(String url) throws Exception {
		HttpURLConnection con = getConnection(url);
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString().trim();
	}

	public static String postResponse(String url, String parameter) throws IOException {
		HttpURLConnection connection = getConnection(url);
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		OutputStream outputStream = connection.getOutputStream();
		outputStream.write(parameter.getBytes());
		outputStream.close();
		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString().trim();
		}
		System.out.println("HTTP request error!");
		return null;
	}

}