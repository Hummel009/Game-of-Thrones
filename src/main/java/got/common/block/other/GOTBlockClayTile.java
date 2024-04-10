package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockClayTile extends Block {
	public GOTBlockClayTile() {
		super(Material.rock);
		setCreativeTab(GOTCreativeTabs.TAB_BLOCK);
		setHardness(1.25f);
		setResistance(7.0f);
		setStepSound(soundTypeStone);
	}
}
