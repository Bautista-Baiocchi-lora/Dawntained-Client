import java.util.ArrayList;

public class ScriptMap
{
    private String name;
    private ArrayList<ScriptTab> tabs;

    public ScriptMap(String name)
    {
        this.name=name;
        tabs=new ArrayList<ScriptTab>();
    }

    public void addTab(ScriptTab tab)
    {
        tabs.add(tab);
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<ScriptTab> getTabs()
    {
        return tabs;
    }
}