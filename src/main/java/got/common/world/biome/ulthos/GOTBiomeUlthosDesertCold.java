package got.common.world.biome.ulthos;

import got.common.database.GOTAchievement;

public class GOTBiomeUlthosDesertCold extends GOTBiomeUlthosDesert {
	public GOTBiomeUlthosDesertCold(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		spawnableCreatureList.clear();
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterUlthosDesertCold;
	}

}