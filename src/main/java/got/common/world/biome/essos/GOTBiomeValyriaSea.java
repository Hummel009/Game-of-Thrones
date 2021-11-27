package got.common.world.biome.essos;

import got.common.database.GOTAchievement;

public class GOTBiomeValyriaSea extends GOTBiomeValyria {
	public GOTBiomeValyriaSea(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_VALYRIA_SEA;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}
