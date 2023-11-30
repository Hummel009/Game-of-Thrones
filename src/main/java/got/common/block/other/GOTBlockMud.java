package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class GOTBlockMud extends Block {
	public GOTBlockMud() {
		super(Material.ground);
		setHardness(0.5f);
		setStepSound(Block.soundTypeGravel);
		setCreativeTab(GOTCreativeTabs.tabBlock);
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int i, int j, int k, ForgeDirection direction, IPlantable plantable) {
		return Blocks.dirt.canSustainPlant(world, i, j, k, direction, plantable) || plantable instanceof BlockStem;
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public int getDamageValue(World world, int i, int j, int k) {
		return world.getBlockMetadata(i, j, k);
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 2; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
