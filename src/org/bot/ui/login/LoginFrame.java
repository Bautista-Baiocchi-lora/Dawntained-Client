/*
 * Created by JFormDesigner on Wed Jul 05 22:10:58 CDT 2017
 */

package org.bot.ui.login;

import org.bot.boot.Engine;
import org.bot.util.directory.DirectoryManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Ethan
 */
public class LoginFrame extends JFrame {
    private JLabel userLabel;
    private JTextField loginText;
    private JPasswordField passwordText;
    private JLabel passLabel;
    private JLabel response;
    private JButton loginButton;
    private Engine engine = Engine.getInstance();

    public LoginFrame() {
        engine.setDirectoryManager(new DirectoryManager());
        initialize();
    }

    private void initialize() {
        loginText = new JTextField();
        passwordText = new JPasswordField();
        passLabel = new JLabel();
        response = new JLabel();
        loginButton = new JButton();
        userLabel = new JLabel();

        setTitle("Login");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        userLabel.setText("Username:");
        contentPane.add(userLabel);
        userLabel.setBounds(10, 27, 75, userLabel.getPreferredSize().height);
        contentPane.add(loginText);
        loginText.setBounds(74, 23, 115, loginText.getPreferredSize().height);
        contentPane.add(passwordText);
        passwordText.setBounds(75, 64, 115, passwordText.getPreferredSize().height);

        passLabel.setText("Password:");
        contentPane.add(passLabel);
        passLabel.setBounds(10, 67, 75, passLabel.getPreferredSize().height);

        response.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(response);
        response.setBounds(25, 98, 170, 16);

        loginButton.setText("Attempt Login");
        contentPane.add(loginButton);
        loginButton.setBounds(5, 125, 200, loginButton.getPreferredSize().height);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String name = loginText.getText();
                String password = new String(passwordText.getPassword());
                if (!login(name, password)) {
                    response.setText("Invalid Password!");
                } else {
                    dispose();
                }
            }
        });
        {
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        setSize(250, 210);
        setLocationRelativeTo(getOwner());
    }

    private boolean login(String user, String pass) {
        if (user.equalsIgnoreCase("ethan") && pass.equalsIgnoreCase("123")) {
            engine.setUsername(user);
            engine.setDeveloper(true);
            return true;
        } else if (user.equals("bautista") && pass.equalsIgnoreCase("123")) {
            engine.setUsername(user);
            engine.setDeveloper(true);
        }
        /**
         * HANDLE WEB-BASED LOGIN HERE.
         */
        return false;
    }
}
