package org.bot.ui.screens.clientframe.menu.accountmanager;

import org.bot.Engine;
import org.bot.account.Account;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by bautistabaiocchi-lora on 7/29/17.
 */
public class AccountManagerUI extends JFrame implements ActionListener, Manager {

	private JList<Account> accountJList;
	private DefaultListModel<Account> listModel;
	private JButton addAccount, deleteAccount;
	private AccountPreviewPanel previewPanel;

	public AccountManagerUI() {
		super("uBot Account Manager");
		configure();
		pack();
		setVisible(true);
	}

	private void configure() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		accountJList = new JList<Account>(listModel = new DefaultListModel<Account>());
		accountJList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					showAccountPanel(accountJList.getSelectedValue());
					updateFrame();
				}
			}
		});
		accountJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		accountJList.setPreferredSize(new Dimension(300, 240));
		accountJList.setFont(new Font("TimesRoman", Font.BOLD, 18));
		refreshList();
		add(accountJList, BorderLayout.CENTER);
		addAccount = new JButton("Add");
		addAccount.addActionListener(this);
		deleteAccount = new JButton("Delete");
		deleteAccount.addActionListener(this);
		final JPanel buttonPanel = new JPanel();
		buttonPanel.add(addAccount);
		buttonPanel.add(deleteAccount);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void refreshList() {
		if (!listModel.isEmpty()) {
			listModel.clear();
		}
		for (Account account : Engine.getAccountManager().getAccounts()) {
			if (account.getServer().equals(Engine.getServerProvider().getManifest().serverName())) {
				listModel.addElement(account);
			}
		}
	}

	private void showAccountPanel(Account account) {
		if (previewPanel != null) {
			remove(previewPanel);
		}
		previewPanel = new AccountPreviewPanel(account);
		previewPanel.registerManager(AccountManagerUI.this);
		add(previewPanel, BorderLayout.EAST);
	}

	private void updateFrame() {
		this.pack();
		this.repaint();
		this.revalidate();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Account account;
		switch (e.getActionCommand()) {
			case "Add":
				account = new Account(JOptionPane.showInputDialog("Username?"), Engine.getServerProvider().getManifest().serverName());
				Engine.getAccountManager().addAccount(account);
				listModel.addElement(account);
				break;
			case "Delete":
				account = accountJList.getSelectedValue();
				if (previewPanel != null) {
					remove(previewPanel);
					previewPanel = null;
					updateFrame();
				}
				listModel.removeElement(account);
				Engine.getAccountManager().deleteAccount(account);
				break;
		}
	}

	@Override
	public void processActionRequest(final InterfaceActionRequest request) {
		Engine.getAccountManager().updateAccount(request.getAccount());
	}
}
