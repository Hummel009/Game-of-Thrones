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
		preseter.setupFrostTrees(false);
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SNOWY;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosFrost;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}