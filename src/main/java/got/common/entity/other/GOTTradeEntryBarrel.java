package got.common.entity.other;

import got.common.database.GOTBlocks;
import got.common.item.other.GOTItemBarrel;
import got.common.recipe.GOTRecipeBrewing;
import got.common.tileentity.GOTTileEntityBarrel;
import net.minecraft.item.ItemStack;

public class GOTTradeEntryBarrel extends GOTTradeEntry {
	public GOTTradeEntryBarrel(ItemStack itemstack, int cost) {
		super(itemstack, cost);
	}

	@Override
	public ItemStack createTradeItem() {
		ItemStack drinkItem = super.createTradeItem();
		ItemStack barrelItem = new ItemStack(GOTBlocks.barrel);
		GOTTileEntityBarrel barrel = new GOTTileEntityBarrel();
		barrel.setInventorySlotContents(9, new ItemStack(drinkItem.getItem(), GOTRecipeBrewing.BARREL_CAPACITY, drinkItem.getItemDamage()));
		barrel.barrelMode = 2;
		GOTItemBarrel.setBarrelDataFromTE(barrelItem, barrel);
		return barrelItem;
	}
}
