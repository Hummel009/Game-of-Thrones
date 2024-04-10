package got.common.item.other;

import got.common.database.GOTBlocks;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTItemArmorStand extends Item {
	public GOTItemArmorStand() {
		setCreativeTab(GOTCreativeTabs.TAB_DECO);
		setMaxStackSize(1);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		int i1 = i;
		int j1 = j;
		int k1 = k;
		if (world.isRemote) {
			return true;
		}
		Block block = GOTBlocks.armorStand;
		Block block1 = world.getBlock(i1 += Facing.offsetsXForSide[side], j1 += Facing.offsetsYForSide[side], k1 += Facing.offsetsZForSide[side]);
		Block block2 = world.getBlock(i1, j1 + 1, k1);
		if (block1 != null && !block1.isReplaceable(world, i1, j1, k1) || block2 != null && !block2.isReplaceable(world, i1, j1 + 1, k1)) {
			return false;
		}
		if (entityplayer.canPlayerEdit(i1, j1, k1, side, itemstack) && entityplayer.canPlayerEdit(i1, j1 + 1, k1, side, itemstack)) {
			if (!block.canPlaceBlockAt(world, i1, j1, k1)) {
				return false;
			}
			int l = MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
			world.setBlock(i1, j1, k1, block, l, 3);
			world.setBlock(i1, j1 + 1, k1, block, l | 4, 3);
			world.playSoundEffect(i1 + 0.5, j1 + 0.5, k1 + 0.5, block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0f) / 2.0f, block.stepSound.getPitch() * 0.8f);
			--itemstack.stackSize;
			return true;
		}
		return false;
	}
}
