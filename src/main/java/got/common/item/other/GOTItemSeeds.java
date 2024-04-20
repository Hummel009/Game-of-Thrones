package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSeeds;

public class GOTItemSeeds extends ItemSeeds {
	public GOTItemSeeds(Block crop, Block soil) {
		super(crop, soil);
		setCreativeTab(GOTCreativeTabs.TAB_MATERIALS);
	}
}