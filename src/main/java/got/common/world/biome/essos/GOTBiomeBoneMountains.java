package got.common.world.biome.essos;

import java.util.Random;

import got.GOT;
import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomeBoneMountains extends GOTBiome {
	public GOTBiomeBoneMountains(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		spawnableGOTAmbientList.clear();
		spawnableCreatureList.clear();
		this.addBiomeVariant(GOTBiomeVariant.MOUNTAIN);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_BEECH, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_BIRCH, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_LARCH, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_PINE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_MAPLE, 0.2f);
		decorator.biomeOreFactor = 2.0f;
		decorator.biomeGemFactor = 1.5f;
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreGlowstone, 4), 8.0f, 0, 48);
		decorator.treesPerChunk = 1;
		decorator.flowersPerChunk = 1;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 1;
		decorator.addTree(GOTTreeType.OAK, 300);
		decorator.addTree(GOTTreeType.OAK_LARGE, 50);
		decorator.addTree(GOTTreeType.SPRUCE, 500);
		decorator.addTree(GOTTreeType.LARCH, 300);
		decorator.addTree(GOTTreeType.MAPLE, 300);
		decorator.addTree(GOTTreeType.MAPLE_LARGE, 50);
		decorator.addTree(GOTTreeType.FIR, 500);
		decorator.addTree(GOTTreeType.PINE, 500);
		enableRocky = true;
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int l;
		super.decorate(world, random, i, k);
		for (l = 0; l < 10; ++l) {
			Block block = GOTRegistry.boneBlock;
			if (random.nextInt(5) == 0) {
				block = GOTRegistry.boneBlock;
			}
			for (int l2 = 0; l2 < 10; ++l2) {
				int k3;
				int j1;
				int i3 = i + random.nextInt(16) + 8;
				if (world.getBlock(i3, (j1 = world.getHeightValue(i3, k3 = k + random.nextInt(16) + 8)) - 1, k3) != block) {
					continue;
				}
				int height = j1 + random.nextInt(4);
				for (int j2 = j1; j2 < height && !GOT.isOpaque(world, i3, j2, k3); ++j2) {
					world.setBlock(i3, j2, k3, block, 0, 3);
				}
			}
		}
	}

	@Override
	public void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, GOTBiomeVariant variant) {
		int stoneHeight = 110 - rockDepth;
		int sandHeight = stoneHeight - 6;
		for (int j = ySize - 1; j >= sandHeight; --j) {
			int index = xzIndex * ySize + j;
			Block block = blocks[index];
			if (block != topBlock && block != fillerBlock) {
				continue;
			}
			if (j >= stoneHeight) {
				blocks[index] = GOTRegistry.boneBlock;
				meta[index] = 0;
			}
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_BONE_MOUNTAINS;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("boneMountains");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.JOGOS;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.COBBLEBRICK;
	}

	@Override
	public int getWallTop() {
		return 90;
	}
}