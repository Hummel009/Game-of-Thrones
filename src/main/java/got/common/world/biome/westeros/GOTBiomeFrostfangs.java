package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.database.GOTRegistry;
import got.common.entity.animal.GOTEntityShadowcat;
import got.common.world.biome.variant.GOTBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class GOTBiomeFrostfangs extends GOTBiomeWesterosFrost {
	public GOTBiomeFrostfangs(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.MOUNTAIN);
		fillerBlock = Blocks.packed_ice;
		decorator.biomeOreFactor = 2.0f;
		decorator.biomeGemFactor = 2.0f;
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreGlowstone, 4), 8.0f, 0, 48);
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreCobalt, 5), 5.0f, 0, 32);
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityShadowcat.class, 100, 1, 2));
	}

	@Override
	public void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, GOTBiomeVariant variant) {
		int snowHeight = 150 - rockDepth;
		int stoneHeight = snowHeight - 40;
		for (int j = ySize - 1; j >= stoneHeight; --j) {
			int index = xzIndex * ySize + j;
			Block block = blocks[index];
			if (j >= snowHeight && block == topBlock || block == topBlock || block == fillerBlock) {
				blocks[index] = Blocks.snow;
				meta[index] = 0;
			}
			block = blocks[index];
			if (block != Blocks.snow) {
				continue;
			}
			if (random.nextInt(6) == 0) {
				int h = 1 + random.nextInt(6);
				for (int j1 = j; j1 > j - h && j1 > 0; --j1) {
					int indexH = xzIndex * ySize + j1;
					if (blocks[indexH] != Blocks.snow) {
						continue;
					}
					blocks[indexH] = Blocks.packed_ice;
					meta[indexH] = 0;
				}
				continue;
			}
			if (random.nextInt(16) != 0) {
				continue;
			}
			blocks[index] = Blocks.packed_ice;
			meta[index] = 0;
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterFrostfangs;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("frostfangs");
	}

}