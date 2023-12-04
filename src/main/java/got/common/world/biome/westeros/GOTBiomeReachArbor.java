package got.common.world.biome.westeros;

import com.google.common.math.IntMath;
import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBeziers;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBiomeReachArbor extends GOTBiomeReach {
	public GOTBiomeReachArbor(int i, boolean major) {
		super(i, major);
		setupStandardDomesticFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.VINEYARD, 8.0f);
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		int ySize = blocks.length / 256;
		boolean vineyard = variant == GOTBiomeVariant.VINEYARD;
		boolean roadNear = GOTBeziers.isBezierAt(i, k, GOTBeziers.Type.ROAD);
		if (vineyard && !roadNear) {
			int chunkZ = k & 0xF;
			int chunkX = i & 0xF;
			int xzIndex = chunkX * 16 + chunkZ;
			for (int j = 128; j >= 0; --j) {
				int index = xzIndex * ySize + j;
				Block above = blocks[index + 1];
				Block block = blocks[index];
				if (block == null || !block.isOpaqueCube() || above != null && above.getMaterial() != Material.air) {
					continue;
				}
				int i1 = IntMath.mod(i, 6);
				int i2 = IntMath.mod(i, 24);
				int k1 = IntMath.mod(k, 32);
				int k2 = IntMath.mod(k, 64);
				if ((i1 == 0 || i1 == 5) && k1 != 0) {
					blocks[index] = Blocks.farmland;
					meta[index] = 0;
					int h = 2;
					if (biomeTerrainNoise.func_151601_a(i, k) > 0.0) {
						++h;
					}
					double d;
					boolean red = biomeTerrainNoise.func_151601_a(i * (d = 0.01), k * d) > 0.0;
					Block vineBlock = red ? GOTBlocks.grapevineRed : GOTBlocks.grapevineWhite;
					for (int j1 = 1; j1 <= h; ++j1) {
						blocks[index + j1] = vineBlock;
						meta[index + j1] = 7;
					}
					break;
				}
				if (i1 >= 2 && i1 <= 3) {
					blocks[index] = GOTBlocks.dirtPath;
					meta[index] = 0;
					if (i1 != i2 || (i1 != 2 || k2 != 16) && (i1 != 3 || k2 != 48)) {
						break;
					}
					int h = 3;
					for (int j1 = 1; j1 <= h; ++j1) {
						if (j1 == h) {
							blocks[index + j1] = Blocks.torch;
							meta[index + j1] = 5;
							continue;
						}
						blocks[index + j1] = GOTBlocks.fence2;
						meta[index + j1] = 10;
					}
					break;
				}
				blocks[index] = topBlock;
				meta[index] = (byte) topBlockMeta;
				break;
			}
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterArbor;
	}
}