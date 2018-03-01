package org.ubot.client.ui.scriptselector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScriptCategory extends JPanel {
    private String name;

    public ScriptCategory(String name, ArrayList<ScriptTab> scripts, int size) {
        this.name = name;
        arrangeLayouts(name, scripts, size);
    }

    private void arrangeLayouts(String name, ArrayList<ScriptTab> scripts, int size) {
        setLayout(new BorderLayout());

        add(new JLabel(name, SwingConstants.CENTER), BorderLayout.NORTH);

        PanelRotator rotator;
        int diff = size - scripts.size();
        if (diff > 0) {
            ArrayList<ScriptTab> copiedScripts = new ArrayList<ScriptTab>(scripts.size());
            for (ScriptTab t : scripts) {
                copiedScripts.add(t);
            }

            for (int i = 0; i < diff; ++i) {
                copiedScripts.add(new ScriptTab(null));
            }
            rotator = new PanelRotator(size, copiedScripts);
        } else {
            rotator = new PanelRotator(size, scripts);
        }

        add(rotator, BorderLayout.CENTER);
    }

    public String getName() {
        return name;
    }
}
