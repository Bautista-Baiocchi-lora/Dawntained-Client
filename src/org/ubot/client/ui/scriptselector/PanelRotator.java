package org.ubot.client.ui.scriptselector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelRotator extends JPanel {
    private JButton nextButton;
    private JButton prevButton;
    private JPanel container;
    private CircularList<? extends JPanel> contents;
    private int size;

    public PanelRotator(int size, ArrayList<? extends JPanel> contents) {
        this.size = size;
        this.contents = new CircularList<>(contents, size);
        arrangeLayout();
        fillContainer();
        addButtonListeners();
    }

    private void arrangeLayout() {
        setLayout(new BorderLayout());

        nextButton = new JButton("Next");
        add(nextButton, BorderLayout.EAST);

        prevButton = new JButton("Prev");
        add(prevButton, BorderLayout.WEST);

        container = new JPanel();
        container.setLayout(new GridLayout(1, size, 3, 3));
        add(container, BorderLayout.CENTER);
    }

    private void fillContainer() {
        container.removeAll();
        for (JPanel panel : contents.getWindow()) {
            container.add(panel);
        }
        container.revalidate();
        container.repaint();
    }

    private void addButtonListeners() {
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contents.spinRight(size);
                fillContainer();
            }
        });

        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contents.spinLeft(size);
                fillContainer();
            }
        });
    }
}
