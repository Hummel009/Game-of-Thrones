package got.common.world.biome.essos;

import java.util.Random;

import com.google.common.math.IntMath;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.yiti.GOTStructureYiTiCity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenFlowers;

public class GOTBiomeYiTi extends GOTBiome {

	public GOTBiomeYiTi(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityHorse.class, 5, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityWhiteBison.class, 6, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBear.class, 4, 1, 1));
		this.addBiomeVariant(GOTBiomeVariant.FLOWERS);
		this.addBiomeVariant(GOTBiomeVariant.FOREST);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		this.addBiomeVariant(GOTBiomeVariant.HILLS);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_FOREST);
		this.addBiomeVariant(GOTBiomeVariant.SHRUBLAND_OAK);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_APPLE_PEAR, 0.1f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_OLIVE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_ALMOND, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_DATE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_POMEGRANATE, 0.3f);
		this.addBiomeVariant(GOTBiomeVariant.SCRUBLAND_SAND);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_SCRUBLAND_SAND);
		this.addBiomeVariant(GOTBiomeVariant.VINEYARD, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FIELD_CORN, 0.2f);
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 1;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		decorator.addTree(GOTTreeType.CHERRY, 500);
		decorator.addTree(GOTTreeType.LEMON, 500);
		decorator.addTree(GOTTreeType.LIME, 500);
		decorator.addTree(GOTTreeType.POMEGRANATE, 500);
		registerYiTiPlainsFlowers();

		SpawnListContainer[] c0 = new SpawnListContainer[2];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_CONQUEST, 4).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);

		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.JOGOS_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);

		SpawnListContainer[] c2 = new SpawnListContainer[1];
		c2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.MANTICORE, 1).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(c2);

		decorator.affix(new GOTStructureYiTiCity(this, 1.0f));

		GOTStructureYiTiCity town = new GOTStructureYiTiCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.TraderTown, 0, -1);
		town.affix(GOTWaypoint.SiQo, 1, 0);
		town.affix(GOTWaypoint.Tiqui, 0, -1);
		town.affix(GOTWaypoint.Asabhad, -1, 0);
		town.affix(GOTWaypoint.Yin, 0, 1, 2);
		town.affix(GOTWaypoint.Jinqi, -1, 0, 3);
		town.affix(GOTWaypoint.Huiji);
		town.affix(GOTWaypoint.Vaibei, 0, -1);
		town.affix(GOTWaypoint.Manjin, 1, 0, 3);
		town.affix(GOTWaypoint.Lizhao, 1, 0);
		town.affix(GOTWaypoint.Baoji, 0, 1, 2);
		town.affix(GOTWaypoint.Yibin, -1, 0, 3);
		town.affix(GOTWaypoint.Yunnan, 1, 0, 1);
		town.affix(GOTWaypoint.Eijiang, 0, 1, 2);
		town.affix(GOTWaypoint.LesserMoraq);
		town.affix(GOTWaypoint.PortMoraq);
		town.affix(GOTWaypoint.Vahar);
		town.affix(GOTWaypoint.Faros);
		town.affix(GOTWaypoint.Zabhad);
		town.affix(GOTWaypoint.Marahai);
		town.affix(GOTWaypoint.Turrani);
		town.affix(GOTWaypoint.LengYi);
		town.affix(GOTWaypoint.LengMa);
		decorator.affix(town);

		invasionSpawns.addInvasion(GOTInvasions.JOGOS, GOTEventSpawner.EventChance.UNCOMMON);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int k1;
		int j1;
		int i1;
		super.decorate(world, random, i, k);
		if (random.nextInt(6) == 0) {
			i1 = i + random.nextInt(16) + 8;
			j1 = random.nextInt(128);
			k1 = k + random.nextInt(16) + 8;
			new WorldGenFlowers(GOTRegistry.pipeweedPlant).generate(world, random, i1, j1, k1);
		}
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		int chunkX = i & 0xF;
		int chunkZ = k & 0xF;
		int xzIndex = chunkX * 16 + chunkZ;
		int ySize = blocks.length / 256;
		double d1 = biomeTerrainNoise.func_151601_a(i * 0.08, k * 0.08);
		double d2 = biomeTerrainNoise.func_151601_a(i * 0.4, k * 0.4);
		if (d1 + d2 > 0.1) {
			int minHeight = height - 8 - random.nextInt(6);
			for (int j = height - 1; j >= minHeight; --j) {
				int index = xzIndex * ySize + j;
				Block block = blocks[index];
				if (block != Blocks.stone) {
					continue;
				}
				blocks[index] = Blocks.sandstone;
				meta[index] = 0;
			}
		}
		boolean kukuruza;
		kukuruza = variant == GOTBiomeVariant.FIELD_CORN;
		if (kukuruza && !GOTRoads.isRoadAt(i, k)) {
			for (int j = 128; j >= 0; --j) {
				int index = xzIndex * ySize + j;
				Block above = blocks[index + 1];
				Block block = blocks[index];
				if (block == null || !block.isOpaqueCube() || above != null && above.getMaterial() != Material.air) {
					continue;
				}
				IntMath.mod(i, 6);
				IntMath.mod(i, 24);
				IntMath.mod(k, 32);
				IntMath.mod(k, 64);
				double d;
				blocks[index] = Blocks.farmland;
				meta[index] = 0;
				int h = 2;
				if (biomeTerrainNoise.func_151601_a(i, k) > 0.0) {
					++h;
				}
				biomeTerrainNoise.func_151601_a(i * (d = 0.01), k * d);
				Block vineBlock = GOTRegistry.cornStalk;
				for (int j1 = 1; j1 <= h; ++j1) {
					blocks[index + j1] = vineBlock;
					meta[index + j1] = 8;
				}
				break;
			}
		}
		boolean vineyard;
		vineyard = variant == GOTBiomeVariant.VINEYARD;
		if (vineyard && !GOTRoads.isRoadAt(i, k)) {
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
					double d;
					blocks[index] = Blocks.farmland;
					meta[index] = 0;
					int h = 2;
					if (biomeTerrainNoise.func_151601_a(i, k) > 0.0) {
						++h;
					}
					boolean red = biomeTerrainNoise.func_151601_a(i * (d = 0.01), k * d) > 0.0;
					Block vineBlock = red ? GOTRegistry.grapevineRed : GOTRegistry.grapevineWhite;
					for (int j1 = 1; j1 <= h; ++j1) {
						blocks[index + j1] = vineBlock;
						meta[index + j1] = 7;
					}
					break;
				}
				if (i1 >= 2 && i1 <= 3) {
					blocks[index] = GOTRegistry.dirtPath;
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
						blocks[index + j1] = GOTRegistry.fence2;
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
		return GOTAchievement.VISIT_YI_TI;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("yiTi");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.YITI;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.COBBLEBRICK;
	}

	@Override
	public int getWallTop() {
		return 90;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}
