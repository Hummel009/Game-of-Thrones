package io.gitlab.dwarfyassassin.gotucp.core.utils;

import io.gitlab.dwarfyassassin.gotucp.core.UCPCoreMod;
import net.minecraftforge.classloading.FMLForgePlugin;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

public class ASMUtils {
	public static MethodNode findMethod(ClassNode classNode, String targetMethodName, String obfTargetMethodName, String targetMethodDesc) {
		return findMethod(classNode, FMLForgePlugin.RUNTIME_DEOBF ? obfTargetMethodName : targetMethodName, targetMethodDesc);
	}

	public static MethodNode findMethod(ClassNode classNode, String targetMethodName, String targetMethodDesc) {
		for (MethodNode method : classNode.methods) {
			if (!method.name.equals(targetMethodName) || !method.desc.equals(targetMethodDesc)) {
				continue;
			}
			return method;
		}
		UCPCoreMod.log.error("Couldn't find method {} with desc {} in {}", targetMethodName, targetMethodDesc, classNode.name);
		return null;
	}

	public static void removePreviousNodes(InsnList list, AbstractInsnNode start, int amount) {
		for (int i = 0; i < amount; ++i) {
			AbstractInsnNode prevNode = start.getPrevious();
			list.remove(prevNode);
		}
	}
}

