package got.common.world.biome.ulthos;

import got.client.sound.GOTMusicRegion;
import got.common.database.GOTBlocks;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.world.gen.feature.WorldGenMinable;

public abstract class GOTBiomeUlthosBase extends GOTBiome {
	protected GOTBiomeUlthosBase(int i, boolean major) {
		super(i, major);
		banditChance = GOTEventSpawner.EventChance.NEVER;
		decorator.setBiomeGemFactor(3.0f);
		decorator.setBiomeOreFactor(3.0f);
		decorator.addSoil(new WorldGenMinable(GOTBlocks.obsidianGravel, 32), 20.0f, 0, 64);
	}

	protected void setupForestTrees() {
		decorator.addTree(GOTTreeType.OAK, 50);
		decorator.addTree(GOTTreeType.OAK_LARGE, 100);
		decorator.addTree(GOTTreeType.DARK_OAK, 50);
		decorator.addTree(GOTTreeType.SPRUCE, 100);
		decorator.addTree(GOTTreeType.SPRUCE_THIN, 50);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA, 20);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA_THIN, 20);
		decorator.addTree(GOTTreeType.CHESTNUT, 20);
		decorator.addTree(GOTTreeType.CHESTNUT_LARGE, 50);
		decorator.addTree(GOTTreeType.CHESTNUT_PARTY, 3);
		decorator.addTree(GOTTreeType.DARK_OAK_PARTY, 3);
		decorator.addTree(GOTTreeType.LARCH, 200);
		decorator.addTree(GOTTreeType.FIR, 200);
		decorator.addTree(GOTTreeType.PINE, 400);
		decorator.addTree(GOTTreeType.ASPEN, 50);
		decorator.addTree(GOTTreeType.ASPEN_LARGE, 10);
	}

	@Override
	public GOTMusicRegion.Sub getBiomeMusic() {
		return GOTMusicRegion.ULTHOS.getSubregion(biomeName);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.ULTHOS;
	}
}