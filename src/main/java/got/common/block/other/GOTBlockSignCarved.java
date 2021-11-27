package got.common.block.other;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import got.common.tileentity.GOTTileEntitySign;
import got.common.util.GOTCommonIcons;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class GOTBlockSignCarved extends BlockSign {
	public GOTBlockSignCarved(Class<? extends GOTTileEntitySign> cls) {
		super(cls, false);
		setStepSound(Block.soundTypeStone);
		setHardness(0.5f);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTCommonIcons.iconEmptyBlock;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public Item getItem(World world, int i, int j, int k) {
		if (this == GOTRegistry.signCarvedGlowing) {
			return GOTRegistry.valyrianChisel;
		}
		return GOTRegistry.chisel;
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return null;
	}

	public IIcon getOnBlockIcon(IBlockAccess world, int i, int j, int k, int side) {
		int onX = i - Facing.offsetsXForSide[side];
		int onY = j - Facing.offsetsYForSide[side];
		int onZ = k - Facing.offsetsZForSide[side];
		Block onBlock = world.getBlock(onX, onY, onZ);
		IIcon icon = onBlock.getIcon(world, onX, onY, onZ, side);
		if (icon == null) {
			icon = Blocks.stone.getIcon(0, 0);
		}
		return icon;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
		super.setBlockBoundsBasedOnState(world, i, j, k);
		setBlockBounds((float) minX, 0.0f, (float) minZ, (float) maxX, 1.0f, (float) maxZ);
	}
}
