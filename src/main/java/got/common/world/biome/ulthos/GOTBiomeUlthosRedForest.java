package got.common.world.biome.ulthos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.feature.GOTTreeType;

public class GOTBiomeUlthosRedForest extends GOTBiomeUlthosForest {
	public GOTBiomeUlthosRedForest(int i, boolean major) {
		super(i, major);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.ULTHOS_RED_OAK, 1000);
		decorator.addTree(GOTTreeType.ULTHOS_RED_OAK_LARGE, 50);
		decorator.addTree(GOTTreeType.ULTHOS_GREEN_OAK, 15);
		decorator.addTree(GOTTreeType.ULTHOS_GREEN_OAK_LARGE, 10);
		decorator.addTree(GOTTreeType.ULTHOS_GREEN_OAK_EXTREME, 1);
		decorator.addTree(GOTTreeType.OAK, 50);
		decorator.addTree(GOTTreeType.OAK_LARGE, 100);
		decorator.addTree(GOTTreeType.ULTHOS_OAK, 50);
		decorator.addTree(GOTTreeType.SPRUCE, 100);
		decorator.addTree(GOTTreeType.SPRUCE_THIN, 50);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA, 20);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA_THIN, 20);
		decorator.addTree(GOTTreeType.CHESTNUT, 20);
		decorator.addTree(GOTTreeType.CHESTNUT_LARGE, 50);
		decorator.addTree(GOTTreeType.CHESTNUT_PARTY, 3);
		decorator.addTree(GOTTreeType.LARCH, 200);
		decorator.addTree(GOTTreeType.FIR, 200);
		decorator.addTree(GOTTreeType.PINE, 400);
		decorator.addTree(GOTTreeType.ASPEN, 50);
		decorator.addTree(GOTTreeType.ASPEN_LARGE, 10);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterUlthosRedForest;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ULTHOS.getSubregion("ulthosRedForest");
	}
}
