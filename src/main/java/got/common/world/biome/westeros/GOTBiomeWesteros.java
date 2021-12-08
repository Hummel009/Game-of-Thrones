package got.common.world.biome.westeros;

import java.util.Random;

import com.google.common.math.IntMath;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTRegistry;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.*;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.common.GOTStructureWesterosObelisk;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeWesteros extends GOTBiome {
	public GOTBiomeWesteros(int i, boolean major) {
		super(i, major);
		this.addBiomeVariant(GOTBiomeVariant.DENSEFOREST_BIRCH);
		this.addBiomeVariant(GOTBiomeVariant.DENSEFOREST_OAK);
		this.addBiomeVariant(GOTBiomeVariant.FOREST);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_ASPEN, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_BEECH, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_BIRCH, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_MAPLE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.HILLS);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_FOREST);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_SCRUBLAND, 1.0f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_APPLE_PEAR, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_OLIVE, 0.1f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_ALMOND, 0.1f);
		this.addBiomeVariant(GOTBiomeVariant.SCRUBLAND, 1.0f);
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityHorse.class, 6, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBear.class, 4, 1, 1));
		decorator.addTree(GOTTreeType.OAK, 1000);
		decorator.addTree(GOTTreeType.OAK_LARGE, 300);
		decorator.addTree(GOTTreeType.BIRCH, 50);
		decorator.addTree(GOTTreeType.BIRCH_LARGE, 20);
		decorator.addTree(GOTTreeType.BEECH, 50);
		decorator.addTree(GOTTreeType.BEECH_LARGE, 20);
		decorator.addTree(GOTTreeType.APPLE, 5);
		decorator.addTree(GOTTreeType.PEAR, 5);
		decorator.addTree(GOTTreeType.PLUM, 5);
		decorator.addTree(GOTTreeType.OLIVE, 1);
		decorator.addTree(GOTTreeType.ALMOND, 1);
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 1;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		registerPlainsFlowers();
		decorator.addRandomStructure(new GOTStructureWesterosObelisk(false), 1000);
		decorator.addRandomStructure(new GOTStructureSmallStoneRuin(false), 500);
		decorator.addRandomStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addRandomStructure(new GOTStructureStoneRuin.STONE(1, 4), 400);
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		int chunkX = i & 0xF;
		int chunkZ = k & 0xF;
		int xzIndex = chunkX * 16 + chunkZ;
		int ySize = blocks.length / 256;
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
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("westeros");
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
	public int spawnCountMultiplier() {
		return 3;
	}
}