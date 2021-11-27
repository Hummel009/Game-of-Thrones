package got.common.block.leaves;

import java.util.*;

import got.common.database.GOTRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class GOTBlockLeavesFruit extends GOTBlockLeavesBase {
	public GOTBlockLeavesFruit() {
		setLeafNames("apple", "pear", "cherry", "mango");
	}

	@Override
	public void addSpecialLeafDrops(ArrayList drops, World world, int i, int j, int k, int meta, int fortune) {
		if ((meta & 3) == 0 && world.rand.nextInt(calcFortuneModifiedDropChance(16, fortune)) == 0) {
			if (world.rand.nextBoolean()) {
				drops.add(new ItemStack(Items.apple));
			} else {
				drops.add(new ItemStack(GOTRegistry.appleGreen));
			}
		}
		if ((meta & 3) == 1 && world.rand.nextInt(calcFortuneModifiedDropChance(16, fortune)) == 0) {
			drops.add(new ItemStack(GOTRegistry.pear));
		}
		if ((meta & 3) == 2 && world.rand.nextInt(calcFortuneModifiedDropChance(8, fortune)) == 0) {
			drops.add(new ItemStack(GOTRegistry.cherry));
		}
		if ((meta & 3) == 3 && world.rand.nextInt(calcFortuneModifiedDropChance(16, fortune)) == 0) {
			drops.add(new ItemStack(GOTRegistry.mango));
		}
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(GOTRegistry.fruitSapling);
	}
}
