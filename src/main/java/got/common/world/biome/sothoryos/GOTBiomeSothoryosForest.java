package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;

public class GOTBiomeSothoryosForest extends GOTBiomeSothoryosBase {
	public GOTBiomeSothoryosForest(int i, boolean major) {
		super(i, major);
		preseter.setupSavannahView();
		preseter.setupSavannahFlora();
		preseter.setupSavannahFauna();
		preseter.setupSavannahTrees();

		decorator.setTreesPerChunk(8);

		biomeAchievement = GOTAchievement.enterSothoryosForest;
	}
}