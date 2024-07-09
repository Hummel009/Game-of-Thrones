package got.common.world.biome.ulthos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTBezierType;

public class GOTBiomeUlthosDesertCold extends GOTBiomeUlthosBase {
	public GOTBiomeUlthosDesertCold(int i, boolean major) {
		super(i, major);
		preseter.setupDesertColdView();
		preseter.setupDesertColdFlora();
		preseter.setupDesertColdFauna();
		preseter.setupDesertColdTrees();

		biomeAchievement = GOTAchievement.enterUlthosDesertCold;
		enableRiver = false;
		chanceToSpawnAnimals = 0.1f;
		roadBlock = GOTBezierType.PATH_SANDY;
	}
}