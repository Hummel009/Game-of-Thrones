package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;

public class GOTBiomeSothoryosTaiga extends GOTBiomeSothoryosForest {
	public GOTBiomeSothoryosTaiga(int i, boolean major) {
		super(i, major);
		setupTaigaFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		addBiomeVariant(GOTBiomeVariant.HILLS_FOREST);
		fillerBlock = Blocks.snow;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.PINE, 20);
		decorator.clearSettlements();
		decorator.clearStructures();
		npcSpawnList.clear();
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosTaiga;
	}

	@Override
	public boolean isForest() {
		return false;
	}
}