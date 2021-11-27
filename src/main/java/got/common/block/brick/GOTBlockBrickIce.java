package got.common.block.brick;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockBrickIce extends Block {
	public GOTBlockBrickIce() {
		super(Material.ice);
		slipperiness = 0.98F;
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(10000.0f);
		setResistance(10000.0f);
		setStepSound(Block.soundTypeGlass);
	}
}