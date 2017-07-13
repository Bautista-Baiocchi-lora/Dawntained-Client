package org.bot.util.injection.asm;


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
			System.out.println("Canvas was: " + node.name + " : " + node.methods.size());
			new SetSuper(newCanvasPath, node);
			System.out.println("Canvas is now: " + node.superName);


		}
	}
}
