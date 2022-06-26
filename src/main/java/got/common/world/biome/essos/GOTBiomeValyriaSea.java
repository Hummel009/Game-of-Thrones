package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeValyriaSea extends GOTBiomeValyria {
	public GOTBiomeValyriaSea(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterValyriaSea;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}
