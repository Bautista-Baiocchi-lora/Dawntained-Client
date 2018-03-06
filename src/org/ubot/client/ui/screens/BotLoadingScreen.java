package org.ubot.client.ui.screens;

import org.ubot.bot.Bot;
import org.ubot.client.provider.loader.ServerLoader;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

public class BotLoadingScreen extends JPanel {

	private final Bot bot;
	private final ServerLoader loader;
	private final JProgressBar progressBar;

	public BotLoadingScreen(Bot bot, ServerLoader loader) {
		super(new BorderLayout());
		this.bot = bot;
		this.loader = loader;

		this.progressBar = new JProgressBar(0, 100);
		this.progressBar.setStringPainted(true);
		this.setPreferredSize(new Dimension(300, 50));

		this.loader.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case "progress":
						progressBar.setValue((int) evt.getNewValue());
						break;
					case "state":
						if (loader.getState() == SwingWorker.StateValue.DONE) {
							try {
								bot.launch(loader.get());
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
						}
						break;
				}
			}
		});

		add(progressBar, BorderLayout.CENTER);
		setPreferredSize(new Dimension(765, 503));
	}

	public void run() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				loader.execute();
			}
		});
	}

}
