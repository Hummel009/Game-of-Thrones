package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import got.common.dispense.GOTDispensePebble;
import got.common.entity.other.GOTEntityPebble;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemPebble extends Item {
	public GOTItemPebble() {
		setMaxStackSize(64);
		setCreativeTab(GOTCreativeTabs.tabCombat);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispensePebble());
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote) {
			world.spawnEntityInWorld(new GOTEntityPebble(world, entityplayer));
			world.playSoundAtEntity(entityplayer, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}
		}
		return itemstack;
	}
}
