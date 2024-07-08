package got.common.world.biome.essos;

import got.client.sound.GOTMusicRegion;
import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

import java.awt.*;

public class GOTBiomeShadowLand extends GOTBiome {
	public GOTBiomeShadowLand(int i, boolean major) {
		super(i, major);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		banditChance = GOTEventSpawner.EventChance.NEVER;
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
		flowers.clear();
		flowers.add(new FlowerEntry(GOTBlocks.asshaiFlower, 0, 20));
		topBlock = GOTBlocks.rock;
		topBlockMeta = 0;
		fillerBlock = GOTBlocks.rock;
		fillerBlockMeta = 0;
		decorator.setSandPerChunk(0);
		decorator.setClayPerChunk(0);
		decorator.setFlowersPerChunk(256);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.CHARRED, 1000);
		biomeColors.setSky(new Color(0));
		biomeColors.setClouds(new Color(0));
		biomeColors.setFog(new Color(0));
		biomeColors.setWater(new Color(0));
	}

	public static boolean isBlackSurface(IBlockAccess world, int i, int j, int k) {
		Block block = world.getBlock(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		return block == GOTBlocks.rock && meta == 0 || block == GOTBlocks.basaltGravel;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterShadowLand;
	}

	@Override
	public GOTMusicRegion.Sub getBiomeMusic() {
		return GOTMusicRegion.ESSOS.getSubregion(biomeName);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.ASSHAI;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_ASSHAI;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return null;
	}

	@Override
	public int getWallTop() {
		return 0;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public boolean getEnableRiver() {
		return true;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}