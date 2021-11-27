package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.*;

public class GOTBlockStairs extends BlockStairs {
	public GOTBlockStairs(Block block, int metadata) {
		super(block, metadata);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		useNeighborBrightness = true;
	}
}
