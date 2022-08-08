package got.common.item.other;

import got.common.block.other.GOTBlockFallenLeaves;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GOTItemFallenLeaves extends GOTItemBlockMetadata {
	public GOTItemFallenLeaves(Block block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		Object[] obj = ((GOTBlockFallenLeaves) field_150939_a).leafBlockMetaFromFallenMeta(itemstack.getItemDamage());
		ItemStack leaves = new ItemStack((Block) obj[0], 1, (int) (Integer) obj[1]);
		String name = leaves.getDisplayName();
		return StatCollector.translateToLocalFormatted("tile.got.fallen_leaves", name);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		return GOTItemWaterPlant.tryPlaceWaterPlant(this, itemstack, world, entityplayer, getMovingObjectPositionFromPlayer(world, entityplayer, true));
	}
}
