package org.ubot.client.ui.scriptselector;

import org.ubot.bot.Bot;
import org.ubot.bot.script.loader.ScriptLoader;
import org.ubot.bot.script.scriptdata.ScriptData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ScriptSelector extends JFrame {


	private static final long serialVersionUID = 1L;
	private JTextField searchField;
	private JComboBox<String> accounts;
	private JPanel topPanel;
	private JPanel scriptPanel;
	private JScrollPane scrollPane;
	private final ScriptLoader scriptLoader;
	private final Bot bot;

	public ScriptSelector(Bot bot, ScriptLoader scriptLoader) {
		super("Script Selector");
		this.bot = bot;
		this.scriptLoader = scriptLoader;
		setResizable(false);
		searchField = new JTextField(20);
		searchField.setForeground(Color.LIGHT_GRAY);
		searchField.setText("Search");
		searchField.setMaximumSize(new Dimension(100, 30));
		searchField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
				searchField.setForeground(Color.BLACK);
				searchField.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				super.focusLost(e);
				searchField.setForeground(Color.LIGHT_GRAY);
				searchField.setText("Search");
			}
		});
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				super.keyTyped(e);
			}
		});

		accounts = new JComboBox<>();


		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.add(accounts);
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(searchField);

		scriptPanel = new JPanel();
		scriptPanel.setLayout(null);

		scrollPane = new JScrollPane(scriptPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);


		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(535, 405);
		loadScripts();
		setVisible(true);
	}


	private void loadScripts() {
		scriptPanel.removeAll();
		java.util.List<ScriptData> scripts = scriptLoader.getScripts();

		final int width = 170;
		final int height = 115;
		final int spacing = 3;
		final int scriptPerRow = 3;
		int realIndex = 0;
		for (int scriptIndex = 0; scriptIndex < scripts.size(); scriptIndex++) {
			final ScriptData scriptData = scripts.get(scriptIndex);
			final ScriptPanel panel = new ScriptPanel(scriptData);
			int col = realIndex / scriptPerRow;
			int row = realIndex - (col * scriptPerRow);
			int x = row * width + spacing;
			int y = col * height + spacing;
			panel.setBounds(x, y, width, height);
			panel.getButton().addActionListener(e -> {
				try {
					bot.startScript(scriptLoader.loadScript(scriptData), scriptData);
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				}
				dispose();
			});
			scriptPanel.add(panel);
			realIndex++;
		}
		searchField.setText("");
		scriptPanel.setPreferredSize(new Dimension(535, (int) (Math.ceil((Double.valueOf(scriptPanel.getComponentCount()) / 3.0)) * height)));

	}
}
