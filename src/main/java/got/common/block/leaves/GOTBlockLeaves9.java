package got.common.block.leaves;

import got.common.database.GOTBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GOTBlockLeaves9 extends GOTBlockLeavesBase {
	public GOTBlockLeaves9() {
		leafNames = new String[]{"dragon", "kanuka", "weirwood"};
	}

	@Override
	protected void addSpecialLeafDrops(List<ItemStack> drops, World world, int meta, int fortune) {
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(GOTBlocks.sapling9);
	}
}