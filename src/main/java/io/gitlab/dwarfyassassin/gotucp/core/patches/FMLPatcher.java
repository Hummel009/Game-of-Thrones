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
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

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

}
