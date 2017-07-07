package org.bot.ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.bot.Engine;

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