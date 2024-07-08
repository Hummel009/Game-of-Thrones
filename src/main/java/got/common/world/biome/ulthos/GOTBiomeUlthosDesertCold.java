package got.common.world.biome.ulthos;

import got.common.database.GOTAchievement;

public class GOTBiomeUlthosDesertCold extends GOTBiomeUlthosDesert {
	public GOTBiomeUlthosDesertCold(int i, boolean major) {
		super(i, major);
		preseter.setupDesertColdViewOverride();
		preseter.setupDesertColdFloraOverride();
		preseter.setupDesertColdFaunaOverride();
		preseter.setupDesertColdTreesOverride();

		npcSpawnList.clear();
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterUlthosDesertCold;
	}

	@Override
	protected boolean isColdDesert() {
		return true;
	}
}