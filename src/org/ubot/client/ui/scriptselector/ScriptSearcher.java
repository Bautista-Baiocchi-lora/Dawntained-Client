package org.ubot.client.ui.scriptselector;

import org.ubot.bot.script.scriptdata.ScriptData;

import java.util.ArrayList;

public class ScriptSearcher {
    private ArrayList<ScriptTab> tabs;
    private ArrayList<ScriptMap> categories;
    private ArrayList<ScriptMap> authors;
    private ArrayList<ScriptMap> titles;

    public ScriptSearcher(ArrayList<ScriptData> data) {
        makeScriptTabs(data);
        makeAuthors();
        categories = makeCategories(tabs);
        makeTitles();
    }

    public ArrayList<ScriptMap> search(String target) {
        ArrayList<ScriptTab> resultTabs = new ArrayList<ScriptTab>();
        target = target.toLowerCase();
        addScripts(checkMaps(categories, target), resultTabs);
        addScripts(checkMaps(authors, target), resultTabs);
        addScripts(checkMaps(titles, target), resultTabs);
        return makeCategories(resultTabs);
    }

    private void addScripts(ArrayList<ScriptTab> source, ArrayList<ScriptTab> target) {
        for (ScriptTab tab : source) {
            target.add(tab);
        }
    }

    private ArrayList<ScriptTab> checkMaps(ArrayList<ScriptMap> maps, String target) {
        ArrayList<ScriptTab> results = new ArrayList<ScriptTab>();
        for (ScriptMap map : maps) {
            if (map.getName().toLowerCase().contains(target)) {
                addScripts(map.getTabs(), results);
            }
        }
        return results;
    }

    private void makeScriptTabs(ArrayList<ScriptData> data) {
        tabs = new ArrayList<ScriptTab>();
        for (ScriptData d : data) {
            tabs.add(new ScriptTab(d));
        }
    }

    private ArrayList<ScriptMap> makeCategories(ArrayList<ScriptTab> tabs) {
        ArrayList<ScriptMap> categories = new ArrayList<ScriptMap>();

        for (ScriptTab tab : tabs) {
            if (tab != null) {
                String skill = tab.getData().getSkillCategory().getName();
                boolean categoryFound = false;
                for (ScriptMap map : categories) {
                    if (map.getName().equalsIgnoreCase(skill)) {
                        map.addTab(tab);
                        categoryFound = true;
                    }
                }

                if (!categoryFound) {
                    ScriptMap map = new ScriptMap(skill);
                    map.addTab(tab);
                    categories.add(map);
                }
            }
        }

        return categories;
    }

    private void makeAuthors() {
        authors = new ArrayList<ScriptMap>();

        for (ScriptTab tab : tabs) {
            String author = tab.getData().getAuthor();
            boolean categoryFound = false;
            for (ScriptMap map : authors) {
                if (map.getName().equalsIgnoreCase(author)) {
                    map.addTab(tab);
                    categoryFound = true;
                }
            }

            if (!categoryFound) {
                ScriptMap map = new ScriptMap(author);
                map.addTab(tab);
                authors.add(map);
            }
        }
    }

    private void makeTitles() {
        titles = new ArrayList<ScriptMap>();

        for (ScriptTab tab : tabs) {
            String title = tab.getData().getName();
            boolean categoryFound = false;
            for (ScriptMap map : titles) {
                if (map.getName().equalsIgnoreCase(title)) {
                    map.addTab(tab);
                    categoryFound = true;
                }
            }

            if (!categoryFound) {
                ScriptMap map = new ScriptMap(title);
                map.addTab(tab);
                titles.add(map);
            }
        }
    }

    public ArrayList<ScriptTab> getTabs() {
        return tabs;
    }

    public ArrayList<ScriptMap> getCategories() {
        return categories;
    }

    public ArrayList<ScriptMap> getAuthors() {
        return authors;
    }
}
