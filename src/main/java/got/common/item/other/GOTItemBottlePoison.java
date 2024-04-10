package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import got.common.item.GOTPoisonedDrinks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemBottlePoison extends Item {
	public GOTItemBottlePoison() {
		setMaxStackSize(1);
		setCreativeTab(GOTCreativeTabs.TAB_MISC);
		setContainerItem(Items.glass_bottle);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.drink;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 32;
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote) {
			GOTPoisonedDrinks.addPoisonEffect(entityplayer, itemstack);
		}
		return entityplayer.capabilities.isCreativeMode ? itemstack : new ItemStack(Items.glass_bottle);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}
}
