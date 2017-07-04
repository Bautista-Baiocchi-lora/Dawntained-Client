package org.bot.ui;


import org.bot.boot.Engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ePopUpMenu extends JPopupMenu implements ActionListener {

    private static final long serialVersionUID = -5639231076995913638L;
    private final JMenu view;
    private final JCheckBoxMenuItem mouse;

    private final Engine engine = Engine.getInstance();


    public ePopUpMenu() {
        view = new JMenu("Debugging");

        mouse = new JCheckBoxMenuItem("Mouse");
        mouse.addActionListener(this);

        view.add(mouse);

        add(view);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mouse) {
            engine.setDebugMouse(!engine.isDebugMouse());
            System.out.println(engine.isDebugMouse() ? "Enabled mouse drawing." : "Disabled mouse drawing.");
        }
    }
}