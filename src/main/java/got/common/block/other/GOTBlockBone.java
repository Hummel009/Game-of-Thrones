package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockBone extends Block {
	public GOTBlockBone() {
		super(Material.rock);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(1.0f);
		setResistance(5.0f);
		setStepSound(Block.soundTypeStone);
	}
}
