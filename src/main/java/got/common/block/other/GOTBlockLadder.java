package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.BlockLadder;

public abstract class GOTBlockLadder extends BlockLadder {
	protected GOTBlockLadder() {
		setCreativeTab(GOTCreativeTabs.TAB_DECO);
	}
}