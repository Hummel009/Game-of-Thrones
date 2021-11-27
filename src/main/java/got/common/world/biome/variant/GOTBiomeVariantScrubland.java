package got.common.world.biome.variant;

import java.util.Random;

import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTBiomeVariantScrubland extends GOTBiomeVariant {
	public Block stoneBlock;

	public GOTBiomeVariantScrubland(int i, String s, Block block) {
		super(i, s, GOTBiomeVariant.VariantScale.LARGE);
		setTrees(0.8f);
		setGrass(0.5f);
		addTreeTypes(0.6f, GOTTreeType.OAK_SHRUB, 100);
		stoneBlock = block;
		disableVillages();
	}

	@Override
	public void generateVariantTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int height, GOTBiome biome) {
		int index;
		int j;
		int chunkX = i & 0xF;
		int chunkZ = k & 0xF;
		int xzIndex = chunkX * 16 + chunkZ;
		int ySize = blocks.length / 256;
		GOTBiome.biomeTerrainNoise.func_151601_a(i * 0.005, k * 0.005);
		if (GOTBiome.biomeTerrainNoise.func_151601_a(i * 0.008, k * 0.008) + GOTBiome.biomeTerrainNoise.func_151601_a(i * 0.05, k * 0.05) + GOTBiome.biomeTerrainNoise.func_151601_a(i * 0.6, k * 0.6) > 0.8 && random.nextInt(3) == 0) {
			j = height;
			index = xzIndex * ySize + j;
			Block above = blocks[index + 1];
			Block block = blocks[index];
			if (block.isOpaqueCube() && above.getMaterial() == Material.air) {
				blocks[index + 1] = Blocks.leaves;
				meta[index + 1] = 4;
				if (random.nextInt(5) == 0) {
					blocks[index + 2] = Blocks.leaves;
					meta[index + 2] = 4;
				}
			}
		}
		if (random.nextInt(3000) == 0) {
			j = height;
			index = xzIndex * ySize + j;
			blocks[index] = biome.fillerBlock;
			meta[index] = (byte) biome.fillerBlockMeta;
			int logHeight = 1 + random.nextInt(4);
			for (int j1 = 1; j1 <= logHeight; ++j1) {
				blocks[index + j1] = Blocks.log;
				meta[index + j1] = 0;
			}
		}
	}
}
