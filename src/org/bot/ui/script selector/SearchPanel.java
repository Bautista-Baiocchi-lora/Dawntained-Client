import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPanel extends JPanel
{
	private JTextField field;
	private JButton button;
	
	public SearchPanel(ScriptSelector sel)
	{
		GridBagLayout layout=new GridBagLayout();
	
		setLayout(new GridBagLayout());
		field=new JTextField();
		button=new JButton("Search");

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sel.search(field.getText());
				field.setText("");
			}
		});

		JButton home=new JButton("Home");
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sel.returnHome();
			}
		});
		
		GridBagConstraints fieldConst=new GridBagConstraints();
		fieldConst.weightx=1;
		fieldConst.fill=GridBagConstraints.HORIZONTAL;
		add(field,fieldConst);
		add(button);
		add(home);
	}
}
