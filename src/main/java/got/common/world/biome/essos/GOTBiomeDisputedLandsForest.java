package got.common.world.biome.essos;

import got.common.world.biome.variant.GOTBiomeVariant;

public class GOTBiomeDisputedLandsForest extends GOTBiomeDisputedLands {
	public GOTBiomeDisputedLandsForest(int i, boolean major) {
		super(i, major);
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.2f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		decorator.setTreesPerChunk(10);
		npcSpawnList.clear();
		invasionSpawns.clearInvasions();
	}
}