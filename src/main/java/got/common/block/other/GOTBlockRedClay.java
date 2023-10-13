package got.common.block.other;

import java.util.Random;

import got.common.database.GOTCreativeTabs;
import got.common.database.GOTItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class GOTBlockRedClay extends Block {
	public GOTBlockRedClay() {
		super(Material.clay);
		setHardness(0.6f);
		setStepSound(Block.soundTypeGravel);
		setCreativeTab(GOTCreativeTabs.tabBlock);
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return GOTItems.redClayBall;
	}

	@Override
	public int quantityDropped(Random random) {
		return 4;
	}
}
