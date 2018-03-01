package org.ubot.util.injection.asm;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ListIterator;

/**
 * Created by Ethan on 7/11/2017.
 */
public class SetSuper {
	private String newSuper;
	private ClassNode node;

	public SetSuper(String newSuper, ClassNode node) {
		this.newSuper = newSuper;
		this.node = node;
		setSuper();
	}

	private void setSuper() {
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
							min.owner = newSuper;
						}
					}
				}
			}
		}
		node.superName = newSuper;
	}
}
