package got.common.block.leaves;

import got.common.database.GOTRegistry;
import net.minecraft.item.Item;

import java.util.Random;

public class GOTBlockLeaves9 extends GOTBlockLeavesBase {
	public GOTBlockLeaves9() {
		setLeafNames("dragon", "kanuka", "weirwood");
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(GOTRegistry.sapling9);
	}
}
