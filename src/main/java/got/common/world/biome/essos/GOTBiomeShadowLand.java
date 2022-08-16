package got.common.world.biome.essos;

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

public class GOTBiomeShadowLand extends GOTBiome {
	public GOTBiomeShadowLand(int i, boolean major) {
		super(i, major);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
		flowers.clear();
		flowers.add(new FlowerEntry(GOTRegistry.asshaiFlower, 0, 20));
		topBlock = GOTRegistry.rock;
		topBlockMeta = 0;
		fillerBlock = GOTRegistry.rock;
		fillerBlockMeta = 0;
		decorator.sandPerChunk = 0;
		decorator.clayPerChunk = 0;
		decorator.flowersPerChunk = 256;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.CHARRED, 1000);
		biomeColors.setSky(0);
		biomeColors.setClouds(0);
		biomeColors.setFog(0);
		biomeColors.setWater(0);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterShadowLand;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("shadowLand");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.ASSHAI;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.ASSHAI;
	}

	public static boolean isBlackSurface(World world, int i, int j, int k) {
		Block block = world.getBlock(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		return block == GOTRegistry.rock && meta == 0 || block == GOTRegistry.basaltGravel;
	}
}