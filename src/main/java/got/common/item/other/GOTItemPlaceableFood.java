package got.common.item.other;

import got.common.block.other.GOTBlockPlaceableFood;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.ItemReed;

public class GOTItemPlaceableFood extends ItemReed {
	public GOTItemPlaceableFood(Block block) {
		super(block);
		setMaxStackSize(1);
		setCreativeTab(GOTCreativeTabs.tabFood);
		((GOTBlockPlaceableFood) block).setFoodItem(this);
	}
}
