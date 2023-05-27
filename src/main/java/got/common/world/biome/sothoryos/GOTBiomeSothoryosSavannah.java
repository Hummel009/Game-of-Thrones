package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;

public class GOTBiomeSothoryosSavannah extends GOTBiomeSothoryosBushland {
	public GOTBiomeSothoryosSavannah(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.HILLS);
		addBiomeVariant(GOTBiomeVariant.SAVANNAH_BAOBAB, 0.5f);
		decorator.grassPerChunk = 256;
		decorator.logsPerChunk = 0;
	}

	@Override
	public boolean enableTermite() {
		return false;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosSavannah;
	}


	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}
