package org.ubot.client.ui.scriptselector;

import org.ubot.bot.script.scriptdata.ScriptData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScriptSelector extends JFrame {
    private ScriptDisplay lastDisplay;
    private ScriptSearcher searcher;
    private int categorySize;

    public ScriptSelector(ArrayList<ScriptData> data, int categorySize) {
        this.categorySize = categorySize;
        searcher = new ScriptSearcher(data);
        setUp(categorySize);
    }

    private void setUp(int categorySize) {
        if (categorySize > 5) {
            categorySize = 5;
        }
        if (categorySize < 2) {
            categorySize = 2;
        }

        setSize(200 * categorySize + 400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        ScriptDisplay mainDisplay = mainDisplay();
        add(mainDisplay, BorderLayout.CENTER);
        lastDisplay = mainDisplay;
        add(new SearchPanel(this), BorderLayout.NORTH);

        setVisible(true);
    }

    public void search(String pattern) {
        changeDisplay(new ScriptDisplay(makeScriptCategories(searcher.search(pattern))));
    }

    public void returnHome() {
        changeDisplay(mainDisplay());
    }

    private void changeDisplay(ScriptDisplay display) {
        remove(lastDisplay);
        lastDisplay = display;
        add(display, BorderLayout.CENTER);
        revalidate();
    }

    private ScriptDisplay mainDisplay() {
        return new ScriptDisplay(makeScriptCategories(searcher.getCategories()));
    }

    private ArrayList<ScriptCategory> makeScriptCategories(ArrayList<ScriptMap> maps) {
        ArrayList<ScriptCategory> categories = new ArrayList<ScriptCategory>();

        for (ScriptMap map : maps) {
            categories.add(new ScriptCategory(map.getName(), map.getTabs(), categorySize));
        }

        return categories;
    }
}
