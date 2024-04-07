package got.common.block.leaves;

import got.common.database.GOTBlocks;
import net.minecraft.item.Item;

import java.util.Random;

public class GOTBlockLeaves2 extends GOTBlockLeavesBase {
	public GOTBlockLeaves2() {
		setLeafNames("aramant", "beech", "holly", "banana");
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(GOTBlocks.sapling2);
	}

	@Override
	public int getSaplingChance(int meta) {
		if (meta == 3) {
			return 9;
		}
		return super.getSaplingChance(meta);
	}
}