package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTWorldGenStreams;
import got.common.world.feature.GOTWorldGenVolcanoCrater;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class GOTBiomeMossovyMountains extends GOTBiomeMossovy {
	public GOTBiomeMossovyMountains(int i, boolean major) {
		super(i, major);
		setupStandardForestFauna();
		clearBiomeVariants();
		addBiomeVariantSet(GOTBiomeVariant.SET_MOUNTAINS);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
		enableRocky = true;
		decorator.doubleFlowersPerChunk = 0;
		decorator.doubleGrassPerChunk = 1;
		decorator.flowersPerChunk = 1;
		decorator.grassPerChunk = 4;
		decorator.biomeOreFactor = 2.0f;
		decorator.biomeGemFactor = 2.0f;
		decorator.clearSettlements();
		decorator.addOre(new WorldGenMinable(GOTBlocks.oreGlowstone, 4), 8.0f, 0, 48);
		decorator.addOre(new WorldGenMinable(GOTBlocks.oreCobalt, 5), 5.0f, 0, 32);
		npcSpawnList.clear();
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int i1;
		int k1;
		int i12;
		int j1;
		int l;
		super.decorate(world, random, i, k);
		GOTWorldGenStreams lavaGen = new GOTWorldGenStreams(Blocks.flowing_lava);
		for (l = 0; l < 250; ++l) {
			i12 = i + random.nextInt(16) + 8;
			j1 = 40 + random.nextInt(120);
			int k13 = k + random.nextInt(16) + 8;
			lavaGen.generate(world, random, i12, j1, k13);
		}
		random.nextInt(1);
		i1 = i + random.nextInt(16) + 8;
		k1 = k + random.nextInt(16) + 8;
		j1 = world.getHeightValue(i1, k1);
		new GOTWorldGenVolcanoCrater().generate(world, random, i1, j1, k1);
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
		return GOTAchievement.enterMossovyMountains;
	}

}