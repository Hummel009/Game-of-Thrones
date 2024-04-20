package got.common.item.other;

import got.common.block.other.GOTBlockDoubleTorch;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemDoubleTorch extends Item {
	private final Block torchBlock;

	public GOTItemDoubleTorch(Block block) {
		setCreativeTab(GOTCreativeTabs.TAB_DECO);
		torchBlock = block;
		((GOTBlockDoubleTorch) torchBlock).setTorchItem(this);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		int side1 = side;
		int j1 = j;
		int k1 = k;
		int i1 = i;
		Block block = world.getBlock(i1, j1, k1);
		if (block == Blocks.snow_layer) {
			side1 = 1;
		} else if (!block.isReplaceable(world, i1, j1, k1)) {
			if (side1 == 0) {
				--j1;
			}
			if (side1 == 1) {
				++j1;
			}
			if (side1 == 2) {
				--k1;
			}
			if (side1 == 3) {
				++k1;
			}
			if (side1 == 4) {
				--i1;
			}
			if (side1 == 5) {
				++i1;
			}
		}
		if (!entityplayer.canPlayerEdit(i1, j1, k1, side1, itemstack) || !entityplayer.canPlayerEdit(i1, j1 + 1, k1, side1, itemstack) || !world.canPlaceEntityOnSide(block, i1, j1, k1, false, side1, null, itemstack) || !world.canPlaceEntityOnSide(block, i1, j1 + 1, k1, false, side1, null, itemstack)) {
			return false;
		}
		if (!GOTBlockDoubleTorch.canPlaceTorchOn(world, i1, j1 - 1, k1)) {
			return false;
		}
		if (!world.isRemote) {
			world.setBlock(i1, j1, k1, torchBlock, 0, 2);
			world.setBlock(i1, j1 + 1, k1, torchBlock, 1, 2);
			Block.SoundType stepSound = torchBlock.stepSound;
			world.playSoundEffect(i1 + 0.5, j1 + 0.5, k1 + 0.5, stepSound.func_150496_b(), (stepSound.getVolume() + 1.0f) / 2.0f, stepSound.getPitch() * 0.8f);
			--itemstack.stackSize;
		}
		return true;
	}
}