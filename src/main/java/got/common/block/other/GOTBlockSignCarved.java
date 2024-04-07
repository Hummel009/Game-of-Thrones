package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.tileentity.GOTTileEntitySign;
import got.common.util.GOTCommonIcons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBlockSignCarved extends BlockSign {
	public GOTBlockSignCarved(Class<? extends GOTTileEntitySign> cls) {
		super(cls, false);
		setStepSound(soundTypeStone);
		setHardness(0.5f);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTCommonIcons.iconEmptyBlock;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getItem(World world, int i, int j, int k) {
		if (this == GOTBlocks.signCarvedGlowing) {
			return GOTItems.valyrianChisel;
		}
		return GOTItems.chisel;
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
			return Blocks.stone.getIcon(0, 0);
		}
		return icon;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
		super.setBlockBoundsBasedOnState(world, i, j, k);
		setBlockBounds((float) minX, 0.0f, (float) minZ, (float) maxX, 1.0f, (float) maxZ);
	}
}
