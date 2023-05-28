package got.common.block.leaves;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class GOTBlockLeaves7 extends GOTBlockLeavesBase {
	public GOTBlockLeaves7() {
		setLeafNames("aspen", "green_oak", "fotinia", "almond");
	}

	@Override
	public void addSpecialLeafDrops(ArrayList<ItemStack> drops, World world, int i, int j, int k, int meta, int fortune) {
		if ((meta & 3) == 3 && world.rand.nextInt(calcFortuneModifiedDropChance(12, fortune)) == 0) {
			drops.add(new ItemStack(GOTItems.almond));
		}
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(GOTBlocks.sapling7);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		super.randomDisplayTick(world, i, j, k, random);
		String s = null;
		int metadata = world.getBlockMetadata(i, j, k) & 3;
		if (metadata == 1 && random.nextInt(150) == 0) {
			s = "leafGreen";
		}
		if (s != null) {
			double d = i + random.nextFloat();
			double d1 = j - 0.05;
			double d2 = k + random.nextFloat();
			double d3 = -0.1 + random.nextFloat() * 0.2f;
			double d4 = -0.03 - random.nextFloat() * 0.02f;
			double d5 = -0.1 + random.nextFloat() * 0.2f;
			GOT.proxy.spawnParticle(s, d, d1, d2, d3, d4, d5);
		}
	}
}
