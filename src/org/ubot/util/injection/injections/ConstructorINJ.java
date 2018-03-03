package org.ubot.util.injection.injections;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ListIterator;

public class ConstructorINJ {

	private String newConstructor;
	private ClassNode node;

	public ConstructorINJ(String newConstructor, ClassNode node) {
		this.newConstructor = newConstructor;
		this.node = node;
		set();
	}

	private void set() {
		String replacedSuperName = "";
		if (node.superName != "") {
			replacedSuperName = node.superName;
		}
		if (replacedSuperName != "") {
			ListIterator<?> mli = node.methods.listIterator();
			while (mli.hasNext()) {
				MethodNode mn = (MethodNode) mli.next();
				ListIterator<?> ili = mn.instructions.iterator();
				while (ili.hasNext()) {
					AbstractInsnNode ain = (AbstractInsnNode) ili.next();
					if (ain.getOpcode() == Opcodes.INVOKESPECIAL) {
						MethodInsnNode min = (MethodInsnNode) ain;
						if (min.owner.equals(replacedSuperName)) {
							min.owner = newConstructor;
						}
					}
				}
			}
		}
		node.superName = newConstructor;
	}
}
