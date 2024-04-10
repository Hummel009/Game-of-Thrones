package got.common;

import cpw.mods.fml.common.IFuelHandler;
import got.common.block.sapling.GOTBlockSaplingBase;
import got.common.database.GOTBlocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class GOTFuelHandler implements IFuelHandler {
	@Override
	public int getBurnTime(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof ItemBlock && ((ItemBlock) item).field_150939_a instanceof GOTBlockSaplingBase) {
			return 100;
		}
		if (item == Items.reeds || item == Item.getItemFromBlock(GOTBlocks.reeds) || item == Item.getItemFromBlock(GOTBlocks.driedReeds) || item == Item.getItemFromBlock(GOTBlocks.cornStalk)) {
			return 100;
		}
		return 0;
	}
}