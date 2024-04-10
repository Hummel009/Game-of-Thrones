package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.BlockGravel;

public class GOTBlockGravel extends BlockGravel {
	public GOTBlockGravel() {
		setCreativeTab(GOTCreativeTabs.TAB_BLOCK);
		setHardness(0.6f);
		setStepSound(soundTypeGravel);
	}
}