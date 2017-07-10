package org.bot.util.injection;

import org.objectweb.asm.tree.ClassNode;

/**
 * Created by Ethan on 7/9/2017.
 */

public interface Injector {

    public abstract boolean canRun(ClassNode classNode);

    public abstract void run(ClassNode classNode);
}