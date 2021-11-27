package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.BlockLadder;

public class GOTBlockLadder extends BlockLadder {
	public GOTBlockLadder() {
		setCreativeTab(GOTCreativeTabs.tabDeco);
	}
}
