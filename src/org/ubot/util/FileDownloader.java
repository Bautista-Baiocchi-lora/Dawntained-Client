package org.ubot.util;

import java.io.*;
import java.net.URLConnection;


public class FileDownloader implements Runnable {

	private final String fileName, source, filePath;
	private volatile double progress = 0;
	private int length, written;
	private File savedFile;

	public FileDownloader(String source, String filePath, String fileName) {
		this.source = source;
		this.fileName = fileName;
		this.filePath = filePath;
	}

	@Override
	public void run() {
		OutputStream output;
		InputStream input;
		URLConnection connection;
		try {
			connection = NetUtil.getConnection(source);
			length = connection.getContentLength();
			savedFile = new File(filePath + File.separator + fileName);
			if (savedFile != null && savedFile.exists()) {
				final URLConnection savedFileConnection = savedFile.toURI().toURL().openConnection();
				if (savedFileConnection.getContentLength() == length) {
					return;
				}
			}
			output = new FileOutputStream(savedFile);
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

	public File getDownloadedFile() {
		return savedFile;
	}

	public boolean isFinished() {
		return written == 0 || length == written;
	}

}