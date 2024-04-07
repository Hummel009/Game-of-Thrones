package got.common.block.brick;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockBrickIce extends Block {
	public GOTBlockBrickIce() {
		super(Material.packedIce);
		slipperiness = 0.98F;
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(15.0f);
		setResistance(60.0f);
		setStepSound(soundTypeGlass);
	}
}