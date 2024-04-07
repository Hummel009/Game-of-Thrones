package got.common.block.leaves;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GOTBlockLeaves5 extends GOTBlockLeavesBase {
	public GOTBlockLeaves5() {
		setLeafNames("pine", "lemon", "orange", "lime");
	}

	@Override
	public void addSpecialLeafDrops(List<ItemStack> drops, World world, int i, int j, int k, int meta, int fortune) {
		if ((meta & 3) == 1 && world.rand.nextInt(calcFortuneModifiedDropChance(16, fortune)) == 0) {
			drops.add(new ItemStack(GOTItems.lemon));
		}
		if ((meta & 3) == 2 && world.rand.nextInt(calcFortuneModifiedDropChance(16, fortune)) == 0) {
			drops.add(new ItemStack(GOTItems.orange));
		}
		if ((meta & 3) == 3 && world.rand.nextInt(calcFortuneModifiedDropChance(16, fortune)) == 0) {
			drops.add(new ItemStack(GOTItems.lime));
		}
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(GOTBlocks.sapling5);
	}
}