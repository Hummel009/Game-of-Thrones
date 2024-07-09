package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.world.structure.sothoryos.sothoryos.GOTStructureSothoryosSettlement;

public class GOTBiomeSothoryosSavannah extends GOTBiomeSothoryosBase {
	public GOTBiomeSothoryosSavannah(int i, boolean major) {
		super(i, major);
		preseter.setupSavannahView();
		preseter.setupSavannahFlora();
		preseter.setupSavannahFauna();
		preseter.setupSavannahTrees();

		decorator.addSettlement(new GOTStructureSothoryosSettlement(this, 1.0f));

		biomeAchievement = GOTAchievement.enterSothoryosSavannah;
	}
}