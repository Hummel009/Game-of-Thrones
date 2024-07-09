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
		decorator.setBiomeGemFactor(3.0f);
		decorator.setBiomeOreFactor(3.0f);
		decorator.addSoil(new WorldGenMinable(GOTBlocks.obsidianGravel, 32), 20.0f, 0, 64);

		biomeWaypoints = GOTWaypoint.Region.ULTHOS;
		biomeMusic = GOTMusicRegion.ULTHOS.getSubregion(biomeName);
		banditChance = GOTEventSpawner.EventChance.NEVER;
	}

	protected void setupDefaultTrees() {
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.PINE, 400);
		decorator.addTree(GOTTreeType.SPRUCE, 300);
		decorator.addTree(GOTTreeType.FIR, 300);
		decorator.addTree(GOTTreeType.DARK_OAK, 100);
		decorator.addTree(GOTTreeType.DARK_OAK_PARTY, 1);
	}
}