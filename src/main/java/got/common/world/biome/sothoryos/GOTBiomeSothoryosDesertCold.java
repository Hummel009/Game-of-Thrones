package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;

public class GOTBiomeSothoryosDesertCold extends GOTBiomeSothoryosDesert {
	public GOTBiomeSothoryosDesertCold(int i, boolean major) {
		super(i, major);
		preseter.setupDesertColdViewOverride();
		preseter.setupDesertColdFloraOverride();
		preseter.setupDesertColdFaunaOverride();
		preseter.setupDesertColdTreesOverride();

		npcSpawnList.clear();
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosDesertCold;
	}

	@Override
	protected boolean isColdDesert() {
		return true;
	}
}