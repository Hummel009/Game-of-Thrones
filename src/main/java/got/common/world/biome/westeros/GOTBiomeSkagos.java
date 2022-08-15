package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;

public class GOTBiomeSkagos extends GOTBiomeNorthWild {
	public GOTBiomeSkagos(int i, boolean major) {
		super(i, major);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSkagos;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("skagos");
	}
}
