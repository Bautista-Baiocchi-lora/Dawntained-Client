package org.bot.ui.scriptselector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScriptDisplay extends JScrollPane {
    public ScriptDisplay(ArrayList<ScriptCategory> categories) {
        arrangeLayout(categories);
    }

    private void arrangeLayout(ArrayList<ScriptCategory> categories) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new GridLayout(0, 1));

        for (ScriptCategory category : categories) {
            wrapper.add(category);
        }

        setLayout(new ScrollPaneLayout());
        getViewport().add(wrapper);
    }
}
