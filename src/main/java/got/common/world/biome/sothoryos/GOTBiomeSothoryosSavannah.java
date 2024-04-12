package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;

public class GOTBiomeSothoryosSavannah extends GOTBiomeSothoryosBushland {
	public GOTBiomeSothoryosSavannah(int i, boolean major) {
		super(i, major);
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		biomeVariants.add(GOTBiomeVariant.SAVANNAH_BAOBAB, 0.5f);
		decorator.setGrassPerChunk(256);
		decorator.setLogsPerChunk(0);
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