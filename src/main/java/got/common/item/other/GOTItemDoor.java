package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTItemDoor extends ItemBlock {
	public GOTItemDoor(Block block) {
		super(block);
		maxStackSize = 1;
		setCreativeTab(GOTCreativeTabs.tabDeco);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		if (side != 1) {
			return false;
		}
		Block doorBlock = field_150939_a;
		int j1 = j;
		++j1;
		if (entityplayer.canPlayerEdit(i, j1, k, side, itemstack) && entityplayer.canPlayerEdit(i, j1 + 1, k, side, itemstack)) {
			if (!doorBlock.canPlaceBlockAt(world, i, j1, k)) {
				return false;
			}
			int doorMeta = MathHelper.floor_double((entityplayer.rotationYaw + 180.0f) * 4.0f / 360.0f - 0.5) & 3;
			ItemDoor.placeDoorBlock(world, i, j1, k, doorMeta, doorBlock);
			--itemstack.stackSize;
			return true;
		}
		return false;
	}
}
