package org.ubot.bot.ui.scriptselector;

import org.ubot.bot.Bot;
import org.ubot.bot.BotModel;
import org.ubot.bot.script.loader.ScriptLoader;
import org.ubot.bot.script.scriptdata.ScriptData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Ethan on 7/13/2017.
 */
public class ScriptSelector extends JFrame {

	private final BotModel model;
	private static final long serialVersionUID = 1L;
	private final JTextField searchField;
	private final JComboBox<String> accounts;
	private final JPanel topPanel;
	private final JPanel scriptPanel;
	private final JScrollPane scrollPane;

	public ScriptSelector(BotModel model) {
		super("Script Selector");
		this.model = model;
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
	}


	private void loadScripts() {
		scriptPanel.removeAll();
		java.util.List<ScriptData> scripts = ScriptLoader.getScripts();

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
			panel.getButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					model.startScript(scriptData);
					ScriptSelector.this.dispose();
				}
			});
			scriptPanel.add(panel);
			realIndex++;
		}
		searchField.setText("");
		scriptPanel.setPreferredSize(new Dimension(535, (int) (Math.ceil((Double.valueOf(scriptPanel.getComponentCount()) / 3.0)) * height)));
	}


}