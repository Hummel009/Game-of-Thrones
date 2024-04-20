package got.common.block.other;

import net.minecraft.item.Item;

import java.util.Random;

public class GOTBlockObsidianGravel extends GOTBlockGravel {
	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return Item.getItemFromBlock(this);
	}
}