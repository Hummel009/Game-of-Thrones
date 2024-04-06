package got.common.util;

import net.minecraft.block.Block;
import net.minecraftforge.common.IShearable;

import java.util.Set;

public class GOTPatcher {
	private GOTPatcher() {
	}

	public static void onInit() {
		if (GOTModChecker.hasLOTR()) {
			try {
				Class<?> registry = Class.forName("lotr.common.LOTRMod");
				Set<Block> blocks = GOTAPI.getObjectFieldsOfType(registry, Block.class);
				for (Block block : blocks) {
					if (block instanceof IShearable) {
						block.setCreativeTab(null);
					}
				}
			} catch (Exception ignored) {
			}
		}
	}
}
