package org.bot.util;

import org.bot.util.directory.Directory;
import org.bot.util.directory.DirectoryManager;

import java.io.*;
import java.net.URLConnection;


public class FileDownloader implements Runnable {

	private final String fileName;
	private final String source;
	private volatile double progress = 0;
	private int length, written;
	private Directory path;

	public FileDownloader(String source, String fileName) {
		this.source = source;
		this.fileName = fileName;
	}

	@Override
	public void run() {
		OutputStream output;
		InputStream input;
		URLConnection connection;
		try {
			connection = NetUtil.createURLConnection(source);
			length = connection.getContentLength();
			final Directory destinationDirectory = path = new Directory(DirectoryManager.SERVER_JARS_PATH);
			if (destinationDirectory.exists()) {
				final URLConnection savedFileConnection = destinationDirectory.toURI().toURL().openConnection();
				if (savedFileConnection.getContentLength() == length) {
					return;
				}
			} else {
				destinationDirectory.create();
			}
			output = new FileOutputStream(destinationDirectory.getPath() + File.separator + fileName + ".jar");
			input = connection.getInputStream();
			final byte[] data = new byte[1024];
			int read;
			while ((read = input.read(data)) != -1) {
				output.write(data, 0, read);
				written += read;
				progress = ((double) written / (double) length);
			}
			output.flush();
			output.close();
			input.close();
		} catch (IOException a) {
			System.out.println("Error downloading file!");
			a.printStackTrace();
		}
	}

	public double getProgress() {
		return progress;
	}

	public String getArchivePath() {
		return path.getPath();
	}

	public boolean isFinished() {
		return written == 0 || length == written;
	}

}