package got.common.block.wall;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.world.World;

public abstract class GOTBlockWallBase extends BlockWall {
	public int subtypes;

	public GOTBlockWallBase(Block block, int i) {
		super(block);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		subtypes = i;
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int i, int j, int k) {
		return true;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j < subtypes; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}
}
