package org.ubot.client.ui.screens;

import org.ubot.bot.Bot;
import org.ubot.client.provider.ServerProvider;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

public class BotLoadingScreen extends JPanel {

	private final Bot bot;
	private final ServerProvider provider;
	private final JProgressBar progressBar;

	public BotLoadingScreen(Bot bot, ServerProvider provider) {
		super(new BorderLayout());
		this.bot = bot;
		this.provider = provider;

		setBorder(BorderFactory.createLoweredBevelBorder());

		this.progressBar = new JProgressBar(0, 100);
		this.progressBar.setStringPainted(true);
		this.setPreferredSize(new Dimension(300, 50));

		this.provider.getLoader().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case "progress":
						progressBar.setValue((int) evt.getNewValue());
						break;
					case "state":
						if (provider.getLoader().getState() == SwingWorker.StateValue.DONE) {
							try {
								bot.launch(provider.getLoader().get());
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
				provider.getLoader().execute();
			}
		});
	}

}
