package org.ubot.util.injection;

import org.objectweb.asm.tree.ClassNode;

/**
 * Created by Ethan on 7/9/2017.
 */

public interface Injector {

	boolean condition(ClassNode classNode);

	void inject(ClassNode classNode);
}