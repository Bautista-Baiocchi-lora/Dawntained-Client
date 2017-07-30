package org.bot.util.injection.asm;


import org.bot.ui.screens.clientframe.menu.logger.LogType;
import org.bot.ui.screens.clientframe.menu.logger.Logger;
import org.objectweb.asm.tree.ClassNode;

/**
 * Created by Ethan on 7/11/2017.
 */
public class ModifyCanvas {
	private String newCanvasPath;
	private ClassNode node;

	public ModifyCanvas(String newCanvasPath, ClassNode node) {
		this.newCanvasPath = newCanvasPath;
		this.node = node;
		init();
	}

	public void init() {
		if (node.superName.toLowerCase().contains("canvas")) {
			Logger.log("Canvas was: " + node.name + " : " + node.methods.size(), LogType.DEBUG);
			new SetSuper(newCanvasPath, node);
			Logger.log("Canvas is now: " + node.superName, LogType.DEBUG);
		}
	}
}
