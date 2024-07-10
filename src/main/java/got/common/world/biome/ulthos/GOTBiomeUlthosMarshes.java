package got.common.world.biome.ulthos;

import got.common.database.GOTAchievement;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;

public class GOTBiomeUlthosMarshes extends GOTBiomeUlthosBase implements GOTBiome.Marshes {
	public GOTBiomeUlthosMarshes(int i, boolean major) {
		super(i, major);
		preseter.setupMarshesView();
		preseter.setupMarshesFlora();
		preseter.setupMarshesFauna();

		setupDefaultTrees();
		decorator.addTree(GOTTreeType.ULTHOS_GREEN_OAK, 1000);
		decorator.addTree(GOTTreeType.ULTHOS_GREEN_OAK_LARGE, 250);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterUlthosMarshes;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}