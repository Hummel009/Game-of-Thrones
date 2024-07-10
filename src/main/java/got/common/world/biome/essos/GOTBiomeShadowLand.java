package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

import java.awt.*;

public class GOTBiomeShadowLand extends GOTBiomeEssosBase {
	public GOTBiomeShadowLand(int i, boolean major) {
		super(i, major);
		topBlock = GOTBlocks.rock;
		topBlockMeta = 0;
		fillerBlock = GOTBlocks.rock;
		fillerBlockMeta = 0;

		preseter.removeAllEntities();

		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);

		flowers.clear();
		flowers.add(new FlowerEntry(GOTBlocks.asshaiFlower, 0, 20));

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

	public static boolean isBasaltSurface(IBlockAccess world, int i, int j, int k) {
		Block block = world.getBlock(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		return block == GOTBlocks.rock && meta == 0 || block == GOTBlocks.basaltGravel;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.ASSHAI;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterShadowLand;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}

	@Override
	public GOTEventSpawner.EventChance getBanditChance() {
		return GOTEventSpawner.EventChance.NEVER;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_ASSHAI;
	}
}