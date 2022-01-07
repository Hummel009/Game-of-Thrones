package got.common.world.biome.essos;

import java.util.Random;

import com.google.common.math.IntMath;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTRegistry;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.*;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.dorne.GOTStructureDorneObelisk;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;

public class GOTBiomeEssos extends GOTBiome {
	public static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(8359286029006L), 1);
	public static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(473689270272L), 1);
	public static NoiseGeneratorPerlin noiseRedSand = new NoiseGeneratorPerlin(new Random(3528569078920702727L), 1);

	public GOTBiomeEssos(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLion.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLioness.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGiraffe.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityZebra.class, 8, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityRhino.class, 2, 1, 1));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGemsbok.class, 8, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityCamel.class, 6, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityElephant.class, 2, 1, 1));
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 5, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 8, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDikDik.class, 8, 1, 2));
		this.addBiomeVariant(GOTBiomeVariant.FLOWERS);
		this.addBiomeVariant(GOTBiomeVariant.FOREST);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		this.addBiomeVariant(GOTBiomeVariant.HILLS);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_FOREST);
		this.addBiomeVariant(GOTBiomeVariant.SHRUBLAND_OAK);
		this.addBiomeVariant(GOTBiomeVariant.SCRUBLAND_SAND);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_SCRUBLAND_SAND);
		decorator.addOre(new WorldGenMinable(Blocks.lapis_ore, 6), 1.0f, 0, 48);
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 2;
		decorator.flowersPerChunk = 1;
		decorator.doubleFlowersPerChunk = 1;
		decorator.cactiPerChunk = 1;
		decorator.generatePipeweed = true;
		decorator.addTree(GOTTreeType.ACACIA, 300);
		decorator.addTree(GOTTreeType.ALMOND, 5);
		decorator.addTree(GOTTreeType.BAOBAB, 20);
		decorator.addTree(GOTTreeType.CEDAR, 300);
		decorator.addTree(GOTTreeType.CEDAR_LARGE, 150);
		decorator.addTree(GOTTreeType.CYPRESS, 500);
		decorator.addTree(GOTTreeType.CYPRESS_LARGE, 50);
		decorator.addTree(GOTTreeType.DATE_PALM, 50);
		decorator.addTree(GOTTreeType.LEMON, 5);
		decorator.addTree(GOTTreeType.LIME, 5);
		decorator.addTree(GOTTreeType.OAK_DESERT, 400);
		decorator.addTree(GOTTreeType.OLIVE, 5);
		decorator.addTree(GOTTreeType.OLIVE_LARGE, 10);
		decorator.addTree(GOTTreeType.ORANGE, 5);
		decorator.addTree(GOTTreeType.PALM, 500);
		decorator.addTree(GOTTreeType.PLUM, 5);
		registerExoticFlowers();

		decorator.addRandomStructure(new GOTStructureDorneObelisk(false), 1000);
		decorator.addRandomStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addRandomStructure(new GOTStructureStoneRuin.SANDSTONE(1, 4), 400);
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = noiseDirt.func_151601_a(i * 0.002, k * 0.002);
		double d2 = noiseDirt.func_151601_a(i * 0.07, k * 0.07);
		double d3 = noiseDirt.func_151601_a(i * 0.25, k * 0.25);
		double d4 = noiseSand.func_151601_a(i * 0.002, k * 0.002);
		double d5 = noiseSand.func_151601_a(i * 0.07, k * 0.07);
		double d6 = noiseSand.func_151601_a(i * 0.25, k * 0.25);
		double d7 = noiseRedSand.func_151601_a(i * 0.002, k * 0.002);
		if (d7 + noiseRedSand.func_151601_a(i * 0.07, k * 0.07) + noiseRedSand.func_151601_a(i * 0.25, k * 0.25) > 0.9) {
			topBlock = Blocks.sand;
			topBlockMeta = 1;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d4 + d5 + d6 > 1.2) {
			topBlock = Blocks.sand;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d1 + d2 + d3 > 0.4) {
			topBlock = Blocks.dirt;
			topBlockMeta = 1;
		}
		int chunkX = i & 0xF;
		int chunkZ = k & 0xF;
		int xzIndex = chunkX * 16 + chunkZ;
		int ySize = blocks.length / 256;
		boolean kukuruza;
		kukuruza = variant == GOTBiomeVariant.FIELD_CORN;
		if (kukuruza && !GOTRoads.isRoadAt(i, k)) {
			for (int j = 128; j >= 0; --j) {
				int index = xzIndex * ySize + j;
				Block above = blocks[index + 1];
				Block block = blocks[index];
				if (((block != null) && block.isOpaqueCube() && ((above == null) || (above.getMaterial() == Material.air)))) {
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
		}
		boolean vineyard;
		vineyard = variant == GOTBiomeVariant.VINEYARD;
		if (vineyard && !GOTRoads.isRoadAt(i, k)) {
			for (int j = 128; j >= 0; --j) {
				int index = xzIndex * ySize + j;
				Block above = blocks[index + 1];
				Block block = blocks[index];
				if (((block != null) && block.isOpaqueCube() && ((above == null) || (above.getMaterial() == Material.air)))) {
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
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("freeCities");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.FREE;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
		GOTWorldGenDoubleFlower doubleFlowerGen = new GOTWorldGenDoubleFlower();
		if (random.nextInt(5) == 0) {
			doubleFlowerGen.setFlowerType(3);
		} else {
			doubleFlowerGen.setFlowerType(2);
		}
		return doubleFlowerGen;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SANDY;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.WOOD;
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
