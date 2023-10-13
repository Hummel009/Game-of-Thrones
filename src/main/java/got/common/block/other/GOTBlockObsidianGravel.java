package got.common.block.other;

import java.util.Random;

import net.minecraft.item.Item;

public class GOTBlockObsidianGravel extends GOTBlockGravel {
	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return Item.getItemFromBlock(this);
	}
}
