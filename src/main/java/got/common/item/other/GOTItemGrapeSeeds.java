package got.common.item.other;

import got.common.block.other.GOTBlockGrapevine;
import got.common.database.*;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

public class GOTItemGrapeSeeds extends Item implements IPlantable {
	public Block grapevineBlock;

	public GOTItemGrapeSeeds(Block block) {
		grapevineBlock = block;
		setCreativeTab(GOTCreativeTabs.tabMaterials);
	}

	@Override
	public Block getPlant(IBlockAccess world, int i, int j, int k) {
		return grapevineBlock;
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, int i, int j, int k) {
		return 0;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
		return EnumPlantType.Crop;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		if (entityplayer.canPlayerEdit(i, j, k, side, itemstack) && world.getBlock(i, j, k) == GOTRegistry.grapevine && GOTBlockGrapevine.canPlantGrapesAt(world, i, j, k, this)) {
			world.setBlock(i, j, k, grapevineBlock, 0, 3);
			--itemstack.stackSize;
			return true;
		}
		return false;
	}
}
