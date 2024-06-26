package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeIbbenTaiga extends GOTBiomeIbben {
	public GOTBiomeIbbenTaiga(int i, boolean major) {
		super(i, major);
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.2f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		banditChance = GOTEventSpawner.EventChance.NEVER;
		decorator.setTreesPerChunk(10);
		decorator.clearSettlements();
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterIbbenTaiga;
	}
}