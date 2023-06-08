/*
 * Decompiled with CFR 0.148.
 *
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package io.gitlab.dwarfyassassin.gotucp.core.patches;

import io.gitlab.dwarfyassassin.gotucp.core.UCPCoreMod;
import io.gitlab.dwarfyassassin.gotucp.core.patches.base.Patcher;
import io.gitlab.dwarfyassassin.gotucp.core.utils.ASMUtils;
import org.objectweb.asm.tree.*;

public class FMLPatcher extends Patcher {
	public FMLPatcher() {
		super("FML");
		classes.put("cpw.mods.fml.common.LoadController", this::patchLoadController);
	}

	private void patchLoadController(ClassNode classNode) {
		MethodNode method = ASMUtils.findMethod(classNode, "buildModList", "(Lcpw/mods/fml/common/event/FMLLoadEvent;)V");
		if (method == null) {
			return;
		}
		method.instructions.insert(new MethodInsnNode(184, "io/gitlab/dwarfyassassin/gotucp/core/hooks/PreMCHooks", "postFMLLoad", "()V", false));
		UCPCoreMod.log.info("Patched the FML load controller.");
	}

	private void patchModContainer(ClassNode classNode) {
		MethodNode method = ASMUtils.findMethod(classNode, "bindMetadata", "(Lcpw/mods/fml/common/MetadataCollection;)V");
		if (method == null) {
			return;
		}
		for (AbstractInsnNode node : method.instructions.toArray()) {
			if (node instanceof FieldInsnNode) {
				FieldInsnNode fieldNode = (FieldInsnNode) node;
				if ("dependants".equals(fieldNode.name)) {
					InsnList insList = new InsnList();
					insList.add(new VarInsnNode(25, 0));
					insList.add(new FieldInsnNode(180, "cpw/mods/fml/common/FMLModContainer", "modMetadata", "Lcpw/mods/fml/common/ModMetadata;"));
					insList.add(new MethodInsnNode(184, "io/gitlab/dwarfyassassin/gotucp/core/hooks/PreMCHooks", "forgeLoadOrderHook", "(Lcpw/mods/fml/common/ModMetadata;)V", false));
					method.instructions.insert(fieldNode, insList);
					break;
				}
			}
		}
		UCPCoreMod.log.info("Patched the FML dependency loader.");
	}

	private void patchLoader(ClassNode classNode) {
		MethodNode method = ASMUtils.findMethod(classNode, "loadMods", "()V");
		if (method == null) {
			return;
		}
		for (AbstractInsnNode node : method.instructions.toArray()) {
			if (!(node instanceof MethodInsnNode) || node.getOpcode() != 184) {
				continue;
			}
			MethodInsnNode methodNode = (MethodInsnNode) node;
			if (!"copyOf".equals(methodNode.name) || !"com/google/common/collect/ImmutableList".equals(methodNode.owner)) {
				continue;
			}
			MethodInsnNode insertNode = new MethodInsnNode(184, "io/gitlab/dwarfyassassin/gotucp/core/hooks/PreMCHooks", "postFMLLoad", "()V", false);
			method.instructions.insert(methodNode.getNext(), insertNode);
			break;
		}
		UCPCoreMod.log.info("Patched the FML loader.");
	}

}

