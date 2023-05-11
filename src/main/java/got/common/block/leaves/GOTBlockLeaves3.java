package got.common.block.leaves;

import got.common.database.GOTRegistry;
import net.minecraft.item.Item;

import java.util.Random;

public class GOTBlockLeaves3 extends GOTBlockLeavesBase {
	public GOTBlockLeaves3() {
		setLeafNames("maple", "larch", "date_palm", "mangrove");
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(GOTRegistry.sapling3);
	}

	@Override
	public int getSaplingChance(int meta) {
		if (meta == 2) {
			return 15;
		}
		return super.getSaplingChance(meta);
	}
}
