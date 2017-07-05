package org.bot.ui.login;

import org.bot.boot.Constants;
import org.bot.boot.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ethan on 7/5/2017.
 */
public class LoginFrame extends JFrame {
    /**
     * I'm shit with UI
     */

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField username;
    private JPasswordField passwordField;
    private Engine engine = Engine.getInstance();

    public LoginFrame() {
        initalize();
    }

    public void initalize() {
        setTitle(Constants.BOT_NAME + " Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 260);
        contentPane = new JPanel();
        contentPane.setForeground(Color.GRAY);
        contentPane.setBorder(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);
        username = new JTextField();
        username.setText("Ethan");
        username.setFont(new Font("Arial", Font.PLAIN, 11));
        username.setBounds(265, 62, 144, 20);
        contentPane.add(username);
        username.setColumns(10);
        JLabel userLabel = new JLabel("Username");
        userLabel.setLabelFor(username);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        userLabel.setBounds(265, 37, 122, 14);
        contentPane.add(userLabel);
        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(265, 92, 122, 14);
        contentPane.add(passLabel);
        passwordField = new JPasswordField();
        passLabel.setLabelFor(passwordField);
        passwordField.setText("123");
        passwordField.setBounds(265, 117, 144, 20);
        contentPane.add(passwordField);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String name = username.getText();
                String password = new String(passwordField.getPassword());
                if (!login(name, password)) {
                    System.out.println("Incorrect login"); //needs to be handled better
                } else {
                    dispose();
                }
            }
        });
        loginButton.setFont(new Font("Arial", Font.PLAIN, 11));
        loginButton.setBounds(265, 151, 144, 20);
        contentPane.add(loginButton);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(450, 260));
        setSize(450, 260);

    }

    private boolean login(String username, String password) {
        if (username.equalsIgnoreCase("ethan") && password.equalsIgnoreCase("123")) {
            engine.setUsername(username);
            engine.setPassword(password);
            engine.setDev(true);
            return true;
        } else if (username.equals("bautista") && password.equalsIgnoreCase("123")) {
            engine.setUsername(username);
            engine.setPassword(password);
            engine.setDev(true);
        }
        /**
         * HANDLE WEB-BASED LOGIN HERE.
         */
        return false;
    }
}
