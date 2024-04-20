package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class GOTBlockStairs extends BlockStairs {
	public GOTBlockStairs(Block block, int metadata) {
		super(block, metadata);
		setCreativeTab(GOTCreativeTabs.TAB_BLOCK);
		useNeighborBrightness = true;
	}
}