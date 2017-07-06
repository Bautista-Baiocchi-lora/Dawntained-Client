package org.bot.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

import org.bot.util.directory.Directory;
import org.bot.util.directory.DirectoryManager;

/**
 * Created by Kenneth on 7/29/2014.
 */
public class FileDownloader implements Runnable {

	private final String source;
	private int percentage = 0;
	private int length, written;

	public FileDownloader(String source) {
		this.source = source;
	}

	@Override
	public void run() {
		OutputStream output;
		InputStream input;
		URLConnection connection;

		try {
			connection = NetUtil.createURLConnection(source);
			length = connection.getContentLength();
			final Directory destinationDirectory = new Directory(DirectoryManager.SERVER_JARS_PATH);
			if (destinationDirectory.exists()) {
				final URLConnection savedFileConnection = destinationDirectory.toURI().toURL().openConnection();
				if (savedFileConnection.getContentLength() == length) {
					return;
				}
			} else {
				destinationDirectory.create();
			}
			output = new FileOutputStream(destinationDirectory.getPath() + File.separator + "GamePack.jar");
			input = connection.getInputStream();
			final byte[] data = new byte[1024];
			int read;
			while ((read = input.read(data)) != -1) {
				output.write(data, 0, read);
				written += read;
				percentage = (int) (((double) written / (double) length) * 100D);
			}
			output.flush();
			output.close();
			input.close();
		} catch (IOException a) {
			System.out.println("Error downloading file!");
			a.printStackTrace();
		}
	}

	public boolean isFinished() {
		return written == 0 || length == written;
	}

	public int getPercentage() {
		return percentage;
	}
}