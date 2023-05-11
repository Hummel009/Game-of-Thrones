package got.common.world.biome.sothoryos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;

public class GOTBiomeSothoryosSavannah extends GOTBiomeSothoryosBushland {
	public GOTBiomeSothoryosSavannah(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.HILLS);
		addBiomeVariant(GOTBiomeVariant.SAVANNAH_BAOBAB, 3.0f);
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
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.SOTHORYOS.getSubregion("sothoryosSavannah");
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}
