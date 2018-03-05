package org.ubot.client.ui;

import org.ubot.client.Client;
import org.ubot.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Ethan on 3/4/2018.
 */
public class BotToolBar extends JToolBar {
    private Client client;
    private JButton newTabButton = new JButton();
    private JButton settingsButton = new JButton();
    private JPopupMenu settings = new JPopupMenu("Settings");
    private JMenu debugs = new JMenu("Debugs");
    private JMenuItem debugInventory = new JMenuItem("Inventory");
    private JMenuItem debugNPCS = new JMenuItem("NPCs");
    private JMenuItem debugObjects = new JMenuItem("Objects");
    private JMenuItem debugPlayers = new JMenuItem("Players");
    private JMenuItem debugGameInfo = new JMenuItem("Information");
    private JMenuItem interfaceExplorer = new JMenuItem("Interface Explorer");
    private JMenuItem exit = new JMenuItem("Exit");

    public BotToolBar(Client client) {
        this.client = client;
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(765, 24));
        setFloatable(false);
        debugs.add(debugNPCS);
        debugs.add(debugPlayers);
        debugs.add(debugObjects);
        debugs.add(debugGameInfo);
        debugs.add(debugInventory);
        debugs.add(interfaceExplorer);
        settings.add(debugs);
        settings.addSeparator();
        settings.add(exit);
        settingsButton.setIcon(Utilities.getIcon("resources/settings.png"));
        settingsButton.setContentAreaFilled(false);
        settingsButton.setRolloverEnabled(true);
        settingsButton.setRolloverIcon(Utilities.getIcon("resources/settings_hover.png"));
        settingsButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                settings.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        exit.addActionListener(e -> System.exit(0));
        updateComponents(null);

    }

    public void updateComponents(java.util.List<JComponent> components) {
        removeAll();
        if (components != null) {
            for (JComponent c : components) {
                add(c);
            }
        }
        add(Box.createHorizontalGlue());
        add(settingsButton);
        revalidate();
    }

}
