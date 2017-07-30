package org.bot.ui.screens.clientframe.menu.accountmanager;

import org.bot.account.Account;
import org.bot.ui.management.InterfaceAction;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manageable;
import org.bot.ui.management.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by bautistabaiocchi-lora on 7/29/17.
 */
public class AccountPreviewPanel extends JPanel implements Manageable, ActionListener {


	private final ArrayList<Manager> managers = new ArrayList<Manager>();
	private final GridBagConstraints constraints;
	private Account account;
	private JTextField password;
	private JButton save;
	private JCheckBox breaking;
	private JSpinner durationSpinner, intervalSpinner;
	private SpinnerNumberModel durationModel, intervalModel;
	private JLabel usernameLabel;

	public AccountPreviewPanel(Account account) {
		this.account = account;
		this.constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.ipady = 15;
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(300, 240));
		configure();
	}

	private void configure() {
		addComponent(1, 1, 1, new JLabel("Username: "));
		usernameLabel = new JLabel(account.getUsername());
		addComponent(2, 1, 1, usernameLabel);
		addComponent(1, 2, 1, new JLabel("Password: "));
		password = new JTextField(account.getPassword());
		addComponent(2, 2, 1, password);
		breaking = new JCheckBox("Breaking");
		breaking.setSelected(account.isBreaking());
		breaking.addActionListener(this);
		addComponent(1, 3, 2, breaking);
		durationSpinner = new JSpinner(durationModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		durationModel.setValue(account.getSleepDuration());
		addComponent(1, 4, 1, new JLabel("Sleep Duration: "));
		addComponent(2, 4, 1, durationSpinner);
		intervalSpinner = new JSpinner(intervalModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		intervalModel.setValue(account.getSleepInterval());
		addComponent(1, 5, 1, new JLabel("Sleep Interval: "));
		addComponent(2, 5, 1, intervalSpinner);
		toggleSleepComponents();
		save = new JButton("Save");
		save.addActionListener(this);
		addComponent(1, 6, 2, save);
	}

	private void addComponent(final int x, final int y, final int width, final JComponent comp) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		comp.setPreferredSize(new Dimension(150, 60));
		add(comp, constraints);
	}

	private void toggleSleepComponents() {
		intervalSpinner.setEnabled(breaking.isSelected());
		durationSpinner.setEnabled(breaking.isSelected());
	}

	@Override
	public void requestAction(InterfaceActionRequest action) {
		for (Manager manager : managers) {
			manager.processActionRequest(action);
		}
	}

	@Override
	public void registerManager(Manager manager) {
		managers.add(manager);
	}


	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource().equals(breaking)) {
			toggleSleepComponents();
		} else {
			account.setPassword(password.getText());
			account.setBreaking(breaking.isSelected());
			account.setSleepInterval(intervalModel.getNumber().intValue());
			account.setSleepDuration(durationModel.getNumber().intValue());
			requestAction(new InterfaceActionRequest.ActionBuilder(InterfaceAction.UPDATE_ACCOUNT).account(account).build());
		}
	}
}
