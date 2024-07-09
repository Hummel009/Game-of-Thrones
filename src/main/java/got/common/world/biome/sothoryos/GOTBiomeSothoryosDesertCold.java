package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTBezierType;

public class GOTBiomeSothoryosDesertCold extends GOTBiomeSothoryosBase {
	public GOTBiomeSothoryosDesertCold(int i, boolean major) {
		super(i, major);
		preseter.setupDesertColdView();
		preseter.setupDesertColdFlora();
		preseter.setupDesertColdFauna();
		preseter.setupDesertColdTrees();

		biomeAchievement = GOTAchievement.enterSothoryosDesertCold;
		enableRiver = false;
		chanceToSpawnAnimals = 0.1f;
		roadBlock = GOTBezierType.PATH_SANDY;
	}
}