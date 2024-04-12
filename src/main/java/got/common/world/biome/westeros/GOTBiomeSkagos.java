package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;

public class GOTBiomeSkagos extends GOTBiomeNorthWild {
	public GOTBiomeSkagos(int i, boolean major) {
		super(i, major);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSkagos;
	}
}