package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.*;

public class GOTBlockGravel extends BlockGravel {
	public GOTBlockGravel() {
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(0.6f);
		setStepSound(Block.soundTypeGravel);
	}
}
