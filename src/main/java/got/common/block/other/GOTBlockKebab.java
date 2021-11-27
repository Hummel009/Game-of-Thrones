package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockKebab extends Block {
	public GOTBlockKebab() {
		super(Material.sand);
		setCreativeTab(GOTCreativeTabs.tabFood);
		setHardness(0.5f);
		setStepSound(Block.soundTypeWood);
	}
}
