package got.common.world.biome.sothoryos;

import got.client.sound.GOTMusicRegion;
import got.common.database.GOTBlocks;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.world.gen.feature.WorldGenMinable;

public abstract class GOTBiomeSothoryosBase extends GOTBiome {
	protected GOTBiomeSothoryosBase(int i, boolean major) {
		super(i, major);
		banditChance = GOTEventSpawner.EventChance.NEVER;
		decorator.setBiomeGemFactor(2.0f);
		decorator.setBiomeOreFactor(2.0f);
		decorator.addSoil(new WorldGenMinable(GOTBlocks.obsidianGravel, 32), 20.0f, 0, 64);
	}

	protected void setupDefaultTrees() {
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.ACACIA, 300);
		decorator.addTree(GOTTreeType.ACACIA_DEAD, 1);
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
		decorator.addTree(GOTTreeType.DRAGONBLOOD, 50);
		decorator.addTree(GOTTreeType.DRAGONBLOOD_LARGE, 1);
		decorator.addTree(GOTTreeType.KANUKA, 50);
	}

	@Override
	public GOTMusicRegion.Sub getBiomeMusic() {
		return GOTMusicRegion.SOTHORYOS.getSubregion(biomeName);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.SOTHORYOS;
	}
}