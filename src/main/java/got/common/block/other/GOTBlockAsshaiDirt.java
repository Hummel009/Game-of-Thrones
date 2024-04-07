package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockAsshaiDirt extends Block {
	public GOTBlockAsshaiDirt() {
		super(Material.ground);
		setHardness(0.5f);
		setStepSound(soundTypeGravel);
		setCreativeTab(GOTCreativeTabs.tabBlock);
	}
}
