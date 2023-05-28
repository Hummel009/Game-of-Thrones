package got.common.block.leaves;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTBlocks;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBlockLeaves1 extends GOTBlockLeavesBase {
	public GOTBlockLeaves1() {
		setLeafNames("ibbinia", "catalpa", "ulthos", "ulthos_red");
		setSeasonal(false, true, false, false);
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(GOTBlocks.sapling1);
	}

	@Override
	public int getLightOpacity(IBlockAccess world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k) & 3;
		if (l == 2 || l == 3) {
			return 255;
		}
		return super.getLightOpacity(world, i, j, k);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		super.randomDisplayTick(world, i, j, k, random);
		String s = null;
		int metadata = world.getBlockMetadata(i, j, k) & 3;
		if (metadata == 1 && random.nextInt(75) == 0) {
			s = "leafGold";
		} else if (metadata == 2 && random.nextInt(250) == 0) {
			s = "leafMirk";
		} else if (metadata == 3 && random.nextInt(40) == 0) {
			s = "leafRed";
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
