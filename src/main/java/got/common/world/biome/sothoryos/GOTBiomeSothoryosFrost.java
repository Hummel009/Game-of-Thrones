package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTBezierType;
import net.minecraft.init.Blocks;

public class GOTBiomeSothoryosFrost extends GOTBiomeSothoryosBase {
	public GOTBiomeSothoryosFrost(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.snow;
		fillerBlock = Blocks.snow;

		preseter.setupFrostView();
		preseter.setupFrostFlora();
		preseter.setupFrostFauna();
		preseter.setupPolarTrees(false);

		biomeAchievement = GOTAchievement.enterSothoryosFrost;
		enableRiver = false;
		chanceToSpawnAnimals = 0.1f;
		roadBlock = GOTBezierType.PATH_SNOWY;
	}
}