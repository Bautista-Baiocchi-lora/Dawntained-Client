import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;

public class ScriptCategory extends JPanel
{	
	private String name;

	public ScriptCategory(String name,ArrayList<ScriptTab> scripts,int size)
	{
		this.name=name;
		arrangeLayouts(name,scripts,size);
	}
	
	private void arrangeLayouts(String name,ArrayList<ScriptTab> scripts,int size)
	{
		setLayout(new BorderLayout());
		
		add(new JLabel(name, SwingConstants.CENTER),BorderLayout.NORTH);

		PanelRotator rotator=new PanelRotator(size,scripts);
		add(rotator, BorderLayout.CENTER);
	}

	public String getName()
	{
		return name;
	}
}
