package got.common.block.other;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import got.common.database.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class GOTBlockMudGrass extends Block implements IGrowable {
	@SideOnly(value = Side.CLIENT)
	public IIcon iconTop;

	public GOTBlockMudGrass() {
		super(Material.grass);
		setHardness(0.6f);
		setStepSound(Block.soundTypeGrass);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setTickRandomly(true);
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int i, int j, int k, ForgeDirection direction, IPlantable plantable) {
		return Blocks.grass.canSustainPlant(world, i, j, k, direction, plantable) || plantable instanceof BlockStem;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		return 16777215;
	}

	@Override
	public boolean func_149851_a(World world, int i, int j, int k, boolean flag) {
		return Blocks.grass.func_149851_a(world, i, j, k, flag);
	}

	@Override
	public boolean func_149852_a(World world, Random random, int i, int j, int k) {
		return Blocks.grass.func_149852_a(world, random, i, j, k);
	}

	@Override
	public void func_149853_b(World world, Random random, int i, int j, int k) {
		Blocks.grass.func_149853_b(world, random, i, j, k);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getBlockColor() {
		return 16777215;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 1) {
			return iconTop;
		}
		if (i == 0) {
			return GOTRegistry.mud.getBlockTextureFromSide(i);
		}
		return blockIcon;
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return GOTRegistry.mud.getItemDropped(0, random, j);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getRenderColor(int i) {
		return 16777215;
	}

	@Override
	public void onPlantGrow(World world, int i, int j, int k, int sourceX, int sourceY, int sourceZ) {
		world.setBlock(i, j, k, GOTRegistry.mud, 0, 2);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		blockIcon = iconregister.registerIcon(getTextureName() + "_side");
		iconTop = iconregister.registerIcon(getTextureName() + "_top");
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		Blocks.grass.updateTick(world, i, j, k, random);
	}
}
