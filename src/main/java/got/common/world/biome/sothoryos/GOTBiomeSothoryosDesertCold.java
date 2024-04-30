package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;

public class GOTBiomeSothoryosDesertCold extends GOTBiomeSothoryosDesert {
	public GOTBiomeSothoryosDesertCold(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		spawnableCreatureList.clear();
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosDesertCold;
	}
}