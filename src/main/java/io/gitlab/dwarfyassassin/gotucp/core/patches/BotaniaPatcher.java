package io.gitlab.dwarfyassassin.gotucp.core.patches;

import io.gitlab.dwarfyassassin.gotucp.core.UCPCoreMod;
import io.gitlab.dwarfyassassin.gotucp.core.patches.base.ModPatcher;
import io.gitlab.dwarfyassassin.gotucp.core.utils.ASMUtils;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public class BotaniaPatcher extends ModPatcher {
	public BotaniaPatcher() {
		super("Botania", "Botania");
		classes.put("vazkii.botania.common.block.subtile.generating.SubTileKekimurus", this::patchKekimurus);
	}

	private void patchKekimurus(ClassNode classNode) {
		MethodNode method = ASMUtils.findMethod(classNode, "onUpdate", "()V");
		if (method == null) {
			return;
		}
		for (AbstractInsnNode node : method.instructions.toArray()) {
			if (node instanceof TypeInsnNode) {
				TypeInsnNode typeNode = (TypeInsnNode) node;
				if ("net/minecraft/block/BlockCake".equals(typeNode.desc)) {
					typeNode.desc = "got/common/block/other/GOTBlockPlaceableFood";
					break;
				}
			}
		}
		UCPCoreMod.log.info("Patched the Kekimurus to eat all GOT cakes.");
	}

}

