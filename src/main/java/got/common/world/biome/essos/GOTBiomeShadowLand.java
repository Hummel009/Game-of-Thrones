package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class GOTBiomeShadowLand extends GOTBiome {
	public GOTBiomeShadowLand(int i, boolean major) {
		super(i, major);
		topBlock = GOTRegistry.rock;
		topBlockMeta = 0;
		fillerBlock = GOTRegistry.rock;
		fillerBlockMeta = 0;
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
		flowers.clear();
		addFlower(GOTRegistry.asshaiFlower, 0, 20);
		decorator.flowersPerChunk = 256;
		biomeColors.setSky(0);
		biomeColors.setClouds(0);
		biomeColors.setFog(0);
		biomeColors.setWater(0);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_SHADOW_LAND;
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
