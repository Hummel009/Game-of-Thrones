package got.common.world.biome.sothoryos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.init.Blocks;

public class GOTBiomeSothoryosFrost extends GOTBiome {
	public GOTBiomeSothoryosFrost(int i, boolean major) {
		super(i, major);
		setupFrostFauna();
		addBiomeVariant(GOTBiomeVariant.HILLS);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
		topBlock = Blocks.snow;
		fillerBlock = Blocks.snow;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosFrost;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.SOTHORYOS.getSubregion("frost");
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}