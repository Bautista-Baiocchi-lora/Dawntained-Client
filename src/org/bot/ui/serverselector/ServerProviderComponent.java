package org.bot.ui.serverselector;

import org.bot.boot.Engine;
import org.bot.server.ServerProvider;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ServerProviderComponent extends JComponent {

    private static final long serialVersionUID = 8365360607486641452L;
    private final ServerSelector instance;
    private final Font NAME_FONT = new Font("Dialog", Font.BOLD, 18);
    private final Font INFO_FONT = new Font("Dialog", Font.ITALIC, 13);
    private final Font DESCRIPTION_FONT = new Font("Dialog", Font.PLAIN, 11);
    private final Border BORDER = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
            "Description");
    private final Border COMPONENT_BORDER = BorderFactory.createLineBorder(Color.BLACK);
    private final ServerProvider provider;
    private final JLabel author, serverName, type, version;

    public ServerProviderComponent(ServerProvider provider, ServerSelector instance) {
        super();
        this.provider = provider;
        this.instance = instance;
        setBorder(COMPONENT_BORDER);
        setLayout(new FlowLayout());
        author = new JLabel("Author: " + provider.getManifest().author());
        author.setFont(INFO_FONT);
        serverName = new JLabel(provider.getManifest().serverName());
        serverName.setFont(NAME_FONT);
        type = new JLabel("Type: " + provider.getManifest().type().getSimpleName());
        type.setFont(INFO_FONT);
        version = new JLabel("Version: " + provider.getManifest().version());
        version.setFont(INFO_FONT);
        setPreferredSize(new Dimension(400, 150));
        add(serverName);
        add(author);
        add(type);
        add(version);
        setToolTipText(provider.getManifest().desc());
        setBackground(Color.GRAY);
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Engine.getInstance().setServerProvider(provider);
                Engine.getInstance().getServerLoader().executeServer();
                instance.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getParent().getWidth(),
                (serverName.getHeight() + type.getHeight() + version.getHeight() + author.getHeight()));
    }

}
