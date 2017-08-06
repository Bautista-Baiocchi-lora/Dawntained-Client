import java.awt.BorderLayout;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.*;

public class ScriptSelector extends JFrame
{
	private ArrayList<ScriptTab> tabs;
	private ScriptDisplay mainDisplay;
	private int categorySize;

	public ScriptSelector(ArrayList<ScriptData> data,int categorySize)
	{
		this.categorySize=categorySize;
		makeScriptTabs(data);
		makeMainDisplay();
		setUp();
	}

	private void setUp()
	{
		if(categorySize>5)
		{
			categorySize=5;
		}
		if(categorySize<2)
		{
			categorySize=2;
		}

		setSize(200*categorySize+400,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());
		add(mainDisplay,BorderLayout.CENTER);
		add(new SearchPanel(this),BorderLayout.NORTH);

		setVisible(true);
	}

	public void search(String pattern)
	{
		add(new ScriptDisplay(new ArrayList<ScriptCategory>()),BorderLayout.CENTER);
		revalidate();
		System.out.println(pattern);
	}

	private void makeScriptTabs(ArrayList<ScriptData> data)
	{
		tabs=new ArrayList<ScriptTab>();
		for(ScriptData d:data)
		{
			tabs.add(new ScriptTab(d));
		}
	}

	private void makeMainDisplay()
	{
		ArrayList<CategoryMap> maps=makeCategoryMaps(tabs);
		mainDisplay=new ScriptDisplay(makeScriptCategories(maps,categorySize));
	}

	private ArrayList<ScriptCategory> makeScriptCategories(ArrayList<CategoryMap> maps,int categorySize)
	{
		ArrayList<ScriptCategory> categories=new ArrayList<ScriptCategory>();

		for(CategoryMap map:maps)
		{
			categories.add(new ScriptCategory(map.getName(),map.getTabs(),categorySize));
		}

		return categories;
	}

	private ArrayList<CategoryMap> makeCategoryMaps(ArrayList<ScriptTab> tabs)
	{
		ArrayList<CategoryMap> maps=new ArrayList<CategoryMap>();

		for(ScriptTab tab:tabs)
		{
			String skill=tab.getData().getSkillCategory().getName();
			boolean categoryFound=false;
			for(CategoryMap map:maps)
			{
				if(map.getName().equalsIgnoreCase(skill))
				{
					map.addTab(tab);
					categoryFound=true;
				}
			}

			if(!categoryFound)
			{
				CategoryMap map=new CategoryMap(skill);
				map.addTab(tab);
				maps.add(map);
			}
		}

		return maps;
	}
}
