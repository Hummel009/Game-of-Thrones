package got.common.world.biome.westeros;

import java.util.Random;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTWorldGenBoulder;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.dorne.GOTStructureDorneCity;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;

public class GOTBiomeDorneDesert extends GOTBiome {
	public static NoiseGeneratorPerlin noiseAridGrass = new NoiseGeneratorPerlin(new Random(62926025827260L), 1);
	public WorldGenerator boulderGen = new GOTWorldGenBoulder(Blocks.stone, 0, 1, 3);
	public WorldGenerator boulderGenSandstone = new GOTWorldGenBoulder(Blocks.sandstone, 0, 1, 3);

	public GOTBiomeDorneDesert(int i, boolean major) {
		super(i, major);
		setDisableRain();
		topBlock = Blocks.sand;
		fillerBlock = Blocks.sandstone;
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityCamel.class, 10, 2, 6));
		spawnableGOTAmbientList.clear();
		this.addBiomeVariant(GOTBiomeVariant.STEPPE);
		this.addBiomeVariant(GOTBiomeVariant.HILLS);
		decorator.addOre(new WorldGenMinable(Blocks.lapis_ore, 6), 1.0f, 0, 48);
		decorator.grassPerChunk = 0;
		decorator.doubleGrassPerChunk = 0;
		decorator.cactiPerChunk = 0;
		SpawnListContainer[] container11 = new SpawnListContainer[1];
		container11[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DESERT_SCORPION, 1).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(container11);
		GOTStructureDorneCity castle = new GOTStructureDorneCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Sandstone);
		decorator.addVillage(castle);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int k1;
		int k12;
		int preGrasses;
		int i1;
		int j1;
		int j12;
		int l;
		int i12;
		int grasses = preGrasses = decorator.grassPerChunk;
		double d1 = noiseAridGrass.func_151601_a(i * 0.002, k * 0.002);
		if (d1 > 0.5) {
			++grasses;
		}
		decorator.grassPerChunk = grasses;
		super.decorate(world, random, i, k);
		decorator.grassPerChunk = preGrasses;
		if (random.nextInt(50) == 0) {
			i12 = i + random.nextInt(16) + 8;
			k12 = k + random.nextInt(16) + 8;
			j12 = world.getHeightValue(i12, k12);
			new WorldGenCactus().generate(world, random, i12, j12, k12);
		}
		if (random.nextInt(16) == 0) {
			i12 = i + random.nextInt(16) + 8;
			k12 = k + random.nextInt(16) + 8;
			j12 = world.getHeightValue(i12, k12);
			new WorldGenDeadBush(Blocks.deadbush).generate(world, random, i12, j12, k12);
		}
		if (random.nextInt(120) == 0) {
			int boulders = 1 + random.nextInt(4);
			for (l = 0; l < boulders; ++l) {
				i1 = i + random.nextInt(16) + 8;
				k1 = k + random.nextInt(16) + 8;
				j1 = world.getHeightValue(i1, k1);
				if (random.nextBoolean()) {
					boulderGen.generate(world, random, i1, j1, k1);
					continue;
				}
				boulderGenSandstone.generate(world, random, i1, j1, k1);
			}
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_DORNE_DESERT;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("dorneDesert");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.DORNE;
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

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.WESTEROS_PATH;
	}
}
