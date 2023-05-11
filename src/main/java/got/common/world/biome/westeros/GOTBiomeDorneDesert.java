package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.database.GOTRegistry;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.GOTStructureStoneRuin;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.ArrayList;
import java.util.Random;

public class GOTBiomeDorneDesert extends GOTBiomeDorne implements GOTBiome.Desert {
	public GOTBiomeDorneDesert(int i, boolean major) {
		super(i, major);
		setupDesertFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.HILLS);
		topBlock = Blocks.sand;
		fillerBlock = Blocks.sand;
		variantChance = 0.3f;
		decorator.grassPerChunk = 5;
		decorator.cactiPerChunk = 2;
		decorator.deadBushPerChunk = 2;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.OAK_DEAD, 1000);
		decorator.addOre(new WorldGenMinable(Blocks.lapis_ore, 6), 1.0f, 0, 48);
		decorator.clearVillages();
		npcSpawnList.clear();
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.DESERT_SCORPION, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		decorator.addRandomStructure(new GOTStructureStoneRuin.RuinSandstone(1, 4), 400);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int j1;
		int l;
		int i1;
		int i12;
		int k1;
		int k12;
		super.decorate(world, random, i, k);
		if (random.nextInt(8) == 0) {
			i12 = i + random.nextInt(16) + 8;
			k12 = k + random.nextInt(16) + 8;
			j1 = world.getHeightValue(i12, k12);
			getRandomWorldGenForGrass(random).generate(world, random, i12, j1, k12);
		}
		if (random.nextInt(100) == 0) {
			i12 = i + random.nextInt(16) + 8;
			k12 = k + random.nextInt(16) + 8;
			j1 = world.getHeightValue(i12, k12);
			new WorldGenCactus().generate(world, random, i12, j1, k12);
		}
		if (random.nextInt(20) == 0) {
			i12 = i + random.nextInt(16) + 8;
			k12 = k + random.nextInt(16) + 8;
			j1 = world.getHeightValue(i12, k12);
			new WorldGenDeadBush(Blocks.deadbush).generate(world, random, i12, j1, k12);
		}
		if (random.nextInt(500) == 0) {
			int trees = 1 + random.nextInt(4);
			for (l = 0; l < trees; ++l) {
				i1 = i + random.nextInt(8) + 8;
				k1 = k + random.nextInt(8) + 8;
				int j12 = world.getHeightValue(i1, k1);
				decorator.genTree(world, random, i1, j12, k1);
			}
		}
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = biomeTerrainNoise.func_151601_a(i * 0.07, k * 0.07);
		double d2 = biomeTerrainNoise.func_151601_a(i * 0.4, k * 0.4);
		d2 *= 0.6;
		if (d1 + d2 > 0.7) {
			topBlock = Blocks.grass;
			topBlockMeta = 0;
			fillerBlock = Blocks.dirt;
			fillerBlockMeta = 0;
		} else if (d1 + d2 > 0.2) {
			topBlock = Blocks.dirt;
			topBlockMeta = 1;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterDorneDesert;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("dorneDesert");
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public GOTBiome.GrassBlockAndMeta getRandomGrass(Random random) {
		return new GOTBiome.GrassBlockAndMeta(GOTRegistry.aridGrass, 0);
	}
}