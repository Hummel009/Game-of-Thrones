package got.common.item.other;

import got.common.block.other.GOTBlockGrapevine;
import got.common.database.GOTBlocks;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class GOTItemGrapeSeeds extends Item implements IPlantable {
	private final Block grapevineBlock;

	public GOTItemGrapeSeeds(Block block) {
		grapevineBlock = block;
		setCreativeTab(GOTCreativeTabs.TAB_MATERIALS);
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
		if (entityplayer.canPlayerEdit(i, j, k, side, itemstack) && world.getBlock(i, j, k) == GOTBlocks.grapevine && GOTBlockGrapevine.canPlantGrapesAt(world, i, j, k, this)) {
			world.setBlock(i, j, k, grapevineBlock, 0, 3);
			--itemstack.stackSize;
			return true;
		}
		return false;
	}
}
