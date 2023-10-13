package got.common.block.leaves;

import java.util.List;
import java.util.Random;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTBlockLeavesFruit extends GOTBlockLeavesBase {
	public GOTBlockLeavesFruit() {
		setLeafNames("apple", "pear", "cherry", "mango");
	}

	@Override
	public void addSpecialLeafDrops(List<ItemStack> drops, World world, int i, int j, int k, int meta, int fortune) {
		if ((meta & 3) == 0 && world.rand.nextInt(calcFortuneModifiedDropChance(16, fortune)) == 0) {
			if (world.rand.nextBoolean()) {
				drops.add(new ItemStack(Items.apple));
			} else {
				drops.add(new ItemStack(GOTItems.appleGreen));
			}
		}
		if ((meta & 3) == 1 && world.rand.nextInt(calcFortuneModifiedDropChance(16, fortune)) == 0) {
			drops.add(new ItemStack(GOTItems.pear));
		}
		if ((meta & 3) == 2 && world.rand.nextInt(calcFortuneModifiedDropChance(8, fortune)) == 0) {
			drops.add(new ItemStack(GOTItems.cherry));
		}
		if ((meta & 3) == 3 && world.rand.nextInt(calcFortuneModifiedDropChance(16, fortune)) == 0) {
			drops.add(new ItemStack(GOTItems.mango));
		}
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(GOTBlocks.fruitSapling);
	}
}
