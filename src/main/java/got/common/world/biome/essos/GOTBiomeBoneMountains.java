package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class GOTBiomeBoneMountains extends GOTBiomeDothrakiSea {
	public GOTBiomeBoneMountains(int i, boolean major) {
		super(i, major);
		setupStandardForestFauna();
		clearBiomeVariants();
		addBiomeVariantSet(GOTBiomeVariant.SET_MOUNTAINS);
		enableRocky = true;
		decorator.biomeOreFactor = 2.0f;
		decorator.biomeGemFactor = 2.0f;
		decorator.doubleFlowersPerChunk = 0;
		decorator.doubleGrassPerChunk = 1;
		decorator.flowersPerChunk = 1;
		decorator.grassPerChunk = 4;
		decorator.addOre(new WorldGenMinable(GOTBlocks.oreGlowstone, 4), 8.0f, 0, 48);
		decorator.addOre(new WorldGenMinable(GOTBlocks.oreCobalt, 5), 5.0f, 0, 32);
	}

	@Override
	public void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, GOTBiomeVariant variant) {
		int snowHeight = 150 - rockDepth;
		int stoneHeight = snowHeight - 40;
		for (int j = ySize - 1; j >= stoneHeight; --j) {
			int index = xzIndex * ySize + j;
			Block block = blocks[index];
			if (j >= snowHeight && block == topBlock) {
				blocks[index] = Blocks.snow;
				meta[index] = 0;
			} else if (block == topBlock || block == fillerBlock) {
				blocks[index] = Blocks.stone;
				meta[index] = 0;
			}
			block = blocks[index];
			if (block != Blocks.stone) {
				continue;
			}
			if (random.nextInt(6) == 0) {
				int h = 1 + random.nextInt(6);
				for (int j1 = j; j1 > j - h && j1 > 0; --j1) {
					int indexH = xzIndex * ySize + j1;
					if (blocks[indexH] != Blocks.stone) {
						continue;
					}
					blocks[indexH] = GOTBlocks.rock;
					meta[indexH] = 3;
				}
				continue;
			}
			if (random.nextInt(16) != 0) {
				continue;
			}
			blocks[index] = GOTBlocks.rock;
			meta[index] = 3;
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterBoneMountains;
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.JOGOS;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.WALL_YITI;
	}

}