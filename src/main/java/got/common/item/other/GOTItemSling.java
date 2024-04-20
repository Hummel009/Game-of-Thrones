package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityPebble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemSling extends Item {
	public GOTItemSling() {
		setMaxStackSize(1);
		setMaxDamage(250);
		setCreativeTab(GOTCreativeTabs.TAB_COMBAT);
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		return repairItem.getItem() == Items.leather || super.getIsRepairable(itemstack, repairItem);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.inventory.hasItem(GOTItems.pebble) || entityplayer.capabilities.isCreativeMode) {
			itemstack.damageItem(1, entityplayer);
			if (!entityplayer.capabilities.isCreativeMode) {
				entityplayer.inventory.consumeInventoryItem(GOTItems.pebble);
			}
			world.playSoundAtEntity(entityplayer, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
			if (!world.isRemote) {
				world.spawnEntityInWorld(new GOTEntityPebble(world, entityplayer).setSling());
			}
		}
		return itemstack;
	}
}