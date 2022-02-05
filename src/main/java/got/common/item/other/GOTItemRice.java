/*
 * Decompiled with CFR 0.148.
 *
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.EnumPlantType
 *  net.minecraftforge.common.IPlantable
 */
package got.common.item.other;

import got.common.database.GOTRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

public class GOTItemRice extends ItemFood implements IPlantable {
	private Block field_150908_b;

	public GOTItemRice(int p_i45351_1_, float p_i45351_2_, Block p_i45351_3_, Block p_i45351_4_) {
		super(p_i45351_1_, p_i45351_2_, false);
		field_150908_b = p_i45351_3_;
		setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public Block getPlant(IBlockAccess world, int x, int y, int z) {
		return field_150908_b;
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
		return 0;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return EnumPlantType.Water;
	}

	@Override
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(p_77648_3_, p_77648_2_, true);
		if (movingobjectposition == null) {
			return false;
		}
		if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			int i = movingobjectposition.blockX;
			int j = movingobjectposition.blockY;
			int k = movingobjectposition.blockZ;
			if (p_77648_7_ != 1) {
				return false;
			}
			if (p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) && p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_ + 1, p_77648_6_, p_77648_7_, p_77648_1_)) {
				if (p_77648_3_.getBlock(i, j, k).getMaterial() == Material.water && p_77648_3_.getBlockMetadata(i, j, k) == 0 && p_77648_3_.isAirBlock(i, j + 1, k)) {
					p_77648_3_.setBlock(i, j + 1, k, GOTRegistry.ricePlant);
					--p_77648_1_.stackSize;
					return true;
				}
			}
		}
		return false;
	}
}
