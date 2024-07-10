package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.entity.animal.GOTEntityJungleScorpion;
import got.common.world.map.GOTBezierType;

public class GOTBiomeSothoryosJungle extends GOTBiomeSothoryosBase {
	public GOTBiomeSothoryosJungle(int i, boolean major) {
		super(i, major);
		topBlock = GOTBlocks.mudGrass;
		fillerBlock = GOTBlocks.mud;

		preseter.setupJungleView();
		preseter.setupJungleFlora();
		preseter.setupJungleFauna();
		preseter.setupJungleTrees();

		addSpawnableMonster(GOTEntityJungleScorpion.class, 5, 1, 1);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosJungle;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SOTHORYOS;
	}
}