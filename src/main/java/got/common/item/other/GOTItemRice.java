package got.common.item.other;

import got.common.database.GOTBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class GOTItemRice extends ItemFood implements IPlantable {
	private final Block block;

	public GOTItemRice(int p_i45351_1_, float p_i45351_2_, Block block) {
		super(p_i45351_1_, p_i45351_2_, false);
		this.block = block;
		setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public Block getPlant(IBlockAccess world, int x, int y, int z) {
		return block;
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
	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, entityPlayer, true);
		if (movingobjectposition == null) {
			return false;
		}
		if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			int i1 = movingobjectposition.blockX;
			int j1 = movingobjectposition.blockY;
			int k1 = movingobjectposition.blockZ;
			if (p_77648_7_ != 1) {
				return false;
			}
			if (entityPlayer.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, itemStack) && entityPlayer.canPlayerEdit(p_77648_4_, p_77648_5_ + 1, p_77648_6_, p_77648_7_, itemStack)) {
				if (world.getBlock(i1, j1, k1).getMaterial() == Material.water && world.getBlockMetadata(i1, j1, k1) == 0 && world.isAirBlock(i1, j1 + 1, k1)) {
					world.setBlock(i1, j1 + 1, k1, GOTBlocks.ricePlant);
					--itemStack.stackSize;
					return true;
				}
			}
		}
		return false;
	}
}
