package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.entity.animal.GOTEntityWyvern;

public class GOTBiomeSothoryosHell extends GOTBiomeSothoryosJungle {
	public GOTBiomeSothoryosHell(int i, boolean major) {
		super(i, major);
		addSpawnableMonster(GOTEntityWyvern.class, 5, 1, 1);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosHell;
	}
}