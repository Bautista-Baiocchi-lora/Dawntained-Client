package org.bot.util.injection;

import org.objectweb.asm.tree.ClassNode;

/**
 * Created by Ethan on 7/9/2017.
 */

public interface Injector {

	boolean canRun(ClassNode classNode);

	void run(ClassNode classNode);
}