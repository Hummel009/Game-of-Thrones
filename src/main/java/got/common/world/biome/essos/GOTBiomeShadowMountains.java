package got.common.world.biome.essos;

import java.util.Random;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTWaypoint;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.structure.other.GOTStructureRuinsBig;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomeShadowMountains extends GOTBiome {
	public GOTBiomeShadowMountains(int i, boolean major) {
		super(i, major);
		topBlock = GOTRegistry.rock;
		topBlockMeta = 0;
		fillerBlock = GOTRegistry.rock;
		fillerBlockMeta = 0;
		spawnableGOTAmbientList.clear();
		spawnableCreatureList.clear();
		addBiomeVariant(GOTBiomeVariant.MOUNTAIN);
		decorator.biomeOreFactor = 2.0f;
		decorator.biomeGemFactor = 1.5f;
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreGlowstone, 4), 8.0f, 0, 48);
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreCobalt, 5), 5.0f, 0, 32);
		decorator.clearTrees();
		decorator.treesPerChunk = -1;
		biomeColors.setSky(0);
		biomeColors.setClouds(0);
		biomeColors.setFog(0);
		biomeColors.setWater(0);
		enableRocky = true;
		GOTStructureRuinsBig colossal = new GOTStructureRuinsBig(this, 0.0f);
		colossal.affix(GOTWaypoint.Stygai);
		decorator.affix(colossal);
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
				blocks[index] = GOTRegistry.rock;
				meta[index] = 0;
			}
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_SHADOW_MOUNTAINS;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("shadowMountains");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.ASSHAI;
	}

	public static boolean isBasalt(World world, int i, int j, int k) {
		Block block = world.getBlock(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		return block == GOTRegistry.rock && meta == 0 || block == GOTRegistry.asshaiDirt || block == GOTRegistry.basaltGravel;
	}
}
